package slice_group

import (
	"math"
	"net/http"
	"sync"
)

const abortIndex int8 = math.MaxInt8 / 2

type HandlerFunc func(c *Context)

type HandlerChain []HandlerFunc

type RouterGroup struct {
	Handlers HandlerChain
	engine   *Engine
}

// implement http.Handler
type Engine struct {
	tree map[string]HandlerChain
	RouterGroup
	pool sync.Pool
}

type Context struct {
	Rw       http.ResponseWriter
	Req      *http.Request
	handlers HandlerChain
	index    int8
}

// get a new engine(http.Handler)
func NewEngine() *Engine {
	engine := &Engine{
		RouterGroup: RouterGroup{
			Handlers: nil,
		},
		tree: make(map[string]HandlerChain),
	}
	engine.RouterGroup.engine = engine
	engine.pool.New = func() interface{} {
		return engine.allocateContext()
	}
	return engine
}

func (c *Context) Next() {
	c.index++
	// 调用 c.Abort() 的时候不会往后执行
	for c.index < int8(len(c.handlers)) {
		c.handlers[c.index](c)
		c.index++
	}
}

func (c *Context) Abort() {
	c.index = abortIndex
}

func (c *Context) Reset() {
	c.handlers = nil
	c.index = -1
}

func (engine *Engine) allocateContext() *Context {
	return &Context{}
}

func (engine *Engine) ServeHTTP(rw http.ResponseWriter, req *http.Request) {
	c := engine.pool.Get().(*Context)
	c.Rw = rw
	c.Req = req
	c.Reset()
	engine.serveHttp(c)
}

func (engine *Engine) serveHttp(c *Context) {
	rPath := c.Req.URL.Path
	handlers := engine.getHandlers(rPath)
	if handlers != nil {
		c.handlers = handlers
		c.Next()
	}
}

func (engine *Engine) addRoute(path string, handlers HandlerChain) {
	engine.tree[path] = handlers
}

func (engine *Engine) getHandlers(path string) (handlers HandlerChain) {
	handlers, ok := engine.tree[path]
	if !ok {
		return nil
	}
	return
}

func (group *RouterGroup) Use(middleware ...HandlerFunc) {
	group.Handlers = append(group.Handlers, middleware...)
}

// set common middleware
func (engine *Engine) Use(middleware ...HandlerFunc) {
	engine.RouterGroup.Use(middleware...)
}

// specific middleware
func (group *RouterGroup) AddRoute(absolutePath string, handlers ...HandlerFunc) {
	handlers = group.mergeHandlers(handlers)
	group.engine.addRoute(absolutePath, handlers)
}

// merge specific and common middleware
func (group *RouterGroup) mergeHandlers(handlers HandlerChain) HandlerChain {
	finalSize := len(group.Handlers) + len(handlers)
	mergedHandlers := make(HandlerChain, finalSize)
	copy(mergedHandlers, group.Handlers)
	copy(mergedHandlers[len(group.Handlers):], handlers)
	return mergedHandlers
}

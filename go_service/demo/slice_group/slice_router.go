package main

import (
	"log"
	"net/http"
	"sync"
)

type HandlerFunc func(c *Context)

type HandlerChain []HandlerFunc

type RouterGroup struct {
	Handlers HandlerChain
	engine   *Engine
}

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
	for c.index < int8(len(c.handlers)) {
		c.handlers[c.index](c)
		c.index++
	}
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
	handlers = group.combineHandlers(handlers)
	group.engine.addRoute(absolutePath, handlers)
}

// merge specific and common middleware
func (group *RouterGroup) combineHandlers(handlers HandlerChain) HandlerChain {
	finalSize := len(group.Handlers) + len(handlers)
	mergedHandlers := make(HandlerChain, finalSize)
	copy(mergedHandlers, group.Handlers)
	copy(mergedHandlers[len(group.Handlers):], handlers)
	return mergedHandlers
}

func main() {
	engine := NewEngine()
	engine.Use(func(c *Context) {
		c.Rw.Write([]byte("first middleware\n"))
		c.Next()
		log.Println("end first middleware")
	})
	engine.Use(func(c *Context) {
		c.Rw.Write([]byte("second middleware\n"))
		c.Next()
		log.Println("end second middleware")
	})
	engine.Use(func(c *Context) {
		c.Rw.Write([]byte("third middleware\n"))
		c.Next()
		log.Println("end third middleware")
	})
	engine.AddRoute("/v1/test", func(c *Context) {
		s := "this is a specific middleware"
		c.Rw.Write([]byte(s))
		log.Println(s)
		c.Next()
	})
	http.ListenAndServe(":8081", engine)
}

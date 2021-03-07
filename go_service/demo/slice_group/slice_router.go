package main

import (
	"context"
	"net/http"
)

type SliceRouter struct {
	group []*SliceGroup
}

type SliceGroup struct {
	*SliceRouter
	path     string
	handlers []HandlerFunc
}

type SliceContext struct {
	Rw  http.ResponseWriter
	Req *http.Request
	Ctx context.Context
	*SliceGroup
	index int8
}

type HandlerFunc func()

func NewSliceRouter() *SliceRouter {
	return &SliceRouter{}
}

func (p *SliceRouter) Group(path string) *SliceGroup {
	return &SliceGroup{
		path:        path,
		SliceRouter: p,
	}
}

func (p *SliceGroup) Use(middleware ...HandlerFunc) *SliceGroup {
	p.handlers = append(p.handlers, middleware...)
	exist := false
	for _, group := range p.SliceRouter.group {
		if group == p {
			exist = true
		}
	}
	if exist {
		p.SliceRouter.group = append(p.SliceRouter.group, p)
	}
	return p
}

package utils

import (
	"fmt"
	"sync"
)

type accumulator struct {
	name   string
	lock   *sync.Mutex
	expr   string
	metric map[string]map[string]uint64 // a:b=1 a:c=2 a:d=3
	job    func()
}

// 累加器集合
var accumulators []*accumulator

// 创建一个累加器
func NewAccumulator(name string, expr string) *accumulator {
	a := &accumulator{
		name:   name,
		lock:   &sync.Mutex{},
		expr:   expr,
		metric: make(map[string]map[string]uint64),
	}
	return a
}

// 这个方法要在开始执行之前调用
// 目的是在
func (p *accumulator) SetJob(job func()) {
	p.job = job
}

// 获取的指标是无法影响累加器内部指标的，需要清零只能调用已有的函数
func (p *accumulator) GetMetric() *map[string]map[string]uint64 {
	res := make(map[string]map[string]uint64)
	for k, v := range p.metric {
		res[k] = make(map[string]uint64)
		for s, u := range v {
			res[k][s] = u
		}
	}
	return &res
}

func (p *accumulator) ClearMetric(label string, metric string) {
	if _, ok := p.metric[label][metric]; ok {
		p.metric[label][metric] = 0
	}
}

func (p *accumulator) ClearLabel(label string) {
	if _, ok := p.metric[label]; ok {
		p.metric[label] = make(map[string]uint64)
	}
}

func (p *accumulator) ClearAllMetric() {
	p.metric = make(map[string]map[string]uint64)
}

func StartAccumulator() {
	for _, accumulator := range accumulators {
		accumulator.Start()
	}
}

// Start 默认为打印 累加器的指标
// 如果想要自定义任务的话，在调用 Start 函数之前，设置 accumulator 的 job属性
// job 不用是线程安全的，在调用 Start 函数之后，会自动设置线程安全
func (p *accumulator) Start() {
	c := NewCron()
	if p.job == nil {
		p.job = p.printMetric
	}
	f := func() {
		p.lock.Lock()
		defer p.lock.Unlock()
		p.job()
	}
	c.AddFunc(p.expr, f)
	go c.Start()
}

// 默认执行的定时任务
func (p *accumulator) printMetric() {
	fmt.Printf("%s %v\n", p.name, p.metric)
}

func (p *accumulator) Add(label string, metric string, delta uint64) {
	p.lock.Lock()
	defer p.lock.Unlock()
	m := p.metric[label]
	if m == nil {
		p.metric[label] = make(map[string]uint64)
	}
	p.metric[label][metric] += delta
}

func RegisterAccumulator(a ...*accumulator) {
	accumulators = append(accumulators, a...)
}

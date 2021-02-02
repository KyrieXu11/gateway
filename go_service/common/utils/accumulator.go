package utils

import (
	"fmt"
	"sync"
)

type accumulator struct {
	lock   *sync.Mutex
	expr   string
	metric map[string]map[string]uint64 // a:b=1 a:c=2 a:d=3
}

var accumulators []*accumulator

func NewAccumulator(expr string) *accumulator {
	a := &accumulator{
		lock:   &sync.Mutex{},
		expr:   expr,
		metric: make(map[string]map[string]uint64),
	}
	return a
}

func StartStat() {
	for _, accumulator := range accumulators {
		accumulator.Start()
	}
}

func (p *accumulator) Start() {
	c := NewCron()
	c.AddFunc(p.expr, p.printMetric)
	go c.Start()
}

func (p *accumulator) printMetric() {
	p.lock.Lock()
	defer p.lock.Unlock()
	fmt.Println(p.metric)
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

package demo_lb

import (
	"gateway/demo/listener"
	"gateway/middleware/reverse_proxy/load_balance"
)

type LoadBalance load_balance.LoadBalance

type RandomLoadBalance load_balance.RandomBalance

type Observer interface {
	Update()
	Subscribe(observer *Observer)
}

type DemoLoadBalance struct {
	observers []Observer
	prefix    string
	manager   *listener.EtcdManager
}

func (p *DemoLoadBalance) Attach(observer Observer) {
	p.observers = append(p.observers, observer)
}

func (p *DemoLoadBalance) Notice() {
	for _, observer := range p.observers {
		observer.Update()
	}
}

func (p *DemoLoadBalance) Watch() {
	manager := listener.NewEtcdManager([]string{"192.168.127.128:2379"})
	p.manager = manager
	manager.WatchForPrefix(p.prefix)
}

func (p *DemoLoadBalance) Close() {
	p.manager.Close()
}

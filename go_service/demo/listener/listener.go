package main

import (
	"context"
	"go.etcd.io/etcd/api/v3/mvccpb"
	"go.etcd.io/etcd/client/v3"
	"log"
	"time"
)

type Listener interface {
	Set(key []byte, val []byte)
	Create(key []byte, val []byte)
	Update(key []byte, val []byte)
	Delete(key []byte, val []byte)
}

type EtcdManager struct {
	hosts  []string
	client *clientv3.Client
	ctx    context.Context
	cancel context.CancelFunc
}

func NewEtcdManager(hosts []string) *EtcdManager {
	ctx, cancelFunc := context.WithTimeout(context.Background(), 100*time.Second)
	return &EtcdManager{
		client: initEtcdClient(hosts),
		hosts:  hosts,
		ctx:    ctx,
		cancel: cancelFunc,
	}
}

func (p *EtcdManager) AddWatch(key string, listener Listener) {
	go p.watch(key, listener)
}

func (p *EtcdManager) Register(name string, addr string, ttl int64) error {
	go func() {
		for true {
			key := "/" + name + "/" + addr
			response, err := p.client.Get(p.ctx, key)
			if err != nil {
				log.Println(err)
			} else if response.Count == 0 {
				p.keepAlive(name, addr, ttl)
			}
		}
	}()
	return nil
}

func (p *EtcdManager) keepAlive(name string, addr string, ttl int64) error {
	leaseResp, err := p.client.Grant(context.Background(), ttl)
	if err != nil {
		return err
	}

	_, err = p.client.Put(context.Background(), "/"+name+"/"+addr, addr, clientv3.WithLease(leaseResp.ID))
	if err != nil {
		log.Printf("put etcd error:%s", err)
		return err
	}

	_, err = p.client.KeepAlive(context.Background(), leaseResp.ID)
	if err != nil {
		log.Printf("keep alive error:%s", err)
		return err
	}
	return nil
}

func (p *EtcdManager) Close() error {
	p.cancel()
	err := p.client.Close()
	return err
}

func (p *EtcdManager) watch(key string, listener Listener) {
	watchChan := p.client.Watch(p.ctx, key)
	for {
		select {
		case <-p.ctx.Done():
			return
		case resp := <-watchChan:
			if err := resp.Err(); err != nil {
				log.Println(err.Error())
				continue
			}
			for _, event := range resp.Events {
				switch event.Type {
				case mvccpb.PUT:
					if event.IsCreate() {
						listener.Create(event.Kv.Key, event.Kv.Value)
					} else if event.IsModify() {
						listener.Update(event.Kv.Key, event.Kv.Value)
					}
					break
				case mvccpb.DELETE:
					listener.Delete(event.Kv.Key, event.Kv.Value)
					break
				}
			}
		}
	}
}

var client *clientv3.Client

func initEtcdClient(hosts []string) *clientv3.Client {
	c, err := clientv3.New(clientv3.Config{
		Endpoints:   hosts,
		DialTimeout: 3 * time.Second,
	})
	if err != nil {
		log.Fatal(err.Error())
	}
	log.Println("etcd connect successfully")
	return c
}

func Watch(ctx context.Context, key string) {
	watchChan := client.Watch(ctx, key)
	for response := range watchChan {
		for _, event := range response.Events {
			log.Printf("type:%s,  key:%s, value:%s \n", event.Type, event.Kv.Key, event.Kv.Value)
		}
	}
}

func Put(ctx context.Context, key string, val string) {
	_, err := client.Put(ctx, key, val)
	if err != nil {
		log.Fatal(err.Error())
	}
	log.Println("put k-v success")
}

type DemoListener struct {
}

func (p *DemoListener) Set(key []byte, val []byte) {
	log.Println("set")
}

func (p *DemoListener) Create(key []byte, val []byte) {
	log.Println("Create")
}

func (p *DemoListener) Update(key []byte, val []byte) {
	log.Println("Update")
}

func (p *DemoListener) Delete(key []byte, val []byte) {
	log.Println("Delete")
}

func main() {
	manager := NewEtcdManager([]string{"192.168.127.128:2379"})
	defer manager.Close()
	manager.AddWatch("a", &DemoListener{})
	ctx, cancelFunc := context.WithTimeout(context.Background(), 20*time.Second)
	select {
	case <-ctx.Done():
		cancelFunc()
	}
}

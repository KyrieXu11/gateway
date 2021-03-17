package listener

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
	ctx, cancelFunc := context.WithCancel(context.Background())
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

func (p *EtcdManager) GetServiceListByPrefix(prefix string) (*map[string]string, error) {
	prefix = "/" + prefix
	resp, err := p.client.Get(p.ctx, prefix, clientv3.WithPrefix())
	res := map[string]string{}
	if err != nil {
		return nil, err
	}
	for _, kv := range resp.Kvs {
		res[string(kv.Key)] = string(kv.Value)
	}
	return &res, nil
}

func (p *EtcdManager) Register(name string, addr string, ttl int64) error {
	ticker := time.NewTicker(time.Second * time.Duration(ttl))
	go func() {
		for {
			key := "/" + name + "/" + addr
			response, err := p.client.Get(p.ctx, key)
			if err != nil {
				log.Println(err)
			} else if response.Count == 0 {
				if err := p.keepAlive(name, addr, ttl); err != nil {
					log.Println(err.Error())
				}
			}
			<-ticker.C
		}
	}()
	return nil
}

func (p *EtcdManager) keepAlive(name string, addr string, ttl int64) error {
	leaseResp, err := p.client.Grant(p.ctx, ttl)
	if err != nil {
		return err
	}
	log.Printf("lease id : %x\n", leaseResp.ID)
	log.Println("put value start")
	_, err = p.client.Put(p.ctx, "/"+name+"/"+addr, addr, clientv3.WithLease(leaseResp.ID))
	if err != nil {
		log.Printf("put etcd error:%s", err)
		return err
	}
	log.Println("keep alive start")
	_, err = p.client.KeepAlive(p.ctx, leaseResp.ID)
	if err != nil {
		log.Printf("keep alive error:%s", err)
		return err
	}
	return nil
}

func (p *EtcdManager) WatchForPrefix(name string) {
	key := "/" + name
	ch := make(chan int)
	go p.watchForPrefix(key, ch)
	go func(ch chan int) {
		for {
			select {
			case a := <-ch:
				log.Println("delete", a)
			default:
				log.Println("111")
				time.Sleep(1 * time.Second)
			}
			log.Println("lalala")
		}
	}(ch)
}

func (p *EtcdManager) watchForPrefix(key string, ch chan int) {
	watchChan := p.client.Watch(p.ctx, key, clientv3.WithPrefix())
	for {
		select {
		case resp := <-watchChan:
			for _, ev := range resp.Events {
				switch ev.Type {
				case mvccpb.PUT:
					if ev.IsCreate() {
						log.Println("create")
					} else if ev.IsModify() {
						log.Println("update")
					}
					break
				case mvccpb.DELETE:

					break
				default:
					log.Println("no type")
					break
				}
			}
		}
	}
}

func (p *EtcdManager) UnRegister(name string, addr string) {
	if p.client != nil {
		p.client.Delete(p.ctx, "/"+name+"/"+addr)
	}
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

// func main() {
// 	manager := NewEtcdManager([]string{"192.168.127.128:2379"})
// 	defer manager.Close()
//
//
//
//
// 	ctx, cancelFunc := context.WithTimeout(context.Background(), 20*time.Second)
// 	select {
// 	case <-ctx.Done():
// 		cancelFunc()
// 	}
// }

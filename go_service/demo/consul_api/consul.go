package main

import (
	"fmt"
	consulapi "github.com/hashicorp/consul/api"
	"log"
)

type ConsulManager struct {
	client   *consulapi.Client
	Services map[string]*consulapi.AgentServiceRegistration
	CheckApi map[string]string
}

func NewConsulManager() (*ConsulManager, error) {
	config := consulapi.DefaultConfig()
	config.Address = "192.168.127.128:8500"
	client, err := consulapi.NewClient(config)
	if err != nil {
		return nil, err
	}
	return &ConsulManager{
		client: client,
		Services: map[string]*consulapi.AgentServiceRegistration{},
		CheckApi: map[string]string{},
	}, nil
}

func (p *ConsulManager) RegisterService(service *consulapi.AgentServiceRegistration) error {
	_, ok := p.Services[service.ID]
	if ok {
		return fmt.Errorf("service has been registered")
	}
	p.Services[service.ID] = service
	checkApi := fmt.Sprintf("http://%s:%d:%s", service.Address, service.Port, "/check")
	p.CheckApi[service.ID] = checkApi
	check := &consulapi.AgentServiceCheck{
		HTTP:     checkApi,
		Timeout:  "5s",
		Interval: "5s",
	}
	service.Check = check
	return p.client.Agent().ServiceRegister(service)
}

func main() {
	service := &consulapi.AgentServiceRegistration{
		Address: "127.0.0.1",
		Port:    8080,
		Name:    "test",
		ID:      "123",
		Tags:    []string{"test"},
	}
	manager, err := NewConsulManager()
	if err != nil {
		log.Fatal(err)
	}
	err = manager.RegisterService(service)
	if err != nil {
		log.Fatal(err)
	}
	select {
	}
}

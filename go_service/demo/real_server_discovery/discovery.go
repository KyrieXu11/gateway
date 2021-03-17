package main

import (
	"gateway/demo/listener"
	"os"
	"os/signal"
	"syscall"
)

func main() {
	manager := listener.NewEtcdManager([]string{"192.168.127.128:2379"})
	manager.WatchForPrefix("service")
	defer manager.Close()
	quit := make(chan os.Signal)
	signal.Notify(quit, syscall.SIGINT, syscall.SIGTERM)
	<-quit
}

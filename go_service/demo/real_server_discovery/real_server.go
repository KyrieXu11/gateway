package main

import (
	"fmt"
	"gateway/demo/listener"
	"io"
	"log"
	"net/http"
	"os"
	"os/signal"
	"syscall"
	"time"
)

type RealServer struct {
	Addr string
}

func main() {
	s1 := &RealServer{Addr: "127.0.0.1:2003"}
	s2 := &RealServer{Addr: "127.0.0.1:2004"}
	manager := listener.NewEtcdManager([]string{"192.168.127.128:2379"})
	defer manager.Close()
	s1.Run(manager)
	s2.Run(manager)
	quit := make(chan os.Signal)
	signal.Notify(quit, syscall.SIGINT, syscall.SIGTERM)
	q := <-quit
	manager.UnRegister("service", s1.Addr)
	manager.UnRegister("service", s2.Addr)
	if s, ok := q.(syscall.Signal); ok {
		os.Exit(int(s))
	} else {
		os.Exit(0)
	}
}

func (p *RealServer) HandlerFunc(w http.ResponseWriter, r *http.Request) {
	s := fmt.Sprintf("http://%s%s\n", p.Addr, r.URL.Path)

	ip := fmt.Sprintf("%v\n", r.Header.Get("X-Forwarded-For"))
	log.Printf("host: %s ip: %s", s, ip)
	io.WriteString(w, s)
	io.WriteString(w, ip)
}

func (p *RealServer) Run(manager *listener.EtcdManager) {
	log.Println("Start server with address :" + p.Addr)
	mux := http.NewServeMux()
	mux.HandleFunc("/", p.HandlerFunc)
	mux.HandleFunc("/base/error", p.ErrorFunc)
	server := &http.Server{
		Addr:         p.Addr,
		WriteTimeout: time.Second * 3,
		Handler:      mux,
	}
	if err := manager.Register("service", p.Addr, 20); err != nil {
		log.Println(err)
	}
	go func() {
		if err := server.ListenAndServe(); err != nil {
			log.Fatal(err.Error())
		}
	}()
}

func (p *RealServer) ErrorFunc(writer http.ResponseWriter, request *http.Request) {

}

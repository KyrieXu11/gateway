package main

import (
	"fmt"
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
	s1.Run()
	s2.Run()
	quit := make(chan os.Signal)
	signal.Notify(quit, syscall.SIGINT, syscall.SIGTERM)
	<-quit
}

func (p *RealServer) HandlerFunc(w http.ResponseWriter, r *http.Request) {
	s := fmt.Sprintf("http://%s%s\n", p.Addr, r.URL.Path)

	ip := fmt.Sprintf("%v\n", r.Header.Get("X-Forwarded-For"))

	io.WriteString(w, s)
	io.WriteString(w, ip)
}

func (p *RealServer) Run() {
	log.Println("Start server with address :" + p.Addr)
	mux := http.NewServeMux()
	mux.HandleFunc("/", p.HandlerFunc)
	mux.HandleFunc("/base/error", p.ErrorFunc)
	server := &http.Server{
		Addr:         p.Addr,
		WriteTimeout: time.Second * 3,
		Handler:      mux,
	}
	go func() {
		if err := server.ListenAndServe(); err != nil {
			log.Fatal(err.Error())
		}
	}()
}

func (p *RealServer) ErrorFunc(writer http.ResponseWriter, request *http.Request) {

}

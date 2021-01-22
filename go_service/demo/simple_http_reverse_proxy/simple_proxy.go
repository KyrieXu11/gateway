package main

import (
	"bufio"
	"log"
	"net/http"
	"net/url"
)

var (
	proxy_url = "http://127.0.0.1:8080"
	port      = "8082"
)

func main() {
	http.HandleFunc("/", HandleFunc)
	log.Println("Start server on port:" + port)
	if err := http.ListenAndServe(":"+port, nil); err != nil {
		log.Fatal(err.Error())
	}
}

func HandleFunc(w http.ResponseWriter, r *http.Request) {
	// 反向代理首先需要解析代理地址，把请求的地址和协议等等
	// 换成代理地址的协议和主机
	u, err := url.Parse(proxy_url)
	if err != nil {
		log.Fatal(err.Error())
	}
	r.URL.Scheme = u.Scheme
	r.URL.Host = u.Host
	clientIP := "127.0.0.1"
	r.Header.Set("X-Forwarded-For", clientIP)

	// 请求代理访问的下游服务器
	transport := http.DefaultTransport
	resp, err := transport.RoundTrip(r)
	if err != nil {
		log.Fatal(err)
	}
	defer resp.Body.Close()
	for k, vSlice := range resp.Header {
		for _, v := range vSlice {
			w.Header().Add(k, v)
		}
	}
	bufio.NewReader(resp.Body).WriteTo(w)
}

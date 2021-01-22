package main

import (
	"log"
	"net/http"
	"net/http/httputil"
	"net/url"
)

func main() {
	// 代理地址
	proxyUrl := "127.0.0.1:8083"

	// 请求的下游url
	dstUrl := "http://localhost:8082/base"

	u, err := url.Parse(dstUrl)
	if err != nil {
		log.Fatal(err.Error())
	}
	// 这个就是把现有的请求的路径拼接到请求下游的url的路径后面的函数
	proxy := httputil.NewSingleHostReverseProxy(u)
	log.Println("Server start address in :" + proxyUrl)
	if err = http.ListenAndServe(proxyUrl, proxy); err != nil {
		log.Fatal(err)
	}
}

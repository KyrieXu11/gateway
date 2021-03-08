package main

import (
	sg "gateway/demo/slice_group"
	"golang.org/x/time/rate"
	"log"
	"net/http"
)

func limit() func(c *sg.Context) {
	l := rate.NewLimiter(1, 5)
	return func(c *sg.Context) {
		if !l.Allow() {
			c.Rw.Write([]byte("server is busy!"))
			log.Println("server is busy!")
			c.Abort()
			return
		} else {
			log.Println("pass request")
			c.Next()
		}
	}
}

func main() {
	engine := sg.NewEngine()
	engine.Use(func(c *sg.Context) {
		c.Rw.Write([]byte("first middleware\n"))
		c.Next()
		log.Println("end first middleware")
	})
	engine.Use(func(c *sg.Context) {
		c.Rw.Write([]byte("second middleware\n"))
		c.Next()
		log.Println("end second middleware")
	})
	engine.Use(func(c *sg.Context) {
		c.Rw.Write([]byte("third middleware\n"))
		c.Next()
		log.Println("end third middleware")
	}, limit())
	engine.AddRoute("/v1/test", func(c *sg.Context) {
		s := "this is a specific middleware"
		c.Rw.Write([]byte(s))
		log.Println(s)
		c.Next()
	})
	http.ListenAndServe(":8081", engine)
}

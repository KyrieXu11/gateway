package main

import (
	"golang.org/x/time/rate"
	"log"
	"time"
)

func main() {
	limiter := rate.NewLimiter(2, 5)
	// for i := 0; i < 100; i++ {
	// 	c, cancelFunc := context.WithTimeout(context.Background(), time.Second*2)
	// 	log.Println("before wait")
	// 	if err := limiter.Wait(c); err != nil {
	// 		log.Println("limit wait error :", err.Error())
	// 	}
	//
	// 	log.Println("after wait")
	//
	// 	// 返回需要多久才能有新的token
	// 	reserve := limiter.Reserve()
	// 	log.Println("reserve delay:", reserve.Delay())
	//
	// 	// 判断当前是否可以获取token
	// 	allow := limiter.Allow()
	// 	log.Println("isAllowed：",allow)
	// 	cancelFunc()
	// }

	for i := 0; i < 100; i++ {
		if allow := limiter.Allow(); allow {
			log.Println("Allow consume")
		}
		time.Sleep(time.Millisecond * 500)
	}

}

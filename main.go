package main

import (
	"flag"
	"gateway/common/log"
	"gateway/common/utils"
	"gateway/start"
	"os"
	"os/signal"
	"syscall"
)

var (
	// 命令行的参数
	// 使用的方法就是 go run main.go -endpoint xxx -config ./xxx/xxx/
	endpoint = flag.String("endpoint", "dashboard",
		"input endpoint dashboard or server")
	conf = flag.String("config", "conf/dev",
		"input config file like ./conf/dev/")
)

func main() {
	flag.Parse()
	// // 如果输入的参数不对的话，输出使用方法并且退出程序
	// if *endpoint == "" {
	// 	flag.Usage()
	// 	os.Exit(1)
	// }
	// if *conf == "" {
	// 	flag.Usage()
	// 	os.Exit(1)
	// }
	// if *endpoint == "dashboard" {
	// 	err := start.InitModules(*conf)
	// 	// 不用打印日志了，在底层已经打印过日志了
	// 	if err != nil {
	// 		return
	// 	}
	// } else {
	//
	// }
	_ = start.InitModules(*conf)
	start.ListenAndServe()
	log.Info("application start!")

	// 程序退出处理
	quit()

	// 防止panic导致连接未关闭与资源未释放
	defer utils.Close()

	// a := [5]int{}
	// defer func() {
	// 	if err := recover(); err != nil {
	// 		fmt.Println(err)
	// 	}
	// }()
	// i := len(a)
	// a[i]=10
}

func quit() {
	quit := make(chan os.Signal)
	signal.Notify(quit, syscall.SIGINT, syscall.SIGQUIT)
	s := <-quit
	log.Infof("Catch the exit signal: %s", s.String())
}

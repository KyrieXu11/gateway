package main

import (
	"flag"
	"gateway/common/log"
	"gateway/common/utils"
	"gateway/start"
	"go.uber.org/zap"
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
	// 如果输入的参数不对的话，输出使用方法并且退出程序
	if *endpoint == "" {
		flag.Usage()
		os.Exit(1)
	}
	if *conf == "" {
		flag.Usage()
		os.Exit(1)
	}
	if err := start.InitModules(*conf); err != nil {
		return
	}
	start.ListenAndServe(*endpoint)

	// 程序退出处理
	quit()
	log.Info("be ready to end procession")
	// 防止panic导致连接未关闭与资源未释放
	defer utils.Close()
}

func quit() {
	quit := make(chan os.Signal)
	signal.Notify(quit, syscall.SIGINT, syscall.SIGQUIT)
	s := <-quit
	log.Info("Catch the exit signal:", zap.String("signal", s.String()))
}

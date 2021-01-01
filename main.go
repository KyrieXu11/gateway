package main

import (
	"flag"
	"gateway/common/config"
	"os"
)

var (
	// 命令行的参数
	// 使用的方法就是 go run main.go -endpoint xxx -config xxx
	endpoint = flag.String("endpoint", "", "input endpoint dashboard or server")
	conf     = flag.String("config", "", "input config file like ./conf/dev/")
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
	if *endpoint == "dashboard" {
		err := config.InitModule(*conf)
		// 不用打印日志了，在底层已经打印过日志了
		if err != nil {
			return
		}
	} else {

	}
}

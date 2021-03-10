package start

import (
	"gateway/common/log"
	"gateway/common/utils"
	"gateway/module"
	"gateway/start/http_proxy"
	"gateway/start/router"
)

// 需要加载的模块
var modules = []string{utils.ModuleApplication, utils.ModuleMySQL, utils.ModuleRedis}

// 初始化模块
func InitModules(configPath string) error {
	return initModules(configPath)
}

// 初始化模块
func initModules(configPath string) error {
	// 日志在执行 ParseConfigPath() 这个函数的时候只能 info 级别的才能输出
	// 因为默认的级别是 info
	// 因为配置文件还没有加载所以只能使用默认的配置
	// 如果想要改的话可以在 log/default#init() 改变传进去的默认的loglevel
	// 首先进行路径的解析
	if err := utils.ParseConfigPath(configPath); err != nil {
		return err
	}
	// 解析配置文件，采用的yml文件类型的配置文件
	if err := utils.LoadAllConfigYaml(); err != nil {
		return err
	}
	if err := initModule(module.InitApplication, utils.ModuleApplication); err != nil {
		return err
	}
	// 加载 mysql 连接
	if err := initModule(module.InitGorm, utils.ModuleMySQL); err != nil {
		return err
	}
	// 加载 redis 连接
	// 同时也做了session的配置
	if err := initModule(module.InitRedis, utils.ModuleRedis); err != nil {
		return err
	}
	return nil
}

// 加载模块，可重用代码
func initModule(f func() error, module string) error {
	// 如果包含这个模块则进行加载
	// 如果不包含的话就会继续执行后面的动作
	if utils.CheckModuleInArray(modules, module) {
		return f()
	}
	return nil
}

// 启动 gin
func ListenAndServe(endPoint string) {
	if endPoint == "dashboard" {
		r := router.InitRouter()
		router.ListenAndServe(r)
		log.Info("Dashboard Application Start!")
	} else {
		http_proxy.RunHttpServer()
		http_proxy.RunHttpTlsServer()
		log.Info("proxy service start!")
	}
}

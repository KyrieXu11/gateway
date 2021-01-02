package start

import (
	"gateway/common/utils"
	"gateway/dao"
)

var modules = []string{"application","mysql","redis"}

// 初始化模块
func InitModules(configPath string) error {
	return initModules(configPath)
}

// 初始化模块
func initModules(configPath string) error {
	// 首先进行路径的解析
	if err := utils.ParseConfigPath(configPath); err != nil {
		return err
	}
	// 解析配置文件，采用的yml文件类型的配置文件
	if err := utils.LoadAllConfigYaml(); err != nil {
		return err
	}
	// TODO: 设置一些基本的参数

	// 加载 mysql 连接
	if err := initModule(dao.InitGorm, "mysql"); err != nil {
		return err
	}
	// 加载 redis 连接
	if err := initModule(dao.InitRedis, "redis"); err != nil {
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

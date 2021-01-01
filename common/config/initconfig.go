package config

import "gateway/common/utils"

// 初始化模块
func InitModule(configPath string) error {
	var modules = []string{"base", "mysql", "redis"}
	return initModule(configPath, modules)
}

// 初始化模块
func initModule(configPath string, modules []string) error {
	// 首先进行路径的解析
	if err := utils.ParseConfigPath(configPath); err != nil {
		return err
	}
	// 解析配置文件，采用的yml文件类型的配置文件
	if err := utils.ParseAllConfigYaml(); err != nil {
		return err
	}
	return nil
}

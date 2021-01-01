package utils

import "github.com/spf13/viper"

// viper 可以存放 k-v 数据
// 这个上下文存放的是 fileName -> viper 的映射
// 在访问的时候的使用方法是 ConfigContextHolder[ConfigFileName].Get(key) 就可以访问文件当中的属性了
var ConfigContextHolder map[string]*viper.Viper

package utils

import (
	"github.com/gomodule/redigo/redis"
	"github.com/jinzhu/gorm"
	"github.com/spf13/viper"
	"io"
)

// viper 可以存放 k-v 数据
// 这个上下文存放的是 fileName -> viper 的映射
// 在访问的时候的使用方法是 ConfigContextHolder[ConfigFileName].Get(key) 就可以访问文件当中的属性了
var ConfigContextHolder map[string]*viper.Viper

// 数据库的全局对象
var DB *gorm.DB

// redis 的连接客户端
var Conn redis.Conn

// 关闭全局连接的函数
func Close() {
	if DB != nil {
		_ = DB.Close()
	}
	realClose(Conn)
}

// 真正执行的关闭方法
// 有个 bug 就是在关闭 DB 的时候
// debug 查看了变量为空，但是还能执行 Close() 函数
func realClose(inter io.Closer) {
	if inter != nil {
		_ = inter.Close()
	}
}

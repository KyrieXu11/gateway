package utils

import (
	"gateway/common/rpc"
	"github.com/gomodule/redigo/redis"
	"github.com/jinzhu/gorm"
	"github.com/spf13/viper"
	"google.golang.org/grpc"
	"io"
)

var (
	ConfigEnv     string // 配置文件的环境，比如传进来的路径是conf/dev则环境为dev
	ConfigEnvPath string // 配置文件的路径传进来的文件路径是上面的话，这个变量则为conf/dev
)

// viper 可以存放 k-v 数据
// 这个上下文存放的是 fileName -> viper 的映射
// 在访问的时候的使用方法是 ConfigContextHolder[ConfigFileName].Get(key) 就可以访问文件当中的属性了
// 当然也可以创建一个实体使用 Unmarshal() 的方法来将实体赋值
var ConfigContextHolder map[string]*viper.Viper

// 数据库的全局对象
var DB *gorm.DB

// redis 的连接客户端
var Conn redis.Conn

var MatcherClient rpc.AntPathMatcherClient

var RpcConn *grpc.ClientConn

// 关闭全局连接的函数
func Close() {
	if DB != nil {
		_ = DB.Close()
	}
	realClose(Conn)
	RpcConn.Close()
}

// 真正执行的关闭方法
// 有个 bug 就是在关闭 DB 的时候
// debug 查看了变量为空，但是还能执行 Close() 函数
func realClose(inter io.Closer) {
	if inter != nil {
		_ = inter.Close()
	}
}

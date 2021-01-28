package utils

import (
	"gateway/common/log"
	"gateway/common/rpc"
	"github.com/gomodule/redigo/redis"
	"github.com/jinzhu/gorm"
	"github.com/spf13/viper"
	"google.golang.org/grpc"
	"io"
	"sync"
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

var lock sync.Mutex

// 数据库的全局对象
var db *gorm.DB

// redis 的连接客户端
var conn redis.Conn

var matcherClient rpc.AntPathMatcherClient

var rpcConn *grpc.ClientConn

func SetDB(Db *gorm.DB) {
	lock.Lock()
	defer lock.Unlock()
	if db == nil {
		db = Db
	}
}

func GetDB() *gorm.DB {
	return db
}

func SetRedisConn(c redis.Conn) {
	lock.Lock()
	defer lock.Unlock()
	if conn == nil {
		conn = c
	}
}

func GetRedisConn() redis.Conn {
	return conn
}

func SetGRpcConn(clientConn *grpc.ClientConn) {
	lock.Lock()
	defer lock.Unlock()
	if rpcConn == nil {
		rpcConn = clientConn
	}
	client := rpc.NewAntPathMatcherClient(rpcConn)
	if matcherClient == nil {
		matcherClient = client
	}
}

func GetGRpcConn() *grpc.ClientConn {
	return rpcConn
}

func GetMatcherClient() rpc.AntPathMatcherClient {
	return matcherClient
}

// 关闭全局连接的函数
func Close() {
	// 首先先检查redis种的session的key清除了没
	if db != nil {
		_ = db.Close()
	}
	realClose(conn)
	if rpcConn != nil {
		_ = rpcConn.Close()
	}
	log.Sync()
}

// 真正执行的关闭方法
// 有个 bug 就是在关闭 DB 的时候
// debug 查看了变量为空，但是还能执行 Close() 函数
func realClose(inter io.Closer) {
	if inter != nil {
		_ = inter.Close()
	}
}

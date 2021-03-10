package module

import (
	"gateway/common/config"
	"gateway/common/log"
	"gateway/common/utils"
	"gateway/middleware"
	"github.com/gin-contrib/sessions"
	"github.com/gomodule/redigo/redis"
)

var (
	dbKey   = "redis"
	network = "tcp"
	address = "127.0.0.1:6379"
)

func initRedis() error {
	r := &config.RedisConfig{}
	// 进行反序列化
	if err := utils.Unmarshal(dbKey, r); err != nil {
		return err
	}
	if r.ConnectType != "" {
		network = r.ConnectType
	}
	if r.Address != "" {
		address = r.Address
	}
	var c redis.Conn
	// 如果配置文件当中配置了用户名和密码
	// 则使用用户名密码进行连接
	// 如果没有就不用用户名和密码
	if r.Auth != "" {
		conn, err := redis.Dial(
			network, address,
			redis.DialPassword(r.Auth),
		)
		if err != nil {
			return err
		}
		c = conn
	} else {
		conn, err := redis.Dial(network, address)
		if err != nil {
			return err
		}
		c = conn
	}
	// 初始化 redis 上下文
	utils.SetRedisConn(c)
	// 设置 session 中间件的配置
	if err := initRedisSessionStore(r); err != nil {
		return err
	}
	return nil
}

// 初始化 redis session 中间件
func initRedisSessionStore(conf *config.RedisConfig) error {
	sessionName := "session"
	maxAge := utils.GetIntConf(utils.ModuleApplication, "session.max_age")
	maxAge = maxAge * utils.Minute
	var opt = &sessions.Options{
		// 给的是秒...转换成分钟
		MaxAge: maxAge,
		// 设置session有效的路径，没10年脑血栓整不出来这玩意的🙃
		Path: "/",
	}
	return middleware.InitSessionConf(
		conf.Idle,
		conf.ConnectType,
		conf.Address,
		conf.Auth,
		sessionName,
		opt,
		[]byte("secret"))
}

// 初始化 redis
func InitRedis() error {
	if err := initRedis(); err != nil {
		log.Error(err.Error())
		return err
	}
	log.Info("Redis successfully initialized!")
	return nil
}

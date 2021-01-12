package module

import (
	"gateway/common/config"
	"gateway/common/log"
	"gateway/common/utils"
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
	if r.UserName != "" && r.Auth != "" {
		conn, err := redis.Dial(
			network, address,
			redis.DialUsername(r.UserName),
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
	if utils.Conn == nil {
		utils.Conn = c
	}
	return nil
}

// 初始化 redis
func InitRedis() error {
	err := initRedis()
	if err != nil {
		log.Error(err.Error())
		return err
	}
	log.Info("Redis successfully initialized!")
	return nil
}

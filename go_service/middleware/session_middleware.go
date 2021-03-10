package middleware

import (
	"gateway/common/log"
	"github.com/gin-contrib/sessions"
	"github.com/gin-contrib/sessions/cookie"
	"github.com/gin-contrib/sessions/redis"
	"github.com/gin-gonic/gin"
)

var (
	redisSessionFunc  gin.HandlerFunc
	cookieSessionFunc gin.HandlerFunc
)

func GetRedisSessionFunc() gin.HandlerFunc {
	return redisSessionFunc
}

func GetCookieSessionFunc() gin.HandlerFunc {
	return cookieSessionFunc
}

func InitCookieSessionConf(name string, options *sessions.Options, keyPairs ...[]byte) {
	store := cookie.NewStore(keyPairs...)
	store.Options(*options)
	cookieSessionFunc = sessions.Sessions(name, store)
}

// 初始化 session 中间件
func InitRedisSessionConf(
	maxIdle int,
	network, addr, auth, sessionName string,
	options *sessions.Options,
	keyPairs ...[]byte) error {

	store, err := redis.NewStore(maxIdle, network, addr, auth, keyPairs...)
	if err != nil {
		return err
	}
	if options != nil {
		log.Info("Use custom redis session option")
		store.Options(*options)
	}
	redisSessionFunc = sessions.Sessions(sessionName, store)
	return nil
}

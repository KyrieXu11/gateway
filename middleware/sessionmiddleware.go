package middleware

import (
	"gateway/common/log"
	"github.com/gin-contrib/sessions"
	"github.com/gin-contrib/sessions/redis"
	"github.com/gin-gonic/gin"
)

var sessionFunc gin.HandlerFunc

func GetSessionStore() gin.HandlerFunc {
	return sessionFunc
}

// 初始化 session 中间件
func InitSessionConf(maxIdle int,
	network, addr, auth, sessionName string,
	options *sessions.Options,
	keyPairs ...[]byte) error {
	store, err := redis.NewStore(maxIdle, network, addr, auth, keyPairs...)
	if err != nil {
		return err
	}
	if options != nil {
		log.Info("use custom redis session option")
		store.Options(*options)
	}
	sessionFunc = sessions.Sessions(sessionName, store)
	return nil
}

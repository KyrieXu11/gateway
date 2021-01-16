package middleware

import (
	"gateway/common/log"
	"github.com/gin-contrib/sessions"
	"github.com/gin-contrib/sessions/redis"
	"github.com/gin-gonic/gin"
)

var sessionStore gin.HandlerFunc

func GetSessionStore() gin.HandlerFunc {
	return sessionStore
}

// 设置 redis session 配置
func SetSessionConf(maxIdle int, network, addr, auth, sessionName string, keyPairs ...[]byte) error {
	store, err := redis.NewStore(maxIdle, network, addr, auth, keyPairs...)
	if err != nil {
		log.Error(err.Error())
		return err
	}
	sessionStore = sessions.Sessions(sessionName, store)
	return nil
}

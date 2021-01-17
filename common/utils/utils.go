package utils

import (
	"crypto/sha256"
	"fmt"
	"github.com/gin-contrib/sessions"
	"github.com/gin-gonic/gin"
	jsoniter "github.com/json-iterator/go"
)

// salt 是数据库中的盐值
// password 是前端传过来的密码
func GetSaltyPassword(salt, password string) string {
	s1 := sha256.New()
	s1.Write([]byte(password))
	str1 := fmt.Sprintf("%x", s1.Sum(nil))
	s2 := sha256.New()
	s2.Write([]byte(str1 + salt))
	return fmt.Sprintf("%x", s2.Sum(nil))
}

// 设置 session
func SetSession(c *gin.Context, key string, inter interface{}) error {
	session := sessions.Default(c)
	marshal, err := jsoniter.Marshal(inter)
	if err != nil {
		return err
	}
	session.Set(key, string(marshal))
	if err = session.Save(); err != nil {
		return err
	}
	return nil
}

// 获取session中的key
// 如果使用上面的函数的话获取的只能是string
func GetSessionVal(c *gin.Context, key string, res interface{}) error {
	session := sessions.Default(c)
	inter := session.Get(key).(string)
	if err := jsoniter.Unmarshal([]byte(inter), res); err != nil {
		return err
	}
	return nil
}

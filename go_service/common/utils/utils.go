package utils

import (
	"crypto/sha256"
	"fmt"
	"gateway/common/log"
	"github.com/gin-contrib/sessions"
	"github.com/gin-gonic/gin"
	jsoniter "github.com/json-iterator/go"
	"math/rand"
	"os/exec"
	"strconv"
	"strings"
	"time"
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

// 执行指令
func ExecCommand(name string, args ...string) (string, error) {
	cmd := exec.Command(name, args...)
	output, err := cmd.Output()
	if err != nil {
		return "", err
	}
	return string(output), nil
}

func StringToBool(s string) bool {
	res, err := strconv.ParseBool(s)
	if err != nil {
		log.Error(err.Error())
		return false
	}
	return res
}

// 随机生成密码盐
func GenerateSalt(length int) string {
	i := time.Now().Unix() % 100
	res := ""
	t := strconv.FormatInt(i, 10)
	for i := 0; i < length; i++ {
		// 小写
		base := 97
		if rand.Float32() < 0.5 {
			// 大写
			base = 65
		}
		i := rune(rand.Int()%26 + base)
		res += string(i)
	}
	return res + t
}

func ValidateAndBindParam(c *gin.Context, p interface{}) error {
	method := strings.ToLower(c.Request.Method)
	switch method {
	case "get":

		break
	case "post":
	case "put":
		if err := c.ShouldBindJSON(p); err != nil {
			return err
		}
		break
	}
	return nil
}

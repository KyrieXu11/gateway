package utils

import (
	"crypto/sha256"
	"fmt"
	"gateway/common/log"
	"github.com/gin-contrib/sessions"
	"github.com/gin-gonic/gin"
	jsoniter "github.com/json-iterator/go"
	"github.com/robfig/cron/v3"
	"go.uber.org/zap"
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
func SetSessionVal(c *gin.Context, key string, inter interface{}) error {
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
	admin := session.Get(key)
	if admin == nil {
		return fmt.Errorf("用户未登陆")
	}
	inter := admin.(string)
	if err := jsoniter.Unmarshal([]byte(inter), res); err != nil {
		return err
	}
	return nil
}

func GetSessionValue(c *gin.Context, key string) interface{} {
	session := sessions.Default(c)
	return session.Get(key)
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
		fallthrough
	case "put":
		if err := c.ShouldBindJSON(p); err != nil {
			return err
		}
		break
	}
	return nil
}

func SliceContains(list []string, s string) bool {
	for _, l := range list {
		if s == l {
			return true
		}
	}
	return false
}

func NewCron() *cron.Cron {
	location, err := time.LoadLocation("Asia/Shanghai")
	if err != nil {
		log.Error("", zap.String("error", err.Error()))
	}
	c := cron.New(cron.WithLocation(location))
	return c
}

func GetPage(page int, size int) (int, error) {
	if page < 1 {
		return 0, fmt.Errorf("页数不能小于1哦")
	}
	if size > 50 {
		return 0, fmt.Errorf("一页最多只能查询50条记录哦")
	}
	page = (page - 1) * size
	return page, nil
}

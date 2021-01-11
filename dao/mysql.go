package dao

import (
	"encoding/base64"
	"fmt"
	"gateway/common/config"
	"gateway/common/utils"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/mysql"
	"log"
	"unsafe"
)

var dbType = "mysql"

// 对错误信息返回的一个封装
func checkNil(in ...*interface{}) error {
	i := utils.CheckNil(in...)
	if i != -1 {
		switch i {
		case 0:
			return fmt.Errorf("请检查用户名是否填写")
		case 1:
			return fmt.Errorf("请检查密码是否填写")
		case 2:
			return fmt.Errorf("请检查端口号是否填写")
		case 3:
			return fmt.Errorf("检查数据库是否填写")
		case 4:
			return fmt.Errorf("检查主机是否填写")
		}
	}
	return nil
}

// 获取连接字符串
func getConnectString() (string, error) {
	var MysqlViper = utils.ConfigContextHolder[dbType]
	// 如果在配置文件当中写了连接字符串则直接进行连接
	// 如果没写的话,则看其他的属性是否填写
	// 如果其他属性有一个没填写的话就返回一个 error
	connectString := ""
	conn := MysqlViper.Get("url")
	if conn != nil {
		connectString = conn.(string)
	} else {
		user, pass, p, db, h := MysqlViper.Get("username"),
			MysqlViper.Get("password"), MysqlViper.Get("port"),
			MysqlViper.Get("dbName"), MysqlViper.Get("host")
		// 检查上面的参数是否有空的,有空的则无法进行连接
		err := checkNil(&user, &pass, &p, &db, &h)
		if err != nil {
			return "", err
		}
		username, port, dbName, host := user.(string), p.(int),
			db.(string), h.(string)
		// 对密码进行解码
		passBytes, _ := base64.StdEncoding.DecodeString(pass.(string))
		password := *(*string)(unsafe.Pointer(&passBytes))
		connectString = fmt.Sprintf(`%s:%s@tcp(%s:%d)/%s?charset=utf8&parseTime=True&loc=Local`,
			username, password, host, port, dbName)
	}
	return connectString, nil
}

// 获取连接字符串
func getConnectStringf() (string, error) {
	s := &config.MySQLConfig{}
	if err := utils.ParseConfig(dbType, s); err != nil {
		return "", err
	}
	// 如果配置了这个属性的话，则优先使用这个属性
	if s.ConnectString != "" {
		return s.ConnectString, nil
	} else {
		username, port, dbName, host := s.UserName, s.Port, s.DBName, s.Host
		passBytes, _ := base64.StdEncoding.DecodeString(s.PassWord)
		password := *(*string)(unsafe.Pointer(&passBytes))
		if err := check(username, dbName, host, password, port); err != nil {
			return "", err
		}
		connectString := fmt.Sprintf(`%s:%s@tcp(%s:%d)/%s?charset=utf8&parseTime=True&loc=Local`,
			username, password, host, port, dbName)
		return connectString, nil
	}
}

// 检查是否都写了这些配置
func check(username, dbName, host, password string, port int) error {
	if username == "" {
		return fmt.Errorf("请检查用户名是否填写")
	}
	if password == "" {
		return fmt.Errorf("请检查密码是否填写")
	}
	if dbName == "" {
		return fmt.Errorf("检查数据库是否填写")
	}
	if host == "" {
		return fmt.Errorf("检查主机是否填写")
	}
	if port == 0 {
		return fmt.Errorf("请检查端口号是否填写")
	}
	return nil
}

// 初始化 Gorm
func InitGorm() error {
	connectString, err := getConnectStringf()
	if err != nil {
		log.Println(err.Error())
		return err
	}
	db, err := gorm.Open(dbType, connectString)
	if err != nil {
		log.Println(err.Error())
		return err
	}
	if utils.DB == nil {
		utils.DB = db
	}
	log.Println("Gorm successfully initialized!")
	return nil
}

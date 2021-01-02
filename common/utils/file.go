package utils

import (
	"bytes"
	"fmt"
	"github.com/spf13/viper"
	"io/ioutil"
	"log"
	"os"
	"regexp"
	"strings"
)

var ConfigEnv string     // 配置文件的环境，比如传进来的路径是conf/dev/a.json则环境为dev
var ConfigEnvPath string // 配置文件的路径传进来的文件路径是上面的话，这个变量则为conf/dev

// 检查配置文件的路径是否为正确的
// 正确的就进行解析，不正确的就返回error
// 正确的就继续执行
func ParseConfigPath(config string) error {
	paths := strings.Split(config, "/")
	length := len(paths)
	// 检查路径的有效性
	if !checkPath(paths[:length-1]) {
		return fmt.Errorf("请检查文件路径的有效性")
	}
	// 检查文件的有效性
	if !checkFile(paths[length-1]) {
		return fmt.Errorf("请检查文件名称的有效性")
	}
	ConfigEnv = paths[length-2]
	ConfigEnvPath = strings.Join(paths[:length-1], "/")
	return nil
}

// 检查路径的有效性
func checkPath(path []string) bool {
	// TODO: 检查路径的有效性
	return true
}

// 检查文件的名称是否合规的
func checkFile(fileName string) bool {
	res, err := regexp.Match(fileNameRegex, []byte(fileName))
	if err != nil {
		log.Fatal(err.Error())
	}
	return res
}

// 解析配置文件
func LoadAllConfigYaml() error {
	dir, err := os.Open(ConfigEnvPath)
	if err != nil {
		log.Fatal(err.Error())
		return err
	}
	dirs, err := dir.Readdir(maxFileInfoNum)
	if err != nil {
		log.Fatal(err.Error())
		return err
	}
	for _, file := range dirs {
		// file 不是文件夹的话则对这个进行读取
		if !file.IsDir() {
			bytesArr, err := ioutil.ReadFile(ConfigEnvPath + "/" + file.Name())
			if err != nil {
				log.Fatal(err.Error())
			}
			v := viper.New()
			// 设置配置的类型
			v.SetConfigType(yaml)
			// 读取配置
			_ = v.ReadConfig(bytes.NewBuffer(bytesArr))
			// 上下文的 key 全部都是小写的
			name := strings.ToLower(strings.Split(file.Name(), ".")[0])
			// 如果没有初始化则进行初始化操作
			if ConfigContextHolder == nil {
				ConfigContextHolder = make(map[string]*viper.Viper)
			}
			ConfigContextHolder[name] = v
		}
	}
	return nil
}

// 检查接口类型是否为空
// 如果哪个为空则返回哪个为空的下标
func CheckNil(inters ...*interface{}) int {
	res, index := true, -1
	// 只有都不为空则返回true
	for i, inter := range inters {
		res = res && (inter != nil)
		if res == false {
			return i
		}
	}
	return index
}

// 看是否需要加载这个模块
func CheckModuleInArray(modules []string, module string) bool {
	for _, s := range modules {
		if s == module {
			return true
		}
	}
	return false
}

// 解析配置文件到实体类中去
func ParseConfig(key string, config interface{}) error {
	v := ConfigContextHolder[key]
	if v == nil {
		return fmt.Errorf("检查配置文件是否存在,或者配置文件名称是否正确")
	}
	err := v.Unmarshal(config)
	if err != nil {
		return err
	}
	return nil
}

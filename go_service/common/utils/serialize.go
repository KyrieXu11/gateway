package utils

import "fmt"

func Unmarshal(key string, inter interface{}) error {
	if v, ok := ConfigContextHolder[key]; ok {
		if err := v.Unmarshal(inter); err != nil {
			return err
		}
		return nil
	}
	return fmt.Errorf("检查配置文件是否存在,或者配置文件名称是否正确")
}

// 第一个参数传递哪个文件的
// 第二个参数传递想要获取哪个文件的什么属性
func GetStringConf(file string, key string) string {
	if v, ok := ConfigContextHolder[file]; ok {
		return v.GetString(key)
	}
	return ""
}

func GetIntConf(file string, key string) int {
	if v, ok := ConfigContextHolder[file]; ok {
		return v.GetInt(key)
	}
	return 0
}

func GetBoolConf(file string, key string) bool {
	if v, ok := ConfigContextHolder[file]; ok {
		return v.GetBool(key)
	}
	return false
}

func GetStringSliceConf(file string, key string) []string {
	if v, ok := ConfigContextHolder[file]; ok {
		return v.GetStringSlice(key)
	}
	return nil
}

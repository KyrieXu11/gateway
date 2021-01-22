package utils

const (
	maxFileInfoNum    = 1024                   // 一个文件夹下最多的文件信息个数
	yaml              = "yaml"                 // 配置文件类型
	fileNameRegex     = "^[\\s\\S]+.[a-zA-z]$" // 文件名称的正则表达式
	filePathRegex     = ""
	ModuleApplication = "application" // 应用的基础设置
	ModuleMySQL       = "mysql"
	ModuleRedis       = "redis"

	// 通用的 error_code 如果不指明错误类型
	// 则默认是这个类型的code
	COMMON_ERROR_CODE = 400
	NOT_FOUND_ERROR   = 404
	SERVER_ERROR      = 500
	SUCCESS_CODE      = 200

	Second = 1
	Minute = 60 * Second

	ValidatorKey   = "ValidatorKey"
	TranslatorKey  = "TranslatorKey"
	SessionKeyUser = "user"
)

var ERROR_CODE = []int{
	COMMON_ERROR_CODE,
	NOT_FOUND_ERROR,
	SERVER_ERROR,
}

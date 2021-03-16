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
	CommonErrorCode = 400
	NotFoundError   = 404
	ServerError     = 500
	SuccessCode     = 200

	Second = 1
	Minute = 60 * Second

	ValidatorKey   = "ValidatorKey"
	TranslatorKey  = "TranslatorKey"
	SessionKeyUser = "user"

	NeedHttps   = 1
	NoNeedHttps = 0

	NumBase10 = 10
	NumBase2  = 2

	IntegerBitSize64 = 64
	IntegerBitSize32 = 32

	HTTPLoadType = 0
	TCPLoadType  = 1
	GrpcLoadType = 2

	HTTPRuleTypePrefixURL = 0
	HTTPRuleTypeDomain    = 1

	HttpServiceType = "http"
	TcpServiceType  = "tcp"
	GrpcServiceType = "grpc"
	AllServiceType  = "all"
)

var ERROR_CODE = []int{
	CommonErrorCode,
	NotFoundError,
	ServerError,
}

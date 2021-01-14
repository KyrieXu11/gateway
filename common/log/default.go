package log

var defaultLogger *logger

func init() {
	defaultLogger = NewLogger(LevelInfo)
}

// 设置日志等级
func SetLoggerLevel(level interface{}) {
	var l int
	switch level.(type) {
	case string:
		levelInt, err := levelStringToLevel(level.(string))
		if err != nil {
			Error(err.Error())
			return
		}
		l = levelInt
		break
	case int:
		// TODO: 做一下参数的校验
		l = level.(int)
		break
	default:
		l = level.(int)
	}
	defaultLogger.SetLevel(l)
}

func SetOutPut(path string) {
	defaultLogger.SetOutPutPath(path)
}

func SetLoggerCallDepth(depth int) {
	defaultLogger.SetCallDepth(depth)
}

func Info(v ...interface{}) {
	defaultLogger.Info(v...)
}

func Infof(format string, v ...interface{}) {
	defaultLogger.Infof(format, v...)
}

func Debug(v ...interface{}) {
	defaultLogger.Debug(v...)
}

func Debugf(format string, v ...interface{}) {
	defaultLogger.Debugf(format, v...)
}

func Error(v ...interface{}) {
	defaultLogger.Error(v...)
}

func Errorf(format string, v ...interface{}) {
	defaultLogger.Errorf(format, v...)
}

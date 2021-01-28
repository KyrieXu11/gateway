package log

import "go.uber.org/zap"

var defaultLogger *logger

func init() {
	defaultLogger = newLogger()
}

// 设置日志等级
func SetLoggerLevel(level string) {
	defaultLogger.SetLevel(level)
}

func SetOutPut(path string) {
	defaultLogger.SetPath(path)
}

func Info(msg string, field ...zap.Field) {
	defaultLogger.info(msg, field...)
}

func Debug(msg string, field ...zap.Field) {
	defaultLogger.debug(msg, field...)
}

func Error(msg string, field ...zap.Field) {
	defaultLogger.error(msg, field...)
}

func Fatal(msg string, field ...zap.Field) {
	defaultLogger.fatal(msg, field...)
}

func Sync() error {
	return defaultLogger.sync()
}

func Warn(msg string, field ...zap.Field) {
	defaultLogger.warn(msg, field...)
}

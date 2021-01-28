package log

import (
	"go.uber.org/zap"
	"go.uber.org/zap/zapcore"
	"runtime"
	"strings"
)

const (
	LevelDebug zapcore.Level = iota - 1
	LevelInfo
	LevelWarning
	LevelError
	LevelFatal

	LevelDebugString   = "debug"
	LevelInfoString    = "info"
	LevelWarningString = "warning"
	LevelErrorString   = "error"
	LevelFatalString   = "fatal"

	StdOut   = "stdout"
	StdError = "stderr"
)

var levelMap = map[string]zapcore.Level{
	LevelDebugString:   LevelDebug,
	LevelInfoString:    LevelInfo,
	LevelWarningString: LevelWarning,
	LevelErrorString:   LevelError,
	LevelFatalString:   LevelFatal,
}

type logger struct {
	conf zap.Config
	log  *zap.Logger
}

func newLogger() *logger {
	ecfg := zapcore.EncoderConfig{
		TimeKey:       "time",
		LevelKey:      "level",
		NameKey:       "logger",
		CallerKey:     "caller",
		MessageKey:    "msg",
		StacktraceKey: "stacktrace",
		LineEnding:    zapcore.DefaultLineEnding,
		EncodeLevel:   zapcore.CapitalColorLevelEncoder,
		EncodeTime:    zapcore.TimeEncoderOfLayout("2006-01-02 15:04:05"),
		EncodeCaller:  callerFunc,
	}

	l := zap.NewAtomicLevelAt(levelMap[LevelDebugString])
	config := zap.Config{
		Level:            l,
		Development:      true,
		Encoding:         "console",
		OutputPaths:      []string{StdOut, "logs/gateway.log"},
		ErrorOutputPaths: []string{StdError, "logs/gateway.error.log"},
		EncoderConfig:    ecfg,
	}
	log, err := config.Build()
	if err != nil {
		panic("can't init zap logger")
	}
	return &logger{
		conf: config,
		log:  log,
	}
}

func (p *logger) SetLevel(levelString string) {
	levelString = strings.ToLower(levelString)
	level, ok := levelMap[levelString]
	if !ok {
		panic("check your code,log level is unknown")
	}
	p.conf.Level = zap.NewAtomicLevelAt(level)
	p.log, _ = p.conf.Build()
}

func (p *logger) SetPath(path string) {
	p.conf.OutputPaths[1] = path
	p.log, _ = p.conf.Build()
}

func (p *logger) info(msg string, field ...zap.Field) {
	p.log.Info(msg, field...)
}

func (p *logger) error(msg string, field ...zap.Field) {
	p.log.Error(msg, field...)
}

func (p *logger) debug(msg string, field ...zap.Field) {
	p.log.Debug(msg, field...)
}

func (p *logger) fatal(msg string, field ...zap.Field) {
	p.log.Fatal(msg, field...)
}
func (p *logger) dPanic(msg string, field ...zap.Field) {
	p.log.DPanic(msg, field...)
}

func (p *logger) warn(msg string, field ...zap.Field) {
	p.log.Warn(msg, field...)
}

func (p *logger) sync() error {
	return p.log.Sync()
}

func callerFunc(caller zapcore.EntryCaller, enc zapcore.PrimitiveArrayEncoder) {
	call := NewEntryCaller()
	enc.AppendString(call.TrimmedPath())
}

func NewEntryCaller() zapcore.EntryCaller {
	caller, file, line, ok := runtime.Caller(8)
	if !ok {
		return zapcore.EntryCaller{}
	}
	return zapcore.EntryCaller{
		Defined: true,
		PC:      caller,
		File:    file,
		Line:    line,
	}
}

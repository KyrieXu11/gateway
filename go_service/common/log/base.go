package log

import (
	"fmt"
	"log"
	"os"
	"strings"
)

const (
	LevelDebug int = iota
	LevelInfo
	LevelWarning
	LevelError
	LevelFatal

	LevelDebugString   = "DEBUG"
	LevelInfoString    = "INFO"
	LevelWarningString = "WARNING"
	LevelErrorString   = "ERROR"
	LevelFatalString   = "FATAL"
)

var levelStrings = []string{LevelDebugString, LevelInfoString, LevelWarningString, LevelErrorString, LevelFatalString}

type logger struct {
	level     int    // 日志的等级
	callDepth int    // 日志的打印的层级，默认设置了4层
	levelList []int  // 日志的等级列表，代码中有什么等级
	logFile   string // 日志输出的文件
}

func NewLogger(level int) *logger {
	return &logger{
		level:     level,
		callDepth: 4,
		levelList: make([]int, len(levelStrings)),
		logFile:   "logs/test.log",
	}
}

// level 字符串转化成等级
func levelStringToLevel(levelString string) (int, error) {
	var level int
	length := len(levelStrings)
	for ; level < length; level++ {
		if strings.ToLower(levelString) == strings.ToLower(levelStrings[level]) {
			break
		}
	}
	if level >= length {
		return 0, fmt.Errorf("检查传进来的日志级别是否正确")
	}
	return level, nil
}

func (p *logger) print(level int, v ...interface{}) {
	// 小于设置的等级则不打印
	if level < p.level {
		return
	}
	v = append(v, "")
	for i := len(v) - 1; i >= 1; i-- {
		v[i] = v[i-1]
	}
	// 把日志的级别放进去
	v[0] = fmt.Sprintf("[%s]", levelStrings[level])
	// fallthrough 的用法
	// 从第一个case开始判断是否为true，如果为true则继续往下执行不会判断后面的case值
	// 直到没有fallthrough进行正常判断case值
	switch level {
	case LevelFatal:
		fallthrough
	case LevelError:
		if p.levelList[LevelError] == 1 {
			_ = log.Output(p.callDepth, fmt.Sprintln(v...))
		}
		break
	case LevelWarning:
		if p.levelList[LevelWarning] == 1 {
			_ = log.Output(p.callDepth, fmt.Sprintln(v...))
		}
		break
	case LevelInfo:
		if p.levelList[LevelInfo] == 1 {
			_ = log.Output(p.callDepth, fmt.Sprintln(v...))
		}
		break
	case LevelDebug:
		if p.levelList[LevelDebug] == 1 {
			_ = log.Output(p.callDepth, fmt.Sprintln(v...))
		}
		break
	default:
		if level == LevelFatal {
			os.Exit(1)
		}
	}
}

func (p *logger) SetOutPutPath(path string) {
	p.logFile = path
	log.SetOutput(p)
}

func (p *logger) Write(bs []byte) (int, error) {
	f, err := os.OpenFile(p.logFile, os.O_CREATE|os.O_APPEND, 0x666)
	defer f.Close()
	if err != nil {
		return -1, err
	}
	return f.Write(bs)
}

func (p *logger) SetLevel(level int) {
	p.level = level
}

func (p *logger) SetCallDepth(callDepth int) {
	p.callDepth = callDepth
}

func (p *logger) Info(v ...interface{}) {
	p.levelList[LevelInfo] = 1
	if LevelInfo >= p.level {
		p.print(LevelInfo, v...)
	}
}

func (p *logger) Infof(format string, v ...interface{}) {
	p.levelList[LevelInfo] = 1
	if LevelInfo >= p.level {
		p.print(LevelInfo, fmt.Sprintf(format, v...))
	}
}

func (p *logger) Warning(v ...interface{}) {
	p.levelList[LevelWarning] = 1
	if LevelWarning >= p.level {
		p.print(LevelWarning, v...)
	}
}

func (p *logger) Warningf(format string, v ...interface{}) {
	p.levelList[LevelWarning] = 1
	if LevelWarning >= p.level {
		p.print(LevelWarning, fmt.Sprintf(format, v...))
	}
}

func (p *logger) Fatal(v ...interface{}) {
	p.levelList[LevelFatal] = 1
	if LevelFatal >= p.level {
		p.print(LevelFatal, fmt.Sprint(v...))
	}
}

func (p *logger) Errorf(format string, v ...interface{}) {
	p.levelList[LevelError] = 1
	if LevelError >= p.level {
		p.print(LevelError, fmt.Sprintf(format, v...))
	}
}

func (p *logger) Error(v ...interface{}) {
	p.levelList[LevelError] = 1
	if LevelError >= p.level {
		p.print(LevelError, v...)
	}
}

func (p *logger) Debug(v ...interface{}) {
	p.levelList[LevelDebug] = 1
	if LevelDebug >= p.level {
		p.print(LevelDebug, v...)
	}
}

func (p *logger) Debugf(format string, v ...interface{}) {
	p.levelList[LevelDebug] = 1
	if LevelDebug >= p.level {
		p.print(LevelDebug, fmt.Sprintf(format, v...))
	}
}

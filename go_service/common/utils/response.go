package utils

import (
	"github.com/gin-gonic/gin"
)

type Response struct {
	Code int         `json:"code"` // 状态码
	Msg  string      `json:"msg"`  // 返回给前端的消息
	Data interface{} `json:"data"` // 返回给前端的数据
}

func CheckErrorAndResponse(c *gin.Context, err error) bool {
	if err != nil {
		ResponseErrorM(c, err.Error())
		return true
	}
	return false
}

func ResponseErrorCM(c *gin.Context, code int, msg string) {
	r := &Response{
		Code: code,
		Msg:  msg,
	}
	// 请求码为 200，而不是表示请求失败了
	c.JSON(SuccessCode, r)
}

func ResponseErrorM(c *gin.Context, msg string) {
	r := &Response{
		Code: CommonErrorCode,
		Msg:  msg,
	}
	c.JSON(SuccessCode, r)
}

func ResponseError(c *gin.Context) {
	r := &Response{
		Code: CommonErrorCode,
	}
	c.JSON(SuccessCode, r)
}

func ResponseSuccessAll(c *gin.Context, code int, msg string, data interface{}) {
	r := &Response{
		Code: code,
		Msg:  msg,
		Data: data,
	}
	c.JSON(SuccessCode, r)
}

func ResponseSuccessCM(c *gin.Context, code int, msg string) {
	r := &Response{
		Code: code,
		Msg:  msg,
		Data: nil,
	}
	c.JSON(SuccessCode, r)
}

func ResponseSuccessObj(c *gin.Context, msg string, data interface{}) {
	r := &Response{
		Code: SuccessCode,
		Msg:  msg,
		Data: data,
	}
	c.JSON(SuccessCode, r)
}

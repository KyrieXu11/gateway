package utils

import "github.com/gin-gonic/gin"

type Response struct {
	Code int         // 状态码
	Msg  string      // 返回给前端的消息
	Data interface{} // 返回给前端的数据
}

func ResponseErrorCM(c *gin.Context, code int, msg string) {
	r := &Response{
		Code: code,
		Msg:  msg,
	}
	// 请求码为 200，而不是表示请求失败了
	c.JSON(SUCCESS_CODE, r)
}

func ResponseErrorM(c *gin.Context, msg string) {
	r := &Response{
		Code: COMMON_ERROR_CODE,
		Msg:  msg,
	}
	c.JSON(SUCCESS_CODE, r)
}

func ResponseError(c *gin.Context) {
	r := &Response{
		Code: COMMON_ERROR_CODE,
	}
	c.JSON(SUCCESS_CODE, r)
}

func ResponseSuccessAll(c *gin.Context, code int, msg string, data interface{}) {
	r := &Response{
		Code: code,
		Msg:  msg,
		Data: data,
	}
	c.JSON(SUCCESS_CODE, r)
}

func ResponseSuccessCM(c *gin.Context, code int, msg string) {
	r := &Response{
		Code: code,
		Msg:  msg,
		Data: nil,
	}
	c.JSON(SUCCESS_CODE, r)
}

func ResponseSuccessObj(c *gin.Context, msg string, data interface{}) {
	r := &Response{
		Code: SUCCESS_CODE,
		Msg:  msg,
		Data: data,
	}
	c.JSON(SUCCESS_CODE, r)
}

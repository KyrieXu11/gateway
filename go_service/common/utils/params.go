package utils

import (
	"fmt"
	"github.com/gin-gonic/gin"
	ut "github.com/go-playground/universal-translator"
	"github.com/go-playground/validator/v10"
	"strings"
)

func DefaultGetValidParams(c *gin.Context, params interface{}) error {
	if err := c.ShouldBind(params); err != nil {
		return err
	}
	// 获取验证器
	valid, err := GetValidator(c)
	if err != nil {
		return err
	}
	// 获取翻译器
	trans, err := GetTranslation(c)
	if err != nil {
		return err
	}
	if err = valid.Struct(params); err != nil {
		errs := err.(validator.ValidationErrors)
		var sliceErrs []string
		for _, e := range errs {
			sliceErrs = append(sliceErrs, e.Translate(trans))
		}
		return fmt.Errorf(strings.Join(sliceErrs, ","))
	}
	return nil
}

func GetValidator(c *gin.Context) (*validator.Validate, error) {
	val, ok := c.Get(ValidatorKey)
	if !ok {
		return nil, fmt.Errorf("未设置验证器")
	}
	validate, ok := val.(*validator.Validate)
	if !ok {
		return nil, fmt.Errorf("获取验证器失败")
	}
	return validate, nil
}

func GetTranslation(c *gin.Context) (ut.Translator, error) {
	trans, ok := c.Get(TranslatorKey)
	if !ok {
		return nil, fmt.Errorf("未设置翻译器")
	}
	translator, ok := trans.(ut.Translator)
	if !ok {
		return nil, fmt.Errorf("获取翻译器失败")
	}
	return translator, nil
}

func GetPageSize(page int, size int) (int, int) {
	// 第2页 50个，在数据库体现是是第 1 页 50个
	// 而 offset 的参数是跳过 page 条数据
	page = (page - 1) * size
	return page, size
}

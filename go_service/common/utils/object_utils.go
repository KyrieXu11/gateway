package utils

import (
	"bytes"
	"encoding/gob"
)

// 拷贝的对象不能有小写的属性
func DeepClone(src, dst interface{}) error {
	var buf bytes.Buffer
	if err := gob.NewEncoder(&buf).Encode(src); err != nil {
		return err
	}
	return gob.NewDecoder(bytes.NewBuffer(buf.Bytes())).Decode(dst)
}

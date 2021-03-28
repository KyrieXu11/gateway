package utils

import "github.com/gomodule/redigo/redis"

func RedisConfPipline(pip ...func(c redis.Conn)) error {
	c:= conn
	for _, f := range pip {
		f(c)
	}
	c.Flush()
	return nil
}

func RedisConfDo(commandName string, args ...interface{}) (interface{}, error) {
	c:= conn
	return c.Do(commandName, args...)
}


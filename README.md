# 读取配置文件

配置文件都放在了`conf`文件夹下，不同的环境有不同的文件夹，比如`dev`环境就放在了`./conf/dev`
文件夹下，一次性加载了一个环境下的所有配置文件并且放在了一个`ConfigContextHolder`中，这个上下文是一个`map`,
映射是`配置文件名称 -> Viper`，而`Viper`是一个`Golang`中读取配置文件的一个库，非常的方便，每次需要获取配置的`value
`的话，使用`viper.Get(key)`就可以获取到`value`。

如果使用的是`unmarshal`来进行反序列化操作的时候，遇到了嵌套的属性，那么可以在结构体当中创建一个内部结构体，比如：
```yaml
server:
  port: 8080
```
则可以像下面这种方式进行反序列化
```gotemplate
type Config struct{
	Server struct{
	    Port    int	
    }
}
```
获取`Port`属性的方式就是`config.Server.Port`，而项目当中也封装了一个方法可以不使用结构体获取想要的数据，详情请看
[`serialize#GetStringConf()`](./common/utils/serialize.go)
# 日志库的写法
定义了一个结构体`logger`，里面有`log_level`、`callDepth`、`level_list`几个属性，大于`log_level
`的日志等级才能输出，输出控制有两种方式：

1. 使用的是`switch` + `fallthrough`来进行控制日志根据等级进行输出的
2. 使用`switch` + `break`来进行控制日志等级输出的

这两种方式都可以做到，但是这两种方式有着不一样的实现思路，第一种实现是使用日志组(`log group`)
来实现的，就是每一种日志级别都会有一个日志对象，每次跳进`switch`的时候就可以让`log`输出
而这个项目是使用的单`log`实现的，所以采用的是第二种实现方式。
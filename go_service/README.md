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

# 登陆权限校验

在中间件中，对登陆请求做了判断，但是获取名称为`user`的`session`看是否在内存当中，这种方式太草率了，让所有的请求都被拦截下来了，所以我们需要对路径和请求的方法做一个筛选，只有登陆路径和`POST
`请求的方式才能通过，而在`Golang`当中没有类似`spring-core`中的[`AntPathMatcher`](./java_utils/AntPathMatcher.java)
，所以做起来十分的难受，那么既然没有的话，我们就有下面的三种跨语言调用的方法：
1. 做一个`RPC`调用。
2. 使用`GO`进行重写。
3. 进程通信（广义上的，通过在`golang`执行命令，并且传参给`java`程序，获取标准输出重定向到`Golang`程序当中，但是这种方式比较的不安全，很容易篡改数据）。

我这里图个方便，直接采用了第三种方式。
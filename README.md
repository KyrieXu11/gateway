# 读取配置文件

配置文件都放在了`conf`文件夹下，不同的环境有不同的文件夹，比如`dev`环境就放在了`./conf/dev`
文件夹下，一次性加载了一个环境下的所有配置文件并且放在了一个`ConfigContextHolder`中，这个上下文是一个`map`,
映射是`配置文件名称 -> Viper`，而`Viper`是一个`Golang`中读取配置文件的一个库，非常的方便，每次需要获取配置的`value
`的话，使用`viper.Get(key)`就可以获取到`value`。
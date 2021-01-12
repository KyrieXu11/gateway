package config

type Application struct {
	// viper 使用 unmarshal 的时候解决多级嵌套的时候需要定义结构体...
	Server struct {
		Port int
	}
	Gin      struct {
		Mode string
	}
	LogLevel string
}

type MySQLConfig struct {
	UserName      string `mapstructure:"username"`
	PassWord      string `mapstructure:"password"`
	Port          int    `mapstructure:"port"`
	Host          string `mapstructure:"host"`
	DBName        string `mapstructure:"dbName"`
	ConnectString string `mapstructure:"url"`
}

type RedisConfig struct {
	Address     string `mapstructure:"address"`
	ConnectType string `mapstructure:"connect_type"`
	UserName    string `mapstructure:"username"`
	Auth        string `mapstructure:"auth"`
}

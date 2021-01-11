package config

type Application struct {
	Port int `mapstructure:"server.port"`
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

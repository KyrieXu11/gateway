package dao

type ServiceDetail struct {
	Info          *ServiceInfo   `json:"info,omitempty" description:"基本信息"`
	HTTPRule      *HttpRule      `json:"http_rule,omitempty" description:"http_rule"`
	TCPRule       *TcpRule       `json:"tcp_rule,omitempty" description:"tcp_rule"`
	GRPCRule      *GrpcRule      `json:"grpc_rule,omitempty" description:"grpc_rule"`
	LoadBalance   *LoadBalance   `json:"load_balance,omitempty" description:"load_balance"`
	AccessControl *AccessControl `json:"access_control,omitempty" description:"access_control"`
}
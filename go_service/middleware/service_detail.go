package middleware

import "gateway/dao"

type ServiceDetail struct {
	Info          *dao.ServiceInfo   `json:"info" description:"基本信息"`
	HTTPRule      *dao.HttpRule      `json:"http_rule" description:"http_rule"`
	TCPRule       *dao.TcpRule       `json:"tcp_rule" description:"tcp_rule"`
	GRPCRule      *dao.GrpcRule      `json:"grpc_rule" description:"grpc_rule"`
	LoadBalance   *dao.LoadBalance   `json:"load_balance" description:"load_balance"`
	AccessControl *dao.AccessControl `json:"access_control" description:"access_control"`
}

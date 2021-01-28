package rpc_client

import (
	"context"
	"gateway/common/log"
	"gateway/common/rpc"
	"gateway/common/utils"
	"go.uber.org/zap"
	"google.golang.org/grpc"
	"sync"
	"time"
)

var once sync.Once

func newMatcherClient() {
	var opts []grpc.DialOption

	opts = append(opts, grpc.WithBlock())
	opts = append(opts, grpc.WithInsecure())
	serverAddr := utils.GetStringConf("application", "rpc.matcher.server_addr")
	conn, err := grpc.Dial(serverAddr, opts...)
	if err != nil {
		log.Error("fail to dial:", zap.String("error", err.Error()))
	}
	utils.SetGRpcConn(conn)
}

func NewMatcherClient() {
	once.Do(newMatcherClient)
}

// Match 是 rpc 流式客户端的函数
func Match(client rpc.AntPathMatcherClient, paths *rpc.Paths) bool {
	res := false
	ctx, cancel := context.WithTimeout(context.Background(), 1*time.Second)
	defer cancel()
	stream, err := client.Match(ctx)
	if err != nil {
		return false
	}
	if err := stream.Send(paths); err != nil {
		log.Error(err.Error())
		return false
	}
	resp, err := stream.CloseAndRecv()
	if err != nil {
		log.Error(err.Error())
		return false
	}
	res = resp.Res
	return res
}

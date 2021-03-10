package http_proxy

import (
	"gateway/common/log"
	"gateway/common/utils"
	"gateway/controller"
	"github.com/gin-gonic/gin"
	"go.uber.org/zap"
	"net/http"
	"os"
	"time"
)

func getServer(addr string, readTimeout int, writeTimeout int, maxHeaderBytes int, router http.Handler) *http.Server {
	server := &http.Server{
		Handler:        router,
		Addr:           addr,
		ReadTimeout:    time.Duration(readTimeout) * time.Second,
		WriteTimeout:   time.Duration(writeTimeout) * time.Second,
		MaxHeaderBytes: 1 << uint(maxHeaderBytes),
	}
	return server
}

func RunHttpServer() {
	r := InitRouter()
	registerRouter(r)
	addr := utils.GetStringConf(utils.ModuleApplication, "proxy.http.addr")
	readTimeout := utils.GetIntConf(utils.ModuleApplication, "proxy.http.read_timeout")
	writeTimeout := utils.GetIntConf(utils.ModuleApplication, "proxy.http.write_timeout")
	maxHeaderBytes := utils.GetIntConf(utils.ModuleApplication, "proxy.http.max_header_bytes")
	server := getServer(addr, readTimeout, writeTimeout, maxHeaderBytes, r)
	go func() {
		log.Info("", zap.String("HttpProxy run on", addr))
		if err := server.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			log.Error(err.Error())
			os.Exit(1)
		}
	}()
}

func RunHttpTlsServer() {
	r := InitRouter()
	registerRouter(r)
	addr := utils.GetStringConf(utils.ModuleApplication, "proxy.https.addr")
	readTimeout := utils.GetIntConf(utils.ModuleApplication, "proxy.https.read_timeout")
	writeTimeout := utils.GetIntConf(utils.ModuleApplication, "proxy.https.write_timeout")
	maxHeaderBytes := utils.GetIntConf(utils.ModuleApplication, "proxy.https.max_header_bytes")
	server := getServer(addr, readTimeout, writeTimeout, maxHeaderBytes, r)
	go func() {
		log.Info("", zap.String("HttpTLSProxy run on", addr))
		if err := server.ListenAndServeTLS("./cert_file/example.com+5.pem", "./cert_file/example.com+5-key.pem"); err != nil && err != http.ErrServerClosed {
			log.Error(err.Error())
			os.Exit(1)
		}
	}()
}

func registerRouter(r *gin.Engine) {
	r.GET("/ping", controller.Ping)
}

#!/bin/bash
# 必须先起 java 服务才能起 go 服务，因为Go服务调用了java服务，否则会报错
cd java_service
mvn package clean -Dmaven.test.skip=true
mvn package -Dmaven.test.skip=true
java -jar target/gateway_java_service-1.0-SNAPSHOT.jar >/data/logs/gateway/java_service_startup.log 2>&1 &
cd ../go_service
if [ ! -d logs ];then
   mkdir -p $logs
fi
go run main.go > /data/logs/gateway/go_service_startup.log 2>&1 &

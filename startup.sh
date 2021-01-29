#!/bin/bash
cd java_service
mvn package clean -Dmaven.test.skip=true
mvn package -Dmaven.test.skip=true
java -jar target/gateway_java_service-1.0-SNAPSHOT.jar >/data/logs/gateway/java_service_startup.log 2>&1 &
cd ../go_service
if [ ! -d logs ];then
   mkdir -p $logs
fi
go run main.go > /data/logs/gateway/go_service_startup.log 2>&1 &

#! /bin/bash

# 切换 jdk 版本
jdk11
java -version
mvn -v

# maven build
# 公共 jar 打包发布
cd liuyun-common/ && mvn clean package deploy -e


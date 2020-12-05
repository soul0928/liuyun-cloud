#! /bin/bash

# maven build

# 公共 jar 打包发布
cd liuyun-common/ && mvn clean package deploy -e


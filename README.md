-----
###  授权码模式（authorization_code）
http://localhost:19080/oauth/authorize?client_id=admin&response_type=code&redirect_uri=http://www.liuyum.com
###  隐式授权模式（implicit）
http://localhost:19080/oauth/authorize?client_id=admin&response_type=token&redirect_uri=http://www.liuyum.com

----
### sentinel
#### 文档地址 https://github.com/alibaba/Sentinel/wiki/%E6%8E%A7%E5%88%B6%E5%8F%B0
nohup java -Dserver.port=8852 -Dcsp.sentinel.dashboard.server=localhost:8852 -Dproject.name=sentinel-dashboard \
 -Dsentinel.dashboard.auth.username=sentinel -Dsentinel.dashboard.auth.password=sentinel \
-Dserver.servlet.session.timeout=7200 \
-jar sentinel-dashboard-1.8.0.jar  >sentinel.log 2>&1 &

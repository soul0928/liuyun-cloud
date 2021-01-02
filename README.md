-----
## (此两种模式通过网关访问需要重新配置)
###  授权码模式（authorization_code）
http://localhost:19080/oauth/authorize?client_id=admin&response_type=code&redirect_uri=http://www.liuyum.com
###  隐式授权模式（implicit）
http://localhost:19080/oauth/authorize?client_id=admin&response_type=token&redirect_uri=http://www.liuyum.com

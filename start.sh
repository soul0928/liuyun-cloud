#! /bin/bash


keytool -genkey -storetype PKCS12 -alias liuyun -storepass liuyun -keypass liuyun -validity 36500 -keyalg RSA -keystore liuyun.jks -dname "CN=www.liuyun.com,OU=liuyun,O=liuyun,L=ChaoYang,ST=Beijing,c=cn"

keytool -list -v -keystore liuyun.jks
package com.liuyun.auth.config.constants;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/22 11:26
 **/
public class AuthConstants {

    /**
     * 请求头 client_id:client_secret Base64加密
     */
    public static final String AUTHORIZATION = "Authorization";
    public static final String BASIC = "Basic ";
    /**
     * 应用标识
     */
    public static final String CLIENT_ID = "client_id";
    /**
     * 应用密钥
     */
    public static final String CLIENT_SECRET = "client_secret";
    /**
     * 授权类型
     */
    public static final String GRANT_TYPE = "grant_type";

    /**
     * 授权模式
     */
    public static final String PASSWORD = "password";
    public static final String PHONE_CODE = "phone_code";
    public static final String CLIENT_CREDENTIALS = "client_credentials";
    public static final String AUTHORIZATION_CODE = "authorization_code";
    public static final String REFRESH_TOKEN = "refresh_token";




}

package com.liuyun.swagger2;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * swagger2 属性配置
 *
 * @author mall
 * @date 2018/11/18 9:17
 */
@Data
@Configuration
@ConfigurationProperties("liuyun.swagger")
public class SwaggerProperties {

    /**
     * 是否开启swagger
     */
    private Boolean enabled = false;

    /**
     * 标题
     */
    private String title = "";

    /**
     * 描述
     */
    private String description = "";

    /**
     * 版本
     */
    private String version = "";

    /**
     * 许可证
     */
    private String license = "";

    /**
     * 许可证URL
     */
    private String licenseUrl = "";

    /**
     * 服务条款URL
     */
    private String termsOfServiceUrl = "";

    /**
     * swagger会解析的包路径
     */
    private String basePackage = "";

    /**
     * 联系人信息
     */
    private Contact contact = new Contact();

    /**
     * 全局参数配置
     */
    private List<GlobalOperationParameter> globalOperationParameters;

    /**
     * 联系人信息
     **/
    @Data
    public static class Contact {

        /**
         * 联系人
         */
        private String name = "";

        /**
         * 联系人url
         */
        private String url = "";

        /**
         * 联系人email
         */
        private String email = "";
    }

    /**
     * 全局参数
     **/
    @Data
    public static class GlobalOperationParameter{

        /**
         * 参数名
         */
        private String name;

        /**
         * 描述信息
         */
        private String description;

        /**
         * 指定参数类型
         */
        private String modelRef;

        /**
         * 参数放在哪个地方:header,query,path,body.form
         */
        private String parameterType;

        /**
         * 参数是否必须传
         */
        private Boolean required;
    }


}

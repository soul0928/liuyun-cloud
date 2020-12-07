package ${cfg.basepackage}.dto;

<#list table.importPackages as pkg>
    import ${pkg};
</#list>
<#if swagger2>
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
    import lombok.Data;
</#if>

/**
* ${table.comment!}
*
* @author ${author}
* @since ${.now}
*/

@Data
<#if swagger2>
    @ApiModel(value="${entity}对象 参数", description="${table.comment!}")
</#if>
public class ${entity}DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键 ID")
    protected Long id;
    <#-- ；-----  BEGIN 字段循环遍历  ---------->
    <#list table.fields as field>

        <#if field.keyFlag>
        <#-- 主键 -->

        <#-- 普通字段 -->
        <#else>
            <#if  (field.propertyName !="aaa")>
                <#if field.comment!?length gt 0>
                    <#if swagger2>
                        @ApiModelProperty(value = "${field.comment}")
                    <#else>
                        /**
                        * ${field.comment}
                        */
                    </#if>
                </#if>
                private ${field.propertyType} ${field.propertyName};
            </#if>
        </#if>

    </#list>
    <#------------  END 字段循环遍历  ---------->
    }

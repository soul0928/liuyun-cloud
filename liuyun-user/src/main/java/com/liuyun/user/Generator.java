package com.liuyun.user;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/7 14:10
 **/
public class Generator {

    public static void main(String[] args) {
        String moduleName = "auth";
        String []tables = {
                "sys_role",
                "sys_permission",
                "sys_role_permission",
                "sys_user_role"
        };
        com.liuyun.core.mybatisplus.Generator.generatorTables(moduleName, tables);
    }

}

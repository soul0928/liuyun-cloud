package com.liuyun.user;

/**
 * @author wangdong
 * @version 1.0.0
 * @date 2020/12/7 14:10
 **/
public class Generator {

    public static void main(String[] args) {
        String moduleName = "user";
        String []tables = {
                "sys_user",
        };
        com.liuyun.core.mybatisplus.Generator.generatorTables(moduleName, tables);
    }

}

package com.liuyun.utils.result;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ResponseUtil
 *
 * @author wangdong
 * @date 2020/12/14 5:36 下午
 **/
@Slf4j
public class ResponseUtil {

    /**
     *  使用response输出JSON
     * @param response response
     * @param result result
     */
    @SuppressWarnings("all")
    public static void out(HttpServletResponse response, Result result){
        ServletOutputStream out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            out = response.getOutputStream();
            out.write(JSONUtil.toJsonStr(result).getBytes());
            IoUtil.flush(out);
        } catch (IOException e) {
            log.error("响应参数异常", e);
        } finally{
            IoUtil.close(out);
        }
    }
}

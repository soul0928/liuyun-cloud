package com.liuyun.utils.captcha;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.GifCaptcha;
import cn.hutool.captcha.ShearCaptcha;

/**
 * 图形验证码工具
 *
 * @author wangdong
 * @version 1.0.0
 * @date 2021/1/10 18:28
 **/
public class CaptchaUtils extends CaptchaUtil {

    private static final int DEFAULT_WIDTH = 150;
    private static final int DEFAULT_HEIGHT = 50;
    private static final int DEFAULT_CODE_COUNT = 4;
    private static final int DEFAULT_THICKNESS = 2;

    public static ShearCaptcha createShearCaptcha() {
        return CaptchaUtil.createShearCaptcha(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_CODE_COUNT, DEFAULT_THICKNESS);
    }

    public static GifCaptcha createGifCaptcha() {
        return CaptchaUtil.createGifCaptcha(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_CODE_COUNT);
    }

}

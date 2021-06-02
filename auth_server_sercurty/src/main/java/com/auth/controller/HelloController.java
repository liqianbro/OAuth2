package com.auth.controller;

import cn.hutool.core.util.RandomUtil;
import com.auth.utils.MD5Util;
import com.auth.utils.RandImageUtil;
import com.auth.utils.Result;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lee
 * @date 2021/5/24 1:49 下午
 */
@RestController
public class HelloController {

    private static final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";


    @GetMapping("/hello")
    public String Hello() {
        return "Hello Spring!!!";
    }

    @PostMapping("/login")
    public String Login() {
        return "";
    }

    /**
     * 获取验证码
     *
     * @return void
     * @author lee
     * @date 2021/3/12 4:16 下午
     */
    @GetMapping("/user/loginCode")
    public Result getCode(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        try {
            val code = RandomUtil.randomString(BASE_CHECK_CODES, 4);
            val lowerCaseCode = code.toLowerCase();
            val realKey = MD5Util.MD5Encode(lowerCaseCode, "utf-8");
            //存储redis
            session.setAttribute("verify_code", lowerCaseCode);
            //redisTemplate.opsForValue().set(realKey, lowerCaseCode, 1, TimeUnit.MINUTES);
            val base64 = RandImageUtil.generate(code);
            result.put("image", base64);
            result.put("code", lowerCaseCode);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取验证码错误:" + e);
        }
        return Result.ok(result);
    }


}

package com.oauth.controller;

import cn.hutool.core.util.RandomUtil;
import com.oauth.pojo.vo.LoginVO;
import com.oauth.utils.MD5Util;
import com.oauth.utils.RandImageUtil;
import com.oauth.utils.Result;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.util.ObjectUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lee
 * @date 2021/5/25 11:57 上午
 */
@RestController
public class HelloController {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    TokenEndpoint tokenEndpoint;

    private static final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";


    @GetMapping("/hello")
    public String Hello() {
        return "Hello，资源服务器";
    }

    /**
     * Oauth2登录认证
     *
     * @author lee
     */
    @PostMapping(value = "/user/login")
    public Result postAccessToken(@RequestBody LoginVO loginVO) throws HttpRequestMethodNotSupportedException {
        val code = MD5Util.MD5Encode(loginVO.getCode().toLowerCase(), "utf-8");
        val isFlag = redisTemplate.opsForValue().get(code);
        if (ObjectUtils.isEmpty(isFlag)) {
            return Result.error("验证码获取错误,请重新获取！！！");
        }
        String verify_code = isFlag.toString();
        if (loginVO.getCode() == null || verify_code == null || "".equals(loginVO.getCode()) || !verify_code.toLowerCase().equals(loginVO.getCode().toLowerCase())) {
            return Result.error("验证码错误");
        }
        User clientUser = new User("TechPower", "techpower", new ArrayList<>());
        //生成已经认证的client
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(clientUser, null, new ArrayList<>());
        //封装参数
        Map<String, String> parameters = new HashMap();
        //封装成一个UserPassword方式的参数体，放入手机号
        parameters.put("username", loginVO.getUsername());
        //放入验证码
        parameters.put("password", loginVO.getPassword());
        //授权模式为：密码模式
        parameters.put("grant_type", "password");
        //调用AccessToken
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(token, parameters).getBody();
        Map<String, Object> result = new HashMap<>(1);
        result.put("token", oAuth2AccessToken.getValue());
        redisTemplate.opsForValue().set(oAuth2AccessToken.getValue(), oAuth2AccessToken.getValue(), 7, TimeUnit.DAYS);
        return Result.ok(result);
    }


    /**
     * 获取验证码
     *
     * @return void
     * @author lee
     * @date 2021/3/12 4:16 下午
     */
    @GetMapping("/user/loginCode")
    public Result getCode() {
        Map<String, Object> result = new HashMap<>();
        try {
            val code = RandomUtil.randomString(BASE_CHECK_CODES, 4);
            val lowerCaseCode = code.toLowerCase();
            val realKey = MD5Util.MD5Encode(lowerCaseCode, "utf-8");
            //存储redis
            redisTemplate.opsForValue().set(realKey, lowerCaseCode, 1, TimeUnit.MINUTES);
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

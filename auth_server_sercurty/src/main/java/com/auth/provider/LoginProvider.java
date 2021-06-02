package com.auth.provider;

import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author lee
 * @date 2021/5/24 4:13 下午
 */

public class LoginProvider extends DaoAuthenticationProvider {
    @SneakyThrows
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        //自定义认证逻辑
        System.out.println(userDetails.toString());
        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}

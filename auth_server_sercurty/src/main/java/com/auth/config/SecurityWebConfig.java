package com.auth.config;

import com.auth.filters.LoginFilter;
import com.auth.provider.LoginProvider;
import com.auth.service.UserDetailServiceImpl;
import com.auth.utils.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;
import java.util.Arrays;

/**
 * 安全配置中心
 *
 * @author lee
 * @date 2021/5/24 2:10 下午
 */
@Configuration
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailServiceImpl userDetailService;
    @Autowired
    ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //所有请求都必须经过认证
                .authorizeRequests()
                .antMatchers("/user/loginCode").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write(objectMapper.writeValueAsString(Result.error("请先登陆～")));
                })
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write(objectMapper.writeValueAsString(Result.error(authException.getMessage() + "请检查是否有权限～")));
                })
                .and()
                .csrf().disable();
        http.addFilterAfter(loginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
  /*
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        ProviderManager providerManager = new ProviderManager(Arrays.asList(loginProvider()));
        return providerManager;
    }

    @Bean
    LoginProvider loginProvider() {
        LoginProvider loginProvider = new LoginProvider();
        loginProvider.setUserDetailsService(userDetailService);
        loginProvider.setPasswordEncoder(passwordEncoder());
        return loginProvider;
    }


    /**
     * 过滤器方式
     *
     * @param
     * @return com.auth.filters.LoginFilter
     * @author lee
     * @date 2021/5/24 4:18 下午
     */
    @Bean
    LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationManager(authenticationManager());
        loginFilter.setFilterProcessesUrl("/jsonLogin");
        loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            String s = new ObjectMapper().writeValueAsString(Result.ok(authentication.getPrincipal()));
            out.write(s);
            out.flush();
            out.close();
        });
        loginFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write(new ObjectMapper().writeValueAsString(Result.ok(exception.getMessage())));
            out.flush();
            out.close();
        });
        return loginFilter;
    }


    /**
     * <p>
     * 不可逆密码加密配置
     * </p>
     *
     * @param
     * @return org.springframework.security.crypto.password.PasswordEncoder
     * @Author Lee
     * @date 2021/3/13 6:23 下午
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package com.oauth.config;

import com.oauth.service.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lee
 * @date 2021/5/25 10:56 上午
 */
@AllArgsConstructor
@Configuration
@EnableAuthorizationServer
public class OAuth2WebConfig extends AuthorizationServerConfigurerAdapter {
    PasswordEncoder passwordEncoder;
    UserDetailServiceImpl userDetailService;
    AuthenticationManager authenticationManager;
    JwtTokenEnhancer jwtTokenEnhancer;

    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束.
     *
     * @param security
     * @return void
     * @author lee
     * @date 2021/5/25 11:04 上午
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许表单提交
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()")
                .tokenKeyAccess("permitAll()");
    }

    /**
     * 用来配置客户端详情服务（ClientDetailsService），
     * 客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
     *
     * @param clients
     * @return void
     * @author lee
     * @date 2021/5/25 11:03 上午
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //super.configure(clients);
        clients.inMemory()
                .withClient("TechPower")
                .secret(passwordEncoder.encode("techpower"))
                .scopes("all")
                //认证类型
                .authorizedGrantTypes("password")
                //资源Id
                .resourceIds("test")
                //TOKEN时效
                .accessTokenValiditySeconds(604800);
    }

    /**
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
     *
     * @param endpoints
     * @return void
     * @author lee
     * @date 2021/5/25 11:04 上午
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer);
        delegates.add(jwtAccessTokenConverter());
        enhancerChain.setTokenEnhancers(delegates); //配置JWT的内容增强器

        endpoints
                //配置管理器
                .authenticationManager(authenticationManager)
                //设置用户
                .userDetailsService(userDetailService) //配置加载用户信息的服务
                //设置token
                .accessTokenConverter(jwtAccessTokenConverter())
                //设置增强token
                .tokenEnhancer(enhancerChain)
                //设置jwt
                .tokenStore(jwtTokenStore())
                //设置刷新token非重复使用
                .reuseRefreshTokens(false);
    }


    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("LiQian");
        return converter;
    }

    @Bean
    public JwtTokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }


}

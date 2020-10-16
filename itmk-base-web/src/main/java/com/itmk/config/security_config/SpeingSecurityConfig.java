package com.itmk.config.security_config;

import com.itmk.security.detailservice.CustomerUserDetailsService;
import com.itmk.security.filte.CheckTokenFilter;
import com.itmk.security.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security配置类
 */
@Configuration   //表示该类是一个配置类
@EnableWebSecurity //启用Spring Security
public class SpeingSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    private CustomAccessDeineHandler customAccessDeineHandler;
    @Autowired
    private CustomizeAuthenticationEntryPoint customizeAuthenticationEntryPoint;
    @Autowired
    private LoginFailureHandler loginFailureHandler;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private CheckTokenFilter checkTokenFilter;
    @Autowired
    private CustomerLogoutSuccessHandler customerLogoutSuccessHandler;

    /**
     * 配置自定义UserDetailsService
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerUserDetailsService);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 明文+随机盐值》加密存储
        return new BCryptPasswordEncoder();
    }
    /**
     * 陪权限资源和自定义认证成功和失败管理器
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.addFilterBefore(checkTokenFilter, UsernamePasswordAuthenticationFilter.class)
               .formLogin()
                .loginProcessingUrl("/api/user/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
            .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers("/api/user/login","/api/user/image").permitAll()
                .anyRequest().authenticated()
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(customizeAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeineHandler)
            .and()
                .logout().logoutUrl("/api/user/loginOut").logoutSuccessHandler(customerLogoutSuccessHandler);
    }
}

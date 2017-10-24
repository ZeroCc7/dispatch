package com.wlwx.dispatch.config;

import com.wlwx.dispatch.auth.CustomAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * FILE: com.eumji.zblog.config.WebSecurityAdapter.java
 * MOTTO:  不积跬步无以至千里,不积小流无以至千里
 * AUTHOR: EumJi
 * DATE: 2017/4/9
 * TIME: 9:33
 */
@Configuration
@EnableWebSecurity
public class WebSecurityAdapter extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.authorizeRequests()//配置安全策略
//                .antMatchers("/").permitAll()//定义/请求不需要验证
//                .anyRequest().authenticated()//其余的所有请求都需要验证
//                .and().logout().permitAll()//定义logout不需要验证
//                .and().formLogin();//使用form表单登录

        http.csrf().disable();//配置安全策略
        http.authorizeRequests().antMatchers("/","/login/**","/login/auth").permitAll()//定义/请求不需要验证
                .antMatchers("/index/**","/user/**").authenticated() //其余的所有请求都需要验证
                .and().rememberMe().tokenValiditySeconds(3600)
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/index").permitAll()
                .and().logout().logoutUrl("/admin/loginOut").permitAll();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**/*.*");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        AuthenticationProvider authenticationProvider = new CustomAuthenticationProvider();
        return authenticationProvider;
    }
}

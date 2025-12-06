package com.food.recipe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security配置
 * @author prxd0527
 * @date 2025-12-07
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 关闭CSRF
                .csrf().disable()
                // 关闭Session，使用JWT
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 配置权限
                .authorizeRequests()
                // 允许所有人访问认证接口
                .antMatchers("/api/auth/**").permitAll()
                // 允许访问Swagger文档
                .antMatchers("/doc.html", "/webjars/**", "/swagger-resources/**", "/v2/**").permitAll()
                // 允许访问上传的图片
                .antMatchers("/upload/**").permitAll()
                // 其他请求需要认证
                .anyRequest().authenticated()
                .and()
                // 关闭默认的登录页面
                .formLogin().disable()
                .httpBasic().disable()
                // 允许跨域
                .cors();

        return http.build();
    }

}

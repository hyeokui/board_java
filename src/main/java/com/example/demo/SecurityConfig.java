package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .formLogin().disable() // FormLogin 사용 X
                .httpBasic().disable() // httpBasic 사용 X
                .csrf().disable() // csrf 보안 사용 X
                .sessionManagement().sessionFixation().none()
                .and()
                .headers().frameOptions().disable()
                .and()

                // 세션 사용하지 않으므로 STATELESS로 설정
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()

                //== URL별 권한 관리 옵션 ==//
                .authorizeRequests()

                // 아이콘, css, js 관련
                // 기본 페이지, css, image, js 하위 폴더에 있는 자료들은 모두 접근 가능, h2-console에 접근 가능
                .antMatchers("/**").permitAll()
                .antMatchers("/","/css/**","/images/**","/js/**","/favicon.ico","/h2-console/**").permitAll()
                .antMatchers("/signup").permitAll() // 회원가입 접근 가능
                .anyRequest().authenticated(); // 위의 경로 이외에는 모두 인증된 사용자만 접근 가능



        http.logout() // 로그아웃 처리
                .logoutSuccessUrl("/main") // 로그아웃 성공 URL
                .invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID") // 로그아웃 성공 시 제거할 쿠키명
        ;


        http.sessionManagement()
                .maximumSessions(1) // -1 무제한
                .maxSessionsPreventsLogin(false) // true:로그인 제한 false(default):기존 세션 만료
                .expiredUrl("/user/login") // 세션 만료
        ;

        http.sessionManagement()
                .sessionFixation().changeSessionId() // default 세션 공격 보호
        ;


        return http.build();
    }
}

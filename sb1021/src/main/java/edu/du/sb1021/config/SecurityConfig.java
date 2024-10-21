package edu.du.sb1021.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration  // 이 클래스는 Spring 설정 클래스임을 나타냅니다.
@EnableGlobalMethodSecurity(prePostEnabled = true)  // 메서드 수준에서의 보안 설정을 활성화합니다. // prePostEnabled = true는 @PreAuthorize 및 @PostAuthorize 어노테이션을 사용 가능하게 합니다.
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user1")
                .password(passwordEncoder().encode("1234"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }


    @Bean  // Spring 컨텍스트에 이 메서드의 반환값을 Bean으로 등록합니다.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // HttpSecurity 객체를 통해 HTTP 요청에 대한 보안 설정을 정의합니다.
        http.authorizeHttpRequests()  // HTTP 요청의 인가 규칙을 정의합니다.
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()// 특정 경로에 대한 요청은 누구나 접근 가능합니다.
                .anyRequest().authenticated();  // 나머지 모든 요청은 인증이 필요합니다.
        http.formLogin(); // 기본 제공되는 폼 로그인 기능을 활성화합니다.
        // 사용자는 로그인 페이지로 리다이렉트되며, 성공적으로 로그인하면 인증이 완료됩니다.
        // 기본 경로는 "/login"이며, Spring Security에서 제공하는 기본 로그인 페이지가 사용됩니다.
        http.csrf().disable();
        return http.build();  // 설정된 보안 필터 체인을 빌드하여 반환합니다.
    }
}

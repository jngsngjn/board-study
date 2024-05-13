package hello.boardstudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index.html", "/css/**", "/js/**").permitAll()

                // 글쓰기, 삭제, 수정 -> 인증된 사용자만
                .requestMatchers("/boards/write", "/boards/{boardId}/delete", "/boards/{boardId}/edit").authenticated()

                // 게시판 페이지, 특정 게시글, 회원가입 -> 모든 사용자
                .requestMatchers("/boards", "/boards/{boardId}", "/register/**", "/send-verification-email", "/verify/**", "/verifyError").permitAll()
                .anyRequest().authenticated()
        );

        http.formLogin(auth -> auth

                // 로그인 페이지 경로 (GET)
                .loginPage("/login")

                // 로그인 처리 경로 (POST)
                .loginProcessingUrl("/login")

                // 로그인 성공 시 경로 (GET)
                .defaultSuccessUrl("/boards", true)
                .permitAll()
        );

        http.logout(auth -> auth
                // 로그아웃 경로 (POST)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/boards")
                .permitAll(false)
        );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
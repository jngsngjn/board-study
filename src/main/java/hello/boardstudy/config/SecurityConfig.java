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
                .requestMatchers("/", "/index.html").permitAll()
                .requestMatchers("/boards/write", "/boards/{boardId}/delete", "/boards/{boardId}/edit").authenticated()
                .requestMatchers("/boards", "/boards/{boardId}", "/register").permitAll()
                .anyRequest().authenticated()
        );

        http.formLogin(auth -> auth.loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/boards", true)
                .permitAll()
        );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
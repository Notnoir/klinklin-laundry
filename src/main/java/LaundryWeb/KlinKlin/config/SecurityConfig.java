package LaundryWeb.KlinKlin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // semua endpoint bisa diakses bebas
            )
            .formLogin(form -> form.disable()) // nonaktifkan form login
            .httpBasic(httpBasic -> httpBasic.disable()); // nonaktifkan basic auth

        return http.build();
    }
}

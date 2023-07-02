package fsd.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions().disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/static/**", "/css/**", "/js/**", "/img/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/trainings/**").authenticated()
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/match/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/players/**").permitAll()
                        .requestMatchers("/player/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> {
                            try {
                                form
                                        .loginPage("/login")
                                        .loginProcessingUrl("/login")
                                        .usernameParameter("email")
                                        .passwordParameter("password")
                                        .defaultSuccessUrl("/home", true)
                                        .failureUrl("/login?error")
                                        .permitAll()
                                        .and()
                                        .logout()
                                        .logoutSuccessUrl("/");
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                );

        return http.build();
    }

}

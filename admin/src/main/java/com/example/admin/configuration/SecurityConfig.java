package com.example.admin.configuration;

import com.example.admin.handler.CustomAuthenticationSuccessHandler;
import com.example.admin.repository.AdminRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {
    private final DataSource dataSource;
    private final AdminRepository adminRepository;

    public SecurityConfig(DataSource dataSource, AdminRepository adminRepository) {
        this.dataSource = dataSource;
        this.adminRepository = adminRepository;
    }
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests((authorize) -> authorize
                            .requestMatchers("/assets/**", "/pages/**", "/admin/login/2fa").permitAll()
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .requestMatchers("/admin/login/2fa").hasRole("PRE_AUTH_ADMIN")
                            .anyRequest().authenticated()
                    )
                    .formLogin((form) -> form
                            .loginPage("/admin/login")
                            .successHandler(authenticationSuccessHandler())
                            .permitAll()
                    )
                    .rememberMe((rm) -> rm
                            .tokenRepository(persistentTokenRepository()))
                    .logout((logout) -> logout
                            .logoutUrl("/admin/logout")
                            .permitAll());
            return http.build();
        }


        public PersistentTokenRepository persistentTokenRepository() {
            JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
            jdbcTokenRepository.setDataSource(dataSource);
            return jdbcTokenRepository;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationSuccessHandler authenticationSuccessHandler() {
            return new CustomAuthenticationSuccessHandler(adminRepository);
        }
}

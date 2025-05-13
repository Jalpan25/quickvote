package com.example.QuickVote.config;

import com.example.QuickVote.security.JwtAuthFilter;
import com.example.QuickVote.service.AdminDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private AdminDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 🔓 Public endpoints (permitAll)
                        .requestMatchers(
                                "/api/admins/register",
                                "/api/admins/login",
                                "/api/admins/pending",
                                "/auth/send-otp",
                                "/auth/verify-otp"
                        ).permitAll()

                        // 🔐 SuperAdmin-only endpoints
                        .requestMatchers(
                                "/api/admins/approved",
                                "/api/admins/process",
                                "/api/admins/getFixedDomain"
                        ).hasRole("SUPERADMIN")

                        // 🔐 User & Admin access
                        .requestMatchers("/api/responses/fetch").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/survey-results/results").hasAnyRole("USER", "ADMIN")

                        // 🔐 Admin-only access
                        .requestMatchers("/api/surveys/fetch-by-admin").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/surveys").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/surveys/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/surveys/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/surveys/{id}").hasRole("ADMIN") // For any other mappings like PUT/POST etc.

                        // 🔐 User-only access
                        .requestMatchers("/api/surveys/filter-by-email").hasRole("USER")
                        .requestMatchers("/api/surveys/{surveyId}/responses").hasRole("USER")
                        .requestMatchers("/api/surveys/{id}/questions").hasRole("USER")

                        // 🔒 All other requests require authentication
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }



    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



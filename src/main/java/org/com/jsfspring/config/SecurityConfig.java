package org.com.jsfspring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.com.jsfspring.security.UserAuthenticationEntryPoint;
import org.com.jsfspring.security.jwt.JWTAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final UserAuthenticationEntryPoint authenticationEntryPoint;

    final String[] WHITELISTED_ENDPOINTS = {
            "/users/auth",
            "/users/encrypt"
    };

    final String[] ADMIN_ENDPOINTS = {
            "/users/all",
            "/users/add-user",
            "/payment/all",
            "/payment/add",
            "/payment/get-all-sum-payment",
            "/payment/get-sum-loan",
            "/attendance/all",
            "/attendance/add",
            "/attendance/delete/**",
            "/attendance/update",
            "/attendance/get-sum-loan-history",
            "/attendance/get-sum-attendance"
    };

    final String[] USER_ENDPOINTS = {
            "/users/update-pass",
            "/payment/get-current-user-payments",
            "/attendance/get-current-user-attendances",
            "/attendance/get-current-user-loan-sum",
            "/attendance/get-current-user-paid-sum",
            "/attendance/get-current-user-left"
    };

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf()
                .disable()
                .cors()
                .and()
                .authorizeRequests() //authorizeHttpRequests
                .antMatchers(WHITELISTED_ENDPOINTS).permitAll()
                .antMatchers(ADMIN_ENDPOINTS).hasRole("ADMIN")
                .antMatchers(USER_ENDPOINTS).hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

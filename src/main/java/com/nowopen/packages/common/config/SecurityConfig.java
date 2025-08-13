package com.nowopen.packages.common.config;

import com.nowopen.packages.common.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {"/api/v1/auth/**"};
    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(CsrfConfigurer::disable)
                .cors((configurer) -> configurer.configurationSource(corsConfigurationSource()))
                .httpBasic(HttpBasicConfigurer::disable)
                // set exception handler
//                .exceptionHandling(
//                        (exceptionHandler)
//                                -> exceptionHandler
//                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                                .accessDeniedHandler(jwtAccessDeniedHandler)
//                )
                // crossOrigin
                .headers((header) -> {
                    header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
                })
                // we don't use the session so make it stateless
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // set access limitation on requests using HttpServletRequest
                .authorizeHttpRequests(authorize -> authorize
                        // allow to access if it's in white list
                        .requestMatchers(AUTH_WHITELIST)
                        .permitAll()
                        // otherwise all the requests should be authenticated
                        .anyRequest()
                        .authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization","Accept","Cache-Control","Content-Type", "Origin","x-csrf-token","x-requested-with"));
        configuration.setExposedHeaders(List.of("Content-Disposition"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}

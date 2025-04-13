package com.duox.escapenest.configuration;

import com.duox.escapenest.constant.ResultCode;
import com.duox.escapenest.dto.response.valueObject.ResultMessage;
import com.duox.escapenest.util.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomJwtDecoder customJwtDecoder;
    @Autowired
    private ObjectMapper objectMapper;
    private static String[] PUBLIC_ENDPOINT = {
            "/account/register",
            "/auth/login"
    };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("https://localhost:8080"));
                    configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
                    configuration.setAllowedHeaders(List.of("*"));
                    configuration.setExposedHeaders(List.of("Authorization","Content-Type"));
                    configuration.setAllowCredentials(true);
                    return configuration;
                }))
                .csrf(AbstractHttpConfigurer:: disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINT).permitAll()
                        .requestMatchers(HttpMethod.GET,PUBLIC_ENDPOINT).permitAll()
                        .requestMatchers(HttpMethod.PUT,PUBLIC_ENDPOINT).permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(customJwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                                .authenticationEntryPoint((request, response, authException) -> {
                                    handleError(response, ResultCode.UNAUTHENTICATED);
                                })
                                .accessDeniedHandler(((request, response, accessDeniedException) ->
                                        handleError(response, ResultCode.UNAUTHENTICATED)))
                );
        return httpSecurity.build();
    }
    private void handleError(HttpServletResponse response, ResultCode resultCode) throws IOException {
        response.setStatus(resultCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ResultMessage<?> resultMessage = ResultUtil.error(resultCode);
        objectMapper.writeValue(response.getWriter(), resultMessage);
    }
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

}

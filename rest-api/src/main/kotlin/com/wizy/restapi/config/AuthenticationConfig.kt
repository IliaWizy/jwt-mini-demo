package com.wizy.restapi.config

import com.wizy.restapi.config.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class AuthenticationConfig(
        private val authenticationProvider: AuthenticationProvider
)
{

    @Bean
    fun securityFilterChain(
            http: HttpSecurity, jwtAuthenticationFilter: JwtAuthenticationFilter
    ): DefaultSecurityFilterChain =
            http.csrf { it.disable() }
                .authorizeHttpRequests {
                    it.requestMatchers("/auth/sign-in", "/auth/refresh", "/error")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/user")
                        .permitAll()
                        .requestMatchers("/user**", "/material**")
                        .hasRole("ADMIN")
                        .anyRequest()
                        .fullyAuthenticated()

                }
                .sessionManagement {
                    it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                }
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
                .build()
}

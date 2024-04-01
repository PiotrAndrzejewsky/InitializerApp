package com.initializer.app.security

import com.initializer.app.security.interceptor.HandlerMethodService
import com.initializer.app.security.interceptor.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFlux
class WebConfig(
    val jwtUtils: JwtUtils,
    val handlerMethodService: HandlerMethodService
): WebFluxConfigurer {

    @Bean
    fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(jwtUtils, handlerMethodService)
    }
}
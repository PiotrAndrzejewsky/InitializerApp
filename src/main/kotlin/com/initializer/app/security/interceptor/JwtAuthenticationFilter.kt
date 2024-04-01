package com.initializer.app.security.interceptor

import com.initializer.app.security.JwtUtils
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
@Order(1)
class JwtAuthenticationFilter(
    val jwtUtils: JwtUtils,
    val handlerMethodService: HandlerMethodService
) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        return handlerMethodService.hasAllowedPathAnnotation(exchange)
            .flatMap { hasAnnotation ->
                if (hasAnnotation) {
                    chain.filter(exchange)
                } else {
                    val authorizationHeader = exchange.request.headers.getFirst("Authorization")
                    verifyHeader(authorizationHeader, exchange, chain)
                }
            }
    }

    private fun verifyHeader(header: String?, exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        if (header == null || !header.startsWith("Bearer ")) {
            return unauthorized(exchange, "Authorization token is missing")
        }

        val token = header.substring(7)
        if (!jwtUtils.verifyToken(token)) {
            return unauthorized(exchange, "Invalid or expired token")
        }
        return chain.filter(exchange)
    }

    private fun unauthorized(exchange: ServerWebExchange, message: String): Mono<Void> {
        exchange.response.statusCode = HttpStatus.UNAUTHORIZED
        return exchange.response.writeWith(Mono.just(exchange.response.bufferFactory().wrap(message.toByteArray())))
    }
}
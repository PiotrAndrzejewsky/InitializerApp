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
    val listOfAllowedPaths: List<String>,
    val jwtUtils: JwtUtils
) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val path = exchange.request.path.pathWithinApplication().value()

        if (listOfAllowedPaths.contains(path)) {
            return chain.filter(exchange)
        }

        if (path == "/refreshToken") {
            val refreshHeader = exchange.request.headers.getFirst("RefreshToken")
            return verifyHeader(refreshHeader, exchange, chain)
        }

        val authorizationHeader = exchange.request.headers.getFirst("Authorization")
        return verifyHeader(authorizationHeader, exchange, chain)
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
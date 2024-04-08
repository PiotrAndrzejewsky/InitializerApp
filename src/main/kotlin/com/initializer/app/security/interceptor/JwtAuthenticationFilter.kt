package com.initializer.app.security.interceptor

import com.initializer.app.exceptions.ProcessException
import com.initializer.app.security.JwtUtils
import org.springframework.core.annotation.Order
import org.springframework.core.io.buffer.DataBufferUtils
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
            .onErrorResume(ProcessException::class.java) { e ->
                handleProcessException(e, exchange)
            }
    }

    private fun verifyHeader(header: String?, exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        if (header == null || !header.startsWith("Bearer ")) {
            return Mono.error(ProcessException("Authorization token is missing", status = HttpStatus.UNAUTHORIZED))
        }

        val token = header.substring(7)
        if (!jwtUtils.verifyToken(token)) {
            return Mono.error(ProcessException("Invalid or expired token", status = HttpStatus.UNAUTHORIZED))
        }
        return chain.filter(exchange)
    }

    private fun handleProcessException(ex: ProcessException, exchange: ServerWebExchange): Mono<Void> {
        exchange.response.statusCode = ex.status
        val bufferFactory = exchange.response.bufferFactory()
        val dataBuffer = bufferFactory.wrap(ex.message.toByteArray(Charsets.UTF_8))
        return exchange.response.writeWith(Mono.just(dataBuffer))
            .doOnError { DataBufferUtils.release(dataBuffer) }
    }
}
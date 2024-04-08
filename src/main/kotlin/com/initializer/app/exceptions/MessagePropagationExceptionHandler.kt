package com.initializer.app.exceptions

import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@ControllerAdvice
class MessagePropagationExceptionHandler : ResponseEntityExceptionHandler() {

    companion object {
        private val LOG = KotlinLogging.logger { }
    }

    @ExceptionHandler(value = [ProcessException::class])
    fun handleProcessException(
        ex: ProcessException, request: ServerWebExchange
    ): Mono<ResponseEntity<Any>> {
        val requestBody = ProcessException(ex.message)
        requestBody.stackTrace = arrayOf()
        LOG.error { "Process Exception was thrown with exception message: ${ex.message}" }

        return handleExceptionInternal(
            ex, requestBody,
            HttpHeaders(), ex.status ?: HttpStatus.INTERNAL_SERVER_ERROR, request
        )
    }
}
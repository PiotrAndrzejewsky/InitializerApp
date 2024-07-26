package com.initializer.app.security.interceptor

import com.initializer.app.security.AllowPath
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service
import org.springframework.web.method.HandlerMethod
import org.springframework.web.reactive.result.method.RequestMappingInfoHandlerMapping
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Service
class HandlerMethodService {

    @Autowired
    lateinit var applicationContext: ApplicationContext

    fun hasAllowedPathAnnotation(exchange: ServerWebExchange): Mono<Boolean> {
        val requestMappingHandlerMapping = applicationContext.getBean(RequestMappingInfoHandlerMapping::class.java)
        return requestMappingHandlerMapping.getHandler(exchange)
            .map { handler ->
                if (handler is HandlerMethod) {
                    handler.hasMethodAnnotation(AllowPath::class.java)
                } else {
                    false
                }
            }.defaultIfEmpty(false)
    }
}
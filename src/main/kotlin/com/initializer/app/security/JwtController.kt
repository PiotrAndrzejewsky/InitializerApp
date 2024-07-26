package com.initializer.app.security

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class JwtController(
    val jwtUtils: JwtUtils
) {

    @PostMapping("/refreshToken")
    @AllowPath
    fun refreshToken(@RequestBody username: String): Flux<JwtUtils.JwtTokens> {
        return jwtUtils.generateTokenPair(username)
    }

    @PostMapping("/test")
    @AllowPath
    fun test(): String {
        return "TEST"
    }
}

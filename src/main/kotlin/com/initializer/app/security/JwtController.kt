package com.initializer.app.security

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class JwtController(
    val jwtUtils: JwtUtils
) {

    @PostMapping("/refreshToken")
    fun refreshToken(username: String): JwtUtils.JwtTokens {
        return jwtUtils.generateTokenPair(username)
    }

    @PostMapping("/test")
    fun test(): String {
        return "TEST"
    }
}

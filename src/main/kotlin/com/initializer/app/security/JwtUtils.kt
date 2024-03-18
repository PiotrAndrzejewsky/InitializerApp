package com.initializer.app.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.util.Date

@Configuration
class JwtUtils(
    @Value("\${access-expiration-time}")
    private val ACCESS_EXPIRATION_TIME_MS: Int,

    @Value("\${refresh-expiration-time}")
    private val REFRESH_EXPIRATION_TIME_MS: Int
) {
    private val SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512)

    private fun generateToken(username: String, expirationTime: Int): String {
        val expirationDate = Date(System.currentTimeMillis() + expirationTime)
        return Jwts.builder()
            .setSubject(username)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
            .compact()
    }

    fun verifyToken(token: String): Boolean {
        return try {
            extractUsernameFromToken(token) != null
        } catch (e: Exception) {
            false
        }
    }

    private fun extractUsernameFromToken(token: String): String? {
        return try {
            val claims: Claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).body
            claims.subject
        } catch (e: Exception) {
            null
        }
    }

    fun generateTokenPair(username: String): JwtTokens {
        val accessToken = generateToken(username, ACCESS_EXPIRATION_TIME_MS)
        val refreshToken = generateToken(username, REFRESH_EXPIRATION_TIME_MS)
        return JwtTokens(accessToken, refreshToken)
    }

    data class JwtTokens (
        val accessToken: String,
        val refreshToken: String
    )
}

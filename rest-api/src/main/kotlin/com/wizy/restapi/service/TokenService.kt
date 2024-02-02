package com.wizy.restapi.service

import com.wizy.restapi.config.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService
(
        jwtProperties: JwtProperties
)
{
    private val secretKey =
            Keys.hmacShaKeyFor(jwtProperties.key.toByteArray())


    fun generate(
            userDetails: UserDetails,
            expirationDate: Date,
            additionalClaims: Map<String, Any> = emptyMap()
    ): String =
            Jwts.builder()
                .claims()
                .subject(userDetails.username)
                .issuedAt(Date(System.currentTimeMillis()))
                .expiration(expirationDate)
                .add(additionalClaims)
                .and()
                .signWith(
                        secretKey, Jwts.SIG.HS256
                )
                .compact()


    fun <T> extractClaim(
            token: String,
            resolver: (Claims) -> T
    ): T? =
            resolver(extractAllClaims(token))


    fun isExpired(token: String): Boolean =
            extractClaim(token) { it.expiration }
                ?.before(Date(System.currentTimeMillis()))
                ?: false


    fun isValid(
            token: String,
            userDetails: UserDetails
    ): Boolean =
            extractClaim(token) { it.subject }
                ?.let { it == userDetails.username && !isExpired(token) }
                ?: false


    private fun extractAllClaims(token: String): Claims
    {
        val parser =
                Jwts.parser()
                    .verifyWith(secretKey)
                    .build()

        return parser.parseSignedClaims(token).payload
    }
}

package com.wizy.restapi.service

import com.wizy.restapi.config.JwtProperties
import com.wizy.restapi.controller.auth.AuthenticationRequest
import com.wizy.restapi.controller.auth.AuthenticationResponse
import com.wizy.restapi.model.UserPrincipal
import com.wizy.restapi.repository.RefreshTokenRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
        private val authenticationManager: AuthenticationManager,
        private val userDetailsService: UserDetailsService,
        private val tokenService: TokenService,
        private val jwtProperties: JwtProperties,
        private val refreshTokenRepository: RefreshTokenRepository
)
{
    fun authentication(authRequest: AuthenticationRequest): AuthenticationResponse
    {
        // проверяем на существование пользователя и аутентифицируем.
        // возвращает заполненный Authentication или throw 403
        authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        authRequest.email, authRequest.password
                )
        )

        // TODO: Разобраться как обойти as UserPrincipal
        val currentUser = userDetailsService.loadUserByUsername(authRequest.email) as UserPrincipal
        val accessToken = generateAccessToken(currentUser)
        val refreshToken = generateRefreshToken(currentUser)
        refreshTokenRepository.save(refreshToken, currentUser.id)

        return AuthenticationResponse(
                accessToken = accessToken, refreshToken = refreshToken
        )
    }


    fun refreshAccessToken(refreshToken: String): AuthenticationResponse
    {
        return tokenService.extractClaim(refreshToken) { it.subject }
            ?.let { email ->
                val userPrincipal =
                        userDetailsService.loadUserByUsername(email) as UserPrincipal
                val uuid = refreshTokenRepository.findByToken(refreshToken)

                if (!tokenService.isExpired(refreshToken) && userPrincipal.id != uuid)
                {
                    return@let null
                }

                return AuthenticationResponse(
                        accessToken = generateAccessToken(userPrincipal),
                        refreshToken = generateRefreshToken(userPrincipal)
                )
            }
            ?: throw IllegalArgumentException("Invalid refresh token")
    }


    private fun generateAccessToken(currentUser: UserDetails) =
            tokenService.generate(
                    userDetails = currentUser,
                    expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
            )

    private fun generateRefreshToken(currentUser: UserDetails) =
            tokenService.generate(
                    userDetails = currentUser,
                    expirationDate = Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
            )

}

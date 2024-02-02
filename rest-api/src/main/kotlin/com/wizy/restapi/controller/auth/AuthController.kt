package com.wizy.restapi.controller.auth

import com.wizy.restapi.service.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
        private val authenticationService: AuthenticationService
)
{

    /*
    * Вход
    *  */
    @PostMapping("/sign-in")
    fun authenticate(
            @RequestBody
            authRequest: AuthenticationRequest
    ): AuthenticationResponse =
            authenticationService.authentication(authRequest)

    @PostMapping("/refresh")
    fun refreshAccessToken(
            @RequestBody
            request: RefreshTokenRequest
    ): AuthenticationResponse =
            authenticationService.refreshAccessToken(request.refreshToken)
}

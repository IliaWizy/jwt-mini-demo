package com.wizy.restapi.controller.auth

data class AuthenticationResponse(
        val accessToken: String,
        val refreshToken: String
)

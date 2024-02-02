package com.wizy.restapi.controller.auth

data class AuthenticationRequest(
        val email: String, val password: String
)

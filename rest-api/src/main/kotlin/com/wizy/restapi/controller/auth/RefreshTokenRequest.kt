package com.wizy.restapi.controller.auth

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class RefreshTokenRequest @JsonCreator constructor(
        @JsonProperty("refreshToken")
        val refreshToken: String
)

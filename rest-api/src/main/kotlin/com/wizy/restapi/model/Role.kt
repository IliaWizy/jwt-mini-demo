package com.wizy.restapi.model

enum class Role(private val role: String)
{
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    val authority: String
        get() = this.role
}

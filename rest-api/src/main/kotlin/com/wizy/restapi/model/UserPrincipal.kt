package com.wizy.restapi.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

data class UserPrincipal(
        val id: UUID,
        private val email: String,
        private val password: String,
        private val role: Role
) : UserDetails
{
    override fun getAuthorities(): MutableCollection<out GrantedAuthority>
    {
        return mutableListOf(SimpleGrantedAuthority(role.authority))
    }

    override fun getPassword(): String
    {
        return this.password
    }

    override fun getUsername(): String
    {
        return this.email
    }

    override fun isAccountNonExpired(): Boolean
    {
        return true
    }

    override fun isAccountNonLocked(): Boolean
    {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean
    {
        return true
    }

    override fun isEnabled(): Boolean
    {
        return true
    }
}

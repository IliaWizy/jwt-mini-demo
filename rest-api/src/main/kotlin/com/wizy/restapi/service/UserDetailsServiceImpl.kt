package com.wizy.restapi.service

import com.wizy.restapi.model.UserPrincipal
import com.wizy.restapi.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class UserDetailsServiceImpl(
        private val userRepository: UserRepository
) : UserDetailsService
{
    override fun loadUserByUsername(username: String): UserPrincipal =
            userRepository
                .findByEmail(username)
                ?: throw UsernameNotFoundException("User $username not found")

}

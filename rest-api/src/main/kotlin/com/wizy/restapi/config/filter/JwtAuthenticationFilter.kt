package com.wizy.restapi.config.filter

import com.wizy.restapi.config.doesNotContainBearerToken
import com.wizy.restapi.config.extractTokenValue
import com.wizy.restapi.service.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

@Configuration
class JwtAuthenticationFilter(
        private val userDetailsService: UserDetailsService, private val tokenService: TokenService
) : OncePerRequestFilter()
{
    override fun doFilterInternal(
            request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    )
    {
        val authHeader: String? = request.getHeader("Authorization")

        if (authHeader.doesNotContainBearerToken())
        {
            filterChain.doFilter(request, response)
            return
        }

        val jwt = authHeader!!.extractTokenValue()
        val email = tokenService.extractClaim(jwt) { it.subject }

        if (email != null && SecurityContextHolder.getContext().authentication == null)
        {
            val loadedUser = userDetailsService.loadUserByUsername(email)

            if (tokenService.isValid(jwt, loadedUser))
            {
                updateContext(loadedUser, request)
            }

            filterChain.doFilter(request, response)
        }

    }

    private fun updateContext(
            user: UserDetails, request: HttpServletRequest
    )
    {
        // ЧИТАЕМ ЭТОТ КОНСТРУКТОР UsernamePasswordAuthenticationToken(user, user.authorities), а именно:
        // Этот конструктор может безопасно использоваться любым кодом,
        // который хочет создать UsernamePasswordAuthenticationToken, поскольку isAuthenticated() вернет false.
        SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(user, null, user.authorities)
                    .apply {
                        details = WebAuthenticationDetailsSource().buildDetails(request)
                    }
    }
}

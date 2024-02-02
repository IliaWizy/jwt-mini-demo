package com.wizy.restapi.repository.fake

import com.wizy.restapi.repository.RefreshTokenRepository
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@ConditionalOnProperty(name = ["spring.profiles.active"], havingValue = "dev")
// @ConditionalOnMissingBean(RefreshTokenRepository::class)
class FakeRefreshTokenRepository : RefreshTokenRepository
{
    private val tokens = mutableMapOf<String, UUID>()

    override fun findByToken(token: String): UUID? =
            tokens[token]


    override fun save(refreshToken: String, uuid: UUID)
    {
        tokens[refreshToken] = uuid

    }


    override fun deleteByToken(token: String)
    {
        tokens.remove(token)
    }
}

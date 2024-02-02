package com.wizy.restapi.repository.fake

import com.wizy.restapi.model.Role
import com.wizy.restapi.model.UserPrincipal
import com.wizy.restapi.repository.UserRepository
import com.wizy.restapi.util.CompanionLogger
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import java.sql.SQLException
import java.util.*

@Repository
@ConditionalOnProperty(name = ["spring.profiles.active"], havingValue = "dev")
// @ConditionalOnMissingBean(UserRepository::class)
class FakeUserRepository(
        private val encoder: PasswordEncoder
) : UserRepository
{

    init
    {
        logger.debug("FakeUserRepository")
    }

    private val userPrincipals = mutableListOf(
            UserPrincipal(generateUUID(), "id@gmail.com", encoder.encode("pass"), Role.ADMIN),
            UserPrincipal(generateUUID(), "Jane@gmail.com", encoder.encode("asdasd"), Role.USER),
            UserPrincipal(generateUUID(), "Max@gmail.com", encoder.encode("asdasd"), Role.USER)
    )

    override fun findAll() =
            userPrincipals

    override fun findByEmail(email: String): UserPrincipal? =
            userPrincipals.firstOrNull { it.username == email }

    @Throws(SQLException::class)
    override fun save(userPrincipal: UserPrincipal): UserPrincipal =
            userPrincipals.add(
                    userPrincipal.copy(
                            id = generateUUID(),
                            password = encoder.encode(userPrincipal.password)
                    )
            ).let { userPrincipal }

    @Throws(SQLException::class)
    override fun deleteById(uuid: UUID) =
            userPrincipals.removeIf { it.id == uuid }

    override fun findByUUID(uuid: UUID): UserPrincipal? =
            userPrincipals.firstOrNull { it.id == uuid }

    override fun generateUUID(): UUID =
            UUID.randomUUID()


    companion object : CompanionLogger()
}

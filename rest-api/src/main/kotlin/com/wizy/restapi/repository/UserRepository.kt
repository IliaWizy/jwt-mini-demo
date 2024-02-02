package com.wizy.restapi.repository

import com.wizy.restapi.model.UserPrincipal
import java.sql.SQLException
import java.util.*

interface UserRepository
{
    fun findAll(): MutableList<UserPrincipal>
    fun findByEmail(email: String): UserPrincipal?

    @Throws(SQLException::class)
    fun save(userPrincipal: UserPrincipal): UserPrincipal

    @Throws(SQLException::class)
    fun deleteById(uuid: UUID): Boolean
    fun findByUUID(uuid: UUID): UserPrincipal?
    fun generateUUID(): UUID
}

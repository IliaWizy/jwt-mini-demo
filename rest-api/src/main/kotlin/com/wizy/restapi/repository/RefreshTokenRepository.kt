package com.wizy.restapi.repository

import java.util.*

interface RefreshTokenRepository
{
    fun findByToken(token: String): UUID?
    fun save(refreshToken: String, uuid: UUID)
    fun deleteByToken(token: String)
}

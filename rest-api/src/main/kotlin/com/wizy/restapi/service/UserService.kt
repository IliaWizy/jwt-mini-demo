package com.wizy.restapi.service

import com.wizy.restapi.controller.user.UserRequest
import com.wizy.restapi.controller.user.UserResponse
import com.wizy.restapi.model.Role
import com.wizy.restapi.model.UserPrincipal
import com.wizy.restapi.repository.UserRepository
import com.wizy.restapi.service.mapper.convertToUserResponse
import com.wizy.restapi.service.mapper.toModel
import com.wizy.restapi.util.CompanionLogger
import org.springframework.stereotype.Service
import java.sql.SQLException
import java.util.*

@Service
class UserService(
        private val userRepository: UserRepository
)
{

    fun create(request: UserRequest): UserResponse =
            try
            {
                val savedUser = userRepository.save(request.toModel(Role.USER))
                savedUser.convertToUserResponse()
            }
            catch (e: SQLException)
            {
                logger.error("Ошибка при сохранении пользователя: ${e.message}", e)
                throw e
            }


    fun findByUUID(uuid: UUID): UserResponse =
            userRepository.findByUUID(uuid = uuid)?.let(UserPrincipal::convertToUserResponse)
                ?: throw NoSuchElementException("404")


    fun findAll(): List<UserResponse> =
            userRepository.findAll().map(UserPrincipal::convertToUserResponse)


    fun deleteByUUID(uuid: UUID): Boolean =
            try
            {
                userRepository.deleteById(uuid)
            }
            catch (e: SQLException)
            {
                logger.error("Ошибка при удалении пользователя: ${e.message}", e)
                throw e
            }

    companion object : CompanionLogger()
}

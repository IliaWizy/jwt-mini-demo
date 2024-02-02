package com.wizy.restapi.controller.user

import com.wizy.restapi.service.UserService
import com.wizy.restapi.util.CompanionLogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/user")
class UserController(
        private val userService: UserService,
)
{

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
            @RequestBody
            userRequest: UserRequest
    ): UserResponse =
            userService.create(userRequest)


    @GetMapping("/{uuid}")
    fun findByUUID(
            @PathVariable
            uuid: UUID
    ): UserResponse =
            userService.findByUUID(uuid)

    @GetMapping
    fun findAll(authentication: Authentication): List<UserResponse> =
            userService.findAll()


    @DeleteMapping("/{uuid}")
    fun deleteByUUID(
            @PathVariable
            uuid: UUID
    ): ResponseEntity<Boolean> =
            userService
                .deleteByUUID(uuid)
                .let {
                    ResponseEntity.noContent().build()
                }


    companion object : CompanionLogger()
}

package com.wizy.restapi.service.mapper

import com.wizy.restapi.controller.user.UserRequest
import com.wizy.restapi.controller.user.UserResponse
import com.wizy.restapi.model.Role
import com.wizy.restapi.model.UserPrincipal
import java.util.*

/**
 * toModel is an extension function of UserRequest.
 * toModel takes a Role as a parameter and returns a User.
 * */
val toModel: UserRequest.(role: Role) -> UserPrincipal =
        {
            UserPrincipal(
                    id = UUID.randomUUID(), email = this.email, password = this.password, role = it
            )
        }

fun UserPrincipal.convertToUserResponse(): UserResponse =
        UserResponse(
                id = this.id, email = this.username
        )

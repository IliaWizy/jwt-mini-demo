package com.wizy.restapi.service.mapper

import com.wizy.restapi.controller.material.MaterialResponse
import com.wizy.restapi.model.Material

// fun Material.toResponse(block: MaterialResponse.() -> Unit): MaterialResponse =
//        MaterialResponse(id = id, title = title, content = content).apply(block)

// val toResponse: Material.() -> MaterialResponse =
//        {
//            MaterialResponse(id = id, title = title, content = content)
//        }


fun Material.convertToUserResponse(): MaterialResponse =
        MaterialResponse(id = id, title = title, content = content)

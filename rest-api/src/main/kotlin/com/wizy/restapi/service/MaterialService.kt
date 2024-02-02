package com.wizy.restapi.service

import com.wizy.restapi.controller.material.MaterialResponse
import com.wizy.restapi.model.Material
import com.wizy.restapi.repository.MaterialRepository
import com.wizy.restapi.service.mapper.convertToUserResponse
import org.springframework.stereotype.Service


@Service
class MaterialService(
        private val materialRepository: MaterialRepository
)
{
    fun findAll(): List<MaterialResponse> =
            materialRepository
                .findAll()
                .map(Material::convertToUserResponse)
}

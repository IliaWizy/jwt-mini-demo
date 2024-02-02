package com.wizy.restapi.controller.material

import com.wizy.restapi.service.MaterialService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/material")
class MaterialController(
        private val materialService: MaterialService
)
{

    @GetMapping
    fun listAll(): ResponseEntity<List<MaterialResponse>> =
            materialService
                .findAll()
                .let { ResponseEntity.ok(it) }
}

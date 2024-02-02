package com.wizy.restapi.repository.fake

import com.wizy.restapi.model.Material
import com.wizy.restapi.repository.MaterialRepository
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@ConditionalOnProperty(name = ["spring.profiles.active"], havingValue = "dev")
class FakeMaterialRepository
    : MaterialRepository
{
    private val materials = listOf(
            Material(id = UUID.randomUUID(), title = "Art 1", content = "Cont 1"),
            Material(id = UUID.randomUUID(), title = "Art 2", content = "Cont 2"),
            Material(id = UUID.randomUUID(), title = "Art 3", content = "Cont 3"),
            Material(id = UUID.randomUUID(), title = "Art 4", content = "Cont 4"),
            Material(id = UUID.randomUUID(), title = "Art 5", content = "Cont 5"),
    )

    override fun findAll(): List<Material> =
            materials

}

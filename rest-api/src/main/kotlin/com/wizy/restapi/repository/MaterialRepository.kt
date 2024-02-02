package com.wizy.restapi.repository

import com.wizy.restapi.model.Material

interface MaterialRepository
{
    fun findAll(): List<Material>
}

package com.wizy.restapi.config


internal fun String.extractTokenValue(): String =
        this.substringAfter("Bearer ")


internal fun String?.doesNotContainBearerToken(): Boolean =
        this == null || !this.startsWith("Bearer ")

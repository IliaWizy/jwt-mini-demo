package com.wizy.restapi.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class CompanionLogger
{
    protected val logger: Logger =
            LoggerFactory.getLogger(javaClass.enclosingClass)
}

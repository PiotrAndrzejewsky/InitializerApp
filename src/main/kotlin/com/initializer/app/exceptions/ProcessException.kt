package com.initializer.app.exceptions

import org.springframework.http.HttpStatus

class ProcessException(
    override val message: String,
    causedBy: Throwable? = null,
    val status: HttpStatus? = null,
): RuntimeException(message, causedBy)
package com.initializer.app.api.controllers

import com.initializer.app.api.generated.UserApi
import com.initializer.app.model.generated.ActivateUserForm
import com.initializer.app.model.generated.UserForm
import com.initializer.app.security.AllowPath
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.RequestBody
import java.math.BigDecimal
import javax.validation.Valid

@ControllerAdvice
class CustomLoginApi() : UserApi {

    @AllowPath
    override fun userActivatePost(@Valid @RequestBody activateUserForm: ActivateUserForm): ResponseEntity<Unit> {
        TODO("Not yet implemented")
    }

    @AllowPath
    override fun userCreatePost(@Valid @RequestBody userForm: UserForm): ResponseEntity<Unit> {
        TODO("Not yet implemented")
    }

    @AllowPath
    override fun userLoginPost(@Valid @RequestBody userForm: UserForm): ResponseEntity<BigDecimal> {
        TODO("Not yet implemented")
    }
}
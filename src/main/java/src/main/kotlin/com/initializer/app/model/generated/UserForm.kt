package com.initializer.app.model.generated

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size
import javax.validation.Valid

/**
 * 
 * @param username 
 * @param password 
 * @param email 
 */
data class UserForm(

    @field:JsonProperty("username", required = true) val username: kotlin.String,

    @field:JsonProperty("password", required = true) val password: kotlin.String,

    @field:JsonProperty("email", required = true) val email: kotlin.String
) {

}


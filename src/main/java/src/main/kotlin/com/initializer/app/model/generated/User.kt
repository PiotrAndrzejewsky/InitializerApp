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
 * @param id 
 * @param username 
 * @param password 
 * @param email 
 * @param activated 
 */
data class User(

    @field:JsonProperty("id") val id: java.math.BigDecimal? = null,

    @field:JsonProperty("username") val username: kotlin.String? = null,

    @field:JsonProperty("password") val password: kotlin.String? = null,

    @field:JsonProperty("email") val email: kotlin.String? = null,

    @field:JsonProperty("activated") val activated: kotlin.Boolean? = null
) {

}


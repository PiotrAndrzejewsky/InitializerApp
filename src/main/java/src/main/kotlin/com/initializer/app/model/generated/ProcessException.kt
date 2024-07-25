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
 * @param message 
 * @param status 
 */
data class ProcessException(

    @field:JsonProperty("message") val message: kotlin.String? = null,

    @field:JsonProperty("status") val status: java.math.BigDecimal? = null
) {

}


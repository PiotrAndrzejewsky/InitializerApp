package com.initializer.app.model.generated

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Size

/**
 * 
 * @param userId 
 * @param confirmationCode 
 */
data class ActivateUserForm(

    @field:JsonProperty("userId", required = true) val userId: java.math.BigDecimal,

    @get:Size(max=6)
    @field:JsonProperty("confirmationCode", required = true) val confirmationCode: kotlin.String
) {

}


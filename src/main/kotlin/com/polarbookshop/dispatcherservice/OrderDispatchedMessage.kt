package com.polarbookshop.dispatcherservice

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class OrderDispatchedMessage @JsonCreator constructor(
    @JsonProperty("orderId") val orderId: Long
)
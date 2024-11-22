package com.polarbookshop.dispatcherservice

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class OrderAcceptedMessage @JsonCreator constructor(
    @JsonProperty("orderId") val orderId: Long
)
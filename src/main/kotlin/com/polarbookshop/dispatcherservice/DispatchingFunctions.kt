package com.polarbookshop.dispatcherservice

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux


@Configuration
class DispatchingFunctions {
    companion object {
        val log: Logger = LoggerFactory.getLogger(DispatchingFunctions::class.java)
    }

    @Bean
    fun pack() : (OrderAcceptedMessage) -> Long {
        return {
            log.info("The order with id ${it.orderId} is packed.")
            it.orderId
        }
    }

    @Bean
    fun label() : (Flux<Long>) -> Flux<OrderDispatchedMessage> {
        return {
            orderFlux -> orderFlux.map {
                orderId ->
                    log.info("The order with id $orderId is labeled.")
                    OrderDispatchedMessage(orderId)
            }
        }
    }

}
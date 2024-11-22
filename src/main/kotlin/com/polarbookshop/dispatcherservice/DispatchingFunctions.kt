package com.polarbookshop.dispatcherservice

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import reactor.core.publisher.Flux
import java.util.function.Function

@Configuration
class DispatchingFunctions {
    companion object {
        val log: Logger = LoggerFactory.getLogger(DispatchingFunctions::class.java)
    }

//    @Bean
//    fun pack() : (OrderAcceptedMessage) -> Long {
//        return {
//            log.info("The order with id ${it.orderId} is packed.")
//            it.orderId
//        }
//    }

//    @Bean
//    fun pack() : Function<OrderAcceptedMessage, Long> {
//        return Function {
//            log.info("The order with id ${it.orderId} is packed.")
//            it.orderId
//        }
//    }

//    @Bean
//    fun label() : (Flux<Long>) -> Flux<OrderDispatchedMessage> {
//        return {
//            orderFlux -> orderFlux.map {
//                orderId ->
//                    log.info("The order with id $orderId is labeled.")
//                    OrderDispatchedMessage(orderId)
//            }
//        }
//    }

//    @Bean
//    fun label() : Function<Flux<Long>, Flux<OrderDispatchedMessage>> {
//        return Function {
//            orderFlux -> orderFlux.map {
//                orderId ->
//                    log.info("The order with id $orderId is labeled.")
//                    OrderDispatchedMessage(orderId)
//            }
//        }
//    }

    @Bean
    fun pack(): (Message<OrderAcceptedMessage>) -> Message<Long> {
        return { message ->
            val orderId = message.payload.orderId
            log.info("The order with id $orderId is packed.")
            MessageBuilder.withPayload(orderId).build()
        }
    }

    @Bean
    fun label(): (Flux<Message<Long>>) -> Flux<Message<OrderDispatchedMessage>> {
        return { messageFlux ->
            messageFlux.map { message ->
                val orderId = message.payload
                log.info("The order with id $orderId is labeled.")
                MessageBuilder.withPayload(OrderDispatchedMessage(orderId)).build()
            }
        }
    }
}
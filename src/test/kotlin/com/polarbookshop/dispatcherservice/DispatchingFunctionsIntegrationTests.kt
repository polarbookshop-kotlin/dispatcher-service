package com.polarbookshop.dispatcherservice

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.function.context.FunctionCatalog
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest
import org.springframework.messaging.Message
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.util.function.Function

@FunctionalSpringBootTest
class DispatchingFunctionsIntegrationTests {
    @Autowired
    private lateinit var catalog : FunctionCatalog
//    private val objectMapper = jacksonObjectMapper()

//    @Test
//    fun packAndLabelOrder() {
//        val packAndLabel = catalog.lookup<Function<OrderAcceptedMessage, Flux<Message<*>>>>("pack|label")
//
//        val orderId = 121L
//        val orderAcceptedMessage = OrderAcceptedMessage(orderId)
//
//        StepVerifier.create(packAndLabel.apply(orderAcceptedMessage))
//            .expectNextMatches { message ->
//                val dispatchedOrder = objectMapper.readValue(message.payload as ByteArray, OrderDispatchedMessage::class.java)
//                dispatchedOrder.orderId == orderId
//            }
//            .verifyComplete()
//    }

    @Test
    fun packAndLabelForJava(){
        val packAndLabel = catalog.lookup<Function<OrderAcceptedMessage, Flux<Message<*>>>>("pack|label")
        val orderId = 121L
        val orderAcceptedMessage = OrderAcceptedMessage(orderId)
        val orderDispatchingFunctions = OrderDispatchedMessage(orderId)

        StepVerifier.create(packAndLabel.apply(orderAcceptedMessage))
            .expectNextMatches { it.equals(orderDispatchingFunctions) }
    }

}
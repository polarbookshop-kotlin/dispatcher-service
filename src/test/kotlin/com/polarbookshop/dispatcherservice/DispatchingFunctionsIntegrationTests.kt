package com.polarbookshop.dispatcherservice

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.function.context.FunctionCatalog
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest
import org.springframework.integration.support.json.Jackson2JsonObjectMapper
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.util.function.Function

@FunctionalSpringBootTest
class DispatchingFunctionsIntegrationTests {
    @Autowired
    private lateinit var catalog : FunctionCatalog
    private val objectMapper = jacksonObjectMapper()

    @Test
    fun packAndLabelOrder() {
        val packAndLabel = catalog.lookup<Function<OrderAcceptedMessage, Flux<Message<Long>>>>("pack|label")

        val orderId = 121L
        val orderAcceptedMessage = OrderAcceptedMessage(orderId)

        StepVerifier.create(packAndLabel.apply(orderAcceptedMessage))
            .expectNextMatches { message ->
                val dispatchedOrder = objectMapper.readValue(message.payload as ByteArray, OrderDispatchedMessage::class.java)
                dispatchedOrder.orderId == orderId
            }
            .verifyComplete()
    }

}
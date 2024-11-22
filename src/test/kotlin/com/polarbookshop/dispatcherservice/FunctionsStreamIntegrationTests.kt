package com.polarbookshop.dispatcherservice

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.integration.support.MessageBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.stream.binder.test.InputDestination
import org.springframework.cloud.stream.binder.test.OutputDestination
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(TestChannelBinderConfiguration::class)
class FunctionsStreamIntegrationTests {
    @Autowired
    private lateinit var input: InputDestination

    @Autowired
    private lateinit var output: OutputDestination

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `when oder accepted then dispatched`() {
        val orderId = 121L
        val orderAcceptedMessage = OrderAcceptedMessage(orderId)
        val orderDispatchedMessage = OrderDispatchedMessage(orderId)
        val inputMessage = MessageBuilder.withPayload(orderAcceptedMessage).build()
        val expectedMessage = MessageBuilder.withPayload(orderDispatchedMessage).build()

        this.input.send(inputMessage)
        assertThat(objectMapper.readValue(output.receive().payload, OrderDispatchedMessage::class.java))
            .isEqualTo(expectedMessage.payload)
    }
}
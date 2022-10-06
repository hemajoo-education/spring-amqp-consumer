/*
 * (C) Copyright Hemajoo Systems Inc.  2022 - All Rights Reserved
 * -----------------------------------------------------------------------------------------------
 * All information contained herein is, and remains the property of
 * Hemajoo Inc. and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Hemajoo Inc. and its
 * suppliers and may be covered by U.S. and Foreign Patents, patents
 * in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained from
 * Hemajoo Systems Inc.
 * -----------------------------------------------------------------------------------------------
 */
package com.hemajoo.education.spring.amqp.game.rest.controller;

import com.github.javafaker.Faker;
import com.hemajoo.education.spring.amqp.core.consumer.RabbitMQConsumerType;
import com.hemajoo.education.spring.amqp.game.consumer.player.PlayerQueueListenerType;
import com.hemajoo.education.spring.amqp.game.protocol.message.chat.ChatMessage;
import com.hemajoo.education.spring.amqp.game.protocol.message.chat.ChatMessageContent;
import com.hemajoo.education.wow.queue.commons.ParticipantType;
import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import com.hemajoo.utility.string.StringExpander;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@Tag(name = "Chat Message controller", description = "Provide endpoints to send chat messages.")
@RequestMapping("/api/v1/amqp/message/chat")
public class RestChatMessageController
{
    /**
     * Data faker.
     */
    @Getter
    private final Faker faker = new Faker();

    /**
     * Variable substitution character.
     */
    @Value("${amqp.variable.placeholder.character}")
    private String amqpVariablePlaceholderCharacter;

    /**
     * Queue prefix name.
     */
    @Value("${amqp.queue.prefix}")
    private String amqpQueuePrefix;

    /**
     * Queue template name.
     */
    @Value("${amqp.queue.template}")
    private String amqpQueueTemplate;

    /**
     * Exchange template name.
     */
    @Value("${amqp.exchange.direct.template}")
    private String amqpDirectExchangeTemplate;

    /**
     * <b>RabbitMQ</b> template.
     */
    private final RabbitTemplate rabbitTemplate;

    public RestChatMessageController(RabbitTemplate rabbitTemplate)
    {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Send random chat messages to a specific recipient.
     * @param count Number of random chat messages to send.
     * @param recipient Recipient.
     */
    @Operation(summary = "Send a given count of random chat message(s) based on Check Norris facts.")
    @PostMapping(value = "/send/random")
    public void sendChatMessageToRecipient(
            @Parameter(description = "Number of random messages to send", required = false) final @NotNull @RequestParam(value = "1") int count,
            @Parameter(description = "Recipient", required = true) final @NotNull @RequestParam String recipient)
    {
        ChatMessage message;

        String exchangeTemplate = amqpDirectExchangeTemplate;
        exchangeTemplate = StringExpander.expand(amqpVariablePlaceholderCharacter, exchangeTemplate, "type", RabbitMQConsumerType.PLAYER.name());
        exchangeTemplate = StringExpander.expand(amqpVariablePlaceholderCharacter, exchangeTemplate, "key", recipient);

        for (int i = 0; i < count; i++)
        {
            message = new ChatMessage(ChatMessage.MessageType.MESSAGE_CHAT_NOTIFICATION,
                    ChatMessageContent.builder()
                            .withText(faker.chuckNorris().fact())
                            .withSenderName(faker.name().fullName())
                            .build(),
                    SenderIdentity.builder()
                            .withReference(recipient)
                            .withType(ParticipantType.PLAYER).build());

            rabbitTemplate.convertAndSend(exchangeTemplate, PlayerQueueListenerType.CHAT.getRoutingKey(), message);
        }
    }

    /**
     * Send a <b>chat message</b> to a specific recipient.
     * @param recipient Recipient.
     */
    @Operation(summary = "Send a chat message to a recipient.")
    @PostMapping(value = "/send")
    public void sendChatMessageToRecipient(
            @Parameter(description = "Text message", required = true) final @NotNull @RequestParam String text,
            @Parameter(description = "Recipient", required = true) final @NotNull @RequestParam String recipient,
            @Parameter(description = "Sender name", required = true) final @NotNull @RequestParam String senderName)
    {
        ChatMessage message;

        String exchangeTemplate = amqpDirectExchangeTemplate;
        exchangeTemplate = StringExpander.expand(amqpVariablePlaceholderCharacter, exchangeTemplate, "type", RabbitMQConsumerType.PLAYER.name());
        exchangeTemplate = StringExpander.expand(amqpVariablePlaceholderCharacter, exchangeTemplate, "key", recipient);

        message = new ChatMessage(ChatMessage.MessageType.MESSAGE_CHAT_NOTIFICATION,
                ChatMessageContent.builder()
                        .withText(text)
                        .withSenderName(senderName)
                        .build(),
                SenderIdentity.builder()
                        .withReference(recipient)
                        .withType(ParticipantType.PLAYER).build());

        rabbitTemplate.convertAndSend(exchangeTemplate, PlayerQueueListenerType.CHAT.getRoutingKey(), message);
    }

//    private Message messageAsJson(final @NotNull Object message) throws JsonProcessingException
//    {
//        return MessageBuilder
//                .withBody(objectMapper.writeValueAsString(message).getBytes())
//                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
//                .build();
//    }
}

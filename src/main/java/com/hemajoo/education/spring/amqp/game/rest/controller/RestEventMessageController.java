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

import com.hemajoo.education.spring.amqp.game.protocol.message.event.EventNotificationMessage;
import com.hemajoo.education.wow.queue.commons.ParticipantType;
import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import com.hemajoo.education.wow.queue.util.MessageProtocolRandomizer;
import com.hemajoo.utility.random.GeneratorException;
import com.hemajoo.utility.string.StringExpander;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@Tag(name = "Event Message controller", description = "Provide endpoints to send event messages.")
@RequestMapping("/api/v1/amqp/message/event")
public class RestEventMessageController
{
    private final RabbitTemplate rabbitTemplate;

    public RestEventMessageController(RabbitTemplate rabbitTemplate)
    {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Send random <b>event notification messages</b> to a queue consumer.
     * @param count Number of messages to send.
     * @param consumerKey Consumer key (in case queue name contains a variable for the agent identifier, ex.: 'com.hemajoo.education.spring.amqp.player.${key}.default').
     * @throws GeneratorException Thrown if an error occurred while generating a random event notification message.
     */
    @PostMapping(value = "/random/notification/queue")
    public void randomlySendEventNotificationMessagesDirectlyToAQueue(
            @Parameter(description = "Number of event messages to send", required = true) final @NotNull @RequestParam int count,
            @Parameter(description = "Queue name", required = true) final @NotNull @RequestParam String queueName,
            @Parameter(description = "Consumer key (if the queue name contains a variable representing the consumer key)") final @RequestParam(required = false) String consumerKey) throws GeneratorException
    {
        String name = queueName;
        EventNotificationMessage message;

        if (StringExpander.containsVariable(name) && consumerKey != null)
        {
            name = StringExpander.expand(queueName, "key", consumerKey);
        }

        for (int i = 0; i < count; i++)
        {
            message = (EventNotificationMessage) MessageProtocolRandomizer.randomEventNotificationMessage(
                    SenderIdentity.builder()
                            .withType(ParticipantType.SERVICE_EVENT)
                            .build());

            rabbitTemplate.convertAndSend(name, message); // Directly send a message to a queue
        }
    }

    /**
     * Send random <b>event notification messages</b> to a queue consumer.
     * @param count Number of messages to send.
     * @param exchange Exchange.
     * @param routingKey Routing key.
     * @throws GeneratorException Thrown if an error occurred while generating a random event notification message.
     */
    @PostMapping(value = "/random/notification/direct-exchange")
    public void randomlySendEventNotificationMessagesThroughDirectExchange(
            @Parameter(description = "Number of random messages to send") final @NotNull @RequestParam int count,
            @Parameter(description = "Exchange") final @NotNull @RequestParam String exchange,
            @Parameter(description = "Routing key") final @RequestParam String routingKey) throws GeneratorException
    {
        EventNotificationMessage message;

        for (int i = 0; i < count; i++)
        {
            message = (EventNotificationMessage) MessageProtocolRandomizer.randomEventNotificationMessage(null);

            rabbitTemplate.convertAndSend(exchange, routingKey, message); // Send a message to an exchange with a routing key
        }
    }
}

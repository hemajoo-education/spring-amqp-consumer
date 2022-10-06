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

import com.hemajoo.education.spring.amqp.core.consumer.RabbitMQConsumerType;
import com.hemajoo.education.spring.amqp.game.protocol.QueueType;
import com.hemajoo.education.spring.amqp.game.protocol.message.system.SystemMessage;
import com.hemajoo.utility.string.StringExpander;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@Tag(name = "System Message controller", description = "Provide endpoints to send system messages.")
@RequestMapping("/api/v1/amqp/message/system")
public class RestSystemMessageController
{
    @Value("${amqp.variable.placeholder.character}")
    private String amqpVariablePlaceholderCharacter;

    @Value("${amqp.queue.prefix}")
    private String amqpPrefix;

    @Value("${amqp.queue.template}")
    private String amqpPrefixQueue;

    @Value("${amqp.exchange.direct.template}")
    private String amqpDirectExchange;

    private final RabbitTemplate rabbitTemplate;

    public RestSystemMessageController(RabbitTemplate rabbitTemplate)
    {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Send a <b>system message</b> to <b>pause</b> a consumer listener for a given queue.
     * <br><br>
     * <b>Note</b><br>
     * The default consumer queue cannot be paused/resumed!
     * @param queueType Queue type to pause.
     * @param consumerType Consumer type (in case queue name contains a variable for the agent identifier, ex.: 'com.hemajoo.education.spring.amqp.${type}.${key}.default').
     * @param consumerKey Consumer key (in case queue name contains a variable for the agent identifier, ex.: 'com.hemajoo.education.spring.amqp.${type}.${key}.default').
     */
    @Operation(summary = "Send a system message to pause a consumer listener for a given queue.")
    @PostMapping(value = "/queue/consumer/pause")
    public void consumerQueuePause(
            @Parameter(description = "Queue type to pause", required = true) final @NotNull @RequestParam QueueType queueType,
            @Parameter(description = "Consumer type", required = true) final @NotNull @RequestParam RabbitMQConsumerType consumerType,
            @Parameter(description = "Consumer key") final @RequestParam(required = false) String consumerKey)
    {
        SystemMessage message = new SystemMessage(SystemMessage.MessageType.MESSAGE_SYSTEM_CONSUMER_QUEUE_PAUSE, queueType, null);

        rabbitTemplate.convertAndSend(resolveQueueName(consumerType, consumerKey), message); // Directly send a message to a queue
    }

    /**
     * Send a <b>system message</b> to <b>resume</b> a consumer listener for a given queue.
     * <br><br>
     * <b>Note</b><br>
     * The <b>default</b> consumer queue cannot be paused/resumed!
     * @param queueType Queue type to resume.
     * @param consumerType Consumer type (in case queue name contains a variable for the agent identifier, ex.: 'com.hemajoo.education.spring.amqp.${type}.${key}.default').
     * @param consumerKey Consumer key (in case queue name contains a variable for the agent identifier, ex.: 'com.hemajoo.education.spring.amqp.${type}.${key}.default').
     */
    @Operation(summary = "Send a system message to resume a consumer listener for a given queue.")
    @PostMapping(value = "/queue/consumer/resume")
    public void consumerQueueResume(
            @Parameter(description = "Queue type to resume", required = true) final @NotNull @RequestParam QueueType queueType,
            @Parameter(description = "Consumer type", required = true) final @NotNull @RequestParam RabbitMQConsumerType consumerType,
            @Parameter(description = "Consumer key") final @RequestParam(required = false) String consumerKey)
    {
        SystemMessage message = new SystemMessage(SystemMessage.MessageType.MESSAGE_SYSTEM_CONSUMER_QUEUE_RESUME, queueType, null);

        rabbitTemplate.convertAndSend(resolveQueueName(consumerType, consumerKey), message); // Directly send a message to a queue
    }

    private String resolveQueueName(final @NonNull RabbitMQConsumerType type, final String key)
    {
        String queueName = amqpPrefixQueue;

        queueName = StringExpander.expand(amqpVariablePlaceholderCharacter, queueName, "type", type.name());
        queueName = StringExpander.expand(amqpVariablePlaceholderCharacter,queueName, "key", key);
        queueName += QueueType.SYSTEM.getExtension();

        return queueName;
    }
}

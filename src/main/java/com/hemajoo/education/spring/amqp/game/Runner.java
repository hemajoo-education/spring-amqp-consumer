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
package com.hemajoo.education.spring.amqp.game;

import com.hemajoo.education.spring.amqp.game.consumer.player.PlayerConsumer;
import com.hemajoo.education.spring.amqp.game.consumer.player.PlayerQueueListenerType;
import com.hemajoo.education.spring.amqp.game.protocol.QueueType;
import com.hemajoo.education.wow.queue.ExchangeType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class Runner implements CommandLineRunner
{
    @Value("${amqp.variable.placeholder.character}")
    private String amqpVariablePlaceholderCharacter;

    @Value("${amqp.queue.prefix}")
    private String amqpQueuePrefix;

    @Value("${amqp.queue.template}")
    private String amqpQueueTemplate;

    @Value("${amqp.exchange.direct.template}")
    private String amqpDirectExchangeTemplate;

    @Autowired
    private PlayerConsumer player;

    @Override
    public void run(String... args) throws Exception
    {
        // Create queues
        player.createQueue(
                QueueType.DEFAULT,
                amqpQueueTemplate,
                PlayerQueueListenerType.DEFAULT.getMessageListenerName());
        player.createQueue(
                QueueType.EVENT,
                amqpQueueTemplate,
                PlayerQueueListenerType.EVENT.getMessageListenerName());
        player.createQueue(
                QueueType.CHAT,
                amqpQueueTemplate,
                PlayerQueueListenerType.CHAT.getMessageListenerName());
        player.createQueue(
                QueueType.SYSTEM,
                amqpQueueTemplate,
                PlayerQueueListenerType.SYSTEM.getMessageListenerName());
        player.createQueue(
                QueueType.BANK,
                amqpQueueTemplate,
                PlayerQueueListenerType.BANK.getMessageListenerName());
        player.createQueue(
                QueueType.POSTAL,
                amqpQueueTemplate,
                PlayerQueueListenerType.POSTAL.getMessageListenerName());
        player.createQueue(
                QueueType.QUEST,
                amqpQueueTemplate,
                PlayerQueueListenerType.QUEST.getMessageListenerName());
        player.createQueue(
                QueueType.AUCTION_HOUSE,
                amqpQueueTemplate,
                PlayerQueueListenerType.AUCTION_HOUSE.getMessageListenerName());
        player.createQueue(
                QueueType.ZONE,
                amqpQueueTemplate,
                PlayerQueueListenerType.ZONE.getMessageListenerName());

        // Create direct exchanges & bindings
        player.addExchange(
                QueueType.DEFAULT,
                ExchangeType.DIRECT,
                amqpDirectExchangeTemplate,
                PlayerQueueListenerType.DEFAULT.getRoutingKey());
        player.addExchange(
                QueueType.DEFAULT,
                ExchangeType.DIRECT,
                amqpDirectExchangeTemplate,
                PlayerQueueListenerType.DEFAULT.getRoutingKey());
        player.addExchange(
                QueueType.EVENT,
                ExchangeType.DIRECT,
                amqpDirectExchangeTemplate,
                PlayerQueueListenerType.EVENT.getRoutingKey());
        player.addExchange(
                QueueType.CHAT,
                ExchangeType.DIRECT,
                amqpDirectExchangeTemplate,
                PlayerQueueListenerType.CHAT.getRoutingKey());
        player.addExchange(
                QueueType.SYSTEM,
                ExchangeType.DIRECT,
                amqpDirectExchangeTemplate,
                PlayerQueueListenerType.SYSTEM.getRoutingKey());
        player.addExchange(
                QueueType.BANK,
                ExchangeType.DIRECT,
                amqpDirectExchangeTemplate,
                PlayerQueueListenerType.BANK.getRoutingKey());
        player.addExchange(
                QueueType.POSTAL,
                ExchangeType.DIRECT,
                amqpDirectExchangeTemplate,
                PlayerQueueListenerType.POSTAL.getRoutingKey());
        player.addExchange(
                QueueType.QUEST,
                ExchangeType.DIRECT,
                amqpDirectExchangeTemplate,
                PlayerQueueListenerType.QUEST.getRoutingKey());
        player.addExchange(
                QueueType.AUCTION_HOUSE,
                ExchangeType.DIRECT,
                amqpDirectExchangeTemplate,
                PlayerQueueListenerType.AUCTION_HOUSE.getRoutingKey());
        player.addExchange(
                QueueType.ZONE,
                ExchangeType.DIRECT,
                amqpDirectExchangeTemplate,
                PlayerQueueListenerType.ZONE.getRoutingKey());
    }
}

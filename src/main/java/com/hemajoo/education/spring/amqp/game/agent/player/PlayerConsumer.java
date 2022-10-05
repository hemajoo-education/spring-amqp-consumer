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
package com.hemajoo.education.spring.amqp.game.agent.player;

import com.hemajoo.education.spring.amqp.core.agent.RabbitMQBaseConsumer;
import com.hemajoo.education.spring.amqp.core.agent.RabbitMQConsumerException;
import com.hemajoo.education.spring.amqp.core.agent.RabbitMQConsumerType;
import com.hemajoo.education.spring.amqp.core.message.protocol.IMessage;
import com.hemajoo.education.spring.amqp.game.protocol.QueueType;
import com.hemajoo.education.spring.amqp.game.protocol.message.system.SystemMessage;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import java.util.concurrent.TimeUnit;

@Log4j2
public class PlayerConsumer extends RabbitMQBaseConsumer
{
    @Getter
    private final PlayerMessageEventService eventService;

    public PlayerConsumer(final RabbitMQConsumerType type, final String key, final @NonNull String defaultQueueName) throws RabbitMQConsumerException
    {
        super(type, key, QueueType.DEFAULT, defaultQueueName);

        this.eventService = new PlayerMessageEventService(this);
    }

    @Override
    public void onDefaultMessage(@NonNull IMessage message)
    {
        LOGGER.debug(String.format("Agent: '%s:%s' received message with category: '%s', type: '%s'", getType(), getKey(), message.getType().getCategoryType(), message.getType()));

        sleep(1);
    }

    public final void onSystemMessage(@NonNull SystemMessage message) throws RabbitMQConsumerException
    {
        LOGGER.debug(String.format("Agent: '%s:%s' received message with category: '%s', type: '%s'", getType(), getKey(), message.getType().getCategoryType(), message.getType()));
        switch (message.getType().getCategoryType())
        {
            case MESSAGE_CATEGORY_SYSTEM:
                handleSystemMessage(message);
                break;
        }

        sleep(1);
    }

    public void onEventMessage(@NonNull IMessage message)
    {
        LOGGER.debug(String.format("Agent: '%s:%s' received message with category: '%s', type: '%s'", getType(), getKey(), message.getType().getCategoryType(), message.getType()));

        sleep(1);
    }

    public void onChatMessage(@NonNull IMessage message)
    {
        LOGGER.debug(String.format("Agent: '%s:%s' received message with category: '%s', type: '%s'", getType(), getKey(), message.getType().getCategoryType(), message.getType()));

        sleep(1);
    }

    public void onAuctionHouseMessage(@NonNull IMessage message)
    {
        LOGGER.debug(String.format("Agent: '%s:%s' received message with category: '%s', type: '%s'", getType(), getKey(), message.getType().getCategoryType(), message.getType()));

        sleep(1);
    }

    private void sleep(final int nanoseconds)
    {
        try
        {
            TimeUnit.NANOSECONDS.sleep(nanoseconds);
        }
        catch (InterruptedException ie)
        {
            Thread.currentThread().interrupt();
        }
    }

    private void handleSystemMessage(final @NonNull SystemMessage message) throws RabbitMQConsumerException
    {
        QueueType queueType;
        SimpleMessageListenerContainer container;

        switch ((SystemMessage.MessageType) message.getType().getProtocolId())
        {
            case MESSAGE_SYSTEM_CONSUMER_QUEUE_PAUSE:
                queueType = (QueueType) message.getContent();
                if (queueType == QueueType.DEFAULT)
                {
                    throw new RabbitMQConsumerException(String.format("Cannot pause the 'default' queue for agent: '%s-%s'", getType(), getKey()));
                }

                container = getMessageListenerContainer(queueType);
                if (container.isActive())
                {
                    container.stop();
                    LOGGER.debug(String.format("Successfully stopped message listener for queue type: '%s', consumer: '%s-%s'", queueType, getType(), getKey()));
                }
                break;

            case MESSAGE_SYSTEM_CONSUMER_QUEUE_RESUME:
                queueType = (QueueType) message.getContent();
                if (queueType == QueueType.DEFAULT)
                {
                    throw new RabbitMQConsumerException(String.format("Cannot start the 'default' queue for agent: '%s-%s'", getType(), getKey()));
                }

                container = getMessageListenerContainer(queueType);
                if (!container.isActive())
                {
                    container.start();
                    LOGGER.debug(String.format("Successfully started message listener for queue type: '%s', consumer: '%s-%s'", queueType, getType(), getKey()));
                }
                break;

            default:
                throw new NotYetImplementedException();
        }
    }
}

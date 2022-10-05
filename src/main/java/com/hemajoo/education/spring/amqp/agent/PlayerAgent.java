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
package com.hemajoo.education.spring.amqp.agent;

import com.hemajoo.education.spring.amqp.base.agent.AgentConfigurationException;
import com.hemajoo.education.spring.amqp.base.agent.AgentType;
import com.hemajoo.education.spring.amqp.base.agent.QueueType;
import com.hemajoo.education.spring.amqp.base.agent.RabbitAgent;
import com.hemajoo.education.spring.queue.wow.message.broker.IMessage;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.TimeUnit;

@Log4j2
public class PlayerAgent extends RabbitAgent
{
    @Getter
    private final EventService eventService;

    public PlayerAgent(final AgentType type, @NonNull String key, QueueType defaultQueueType, @NonNull String defaultQueueName) throws AgentConfigurationException
    {
        super(type, key, defaultQueueType, defaultQueueName);

        this.eventService = new EventService(this);
    }

    @Override
    public void onDefaultMessage(@NonNull IMessage message)
    {
        LOGGER.debug(String.format("Agent: '%s:%s' received message with category: '%s', type: '%s'", getType(), getKey(), message.getType().getCategoryType(), message.getType()));

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
}

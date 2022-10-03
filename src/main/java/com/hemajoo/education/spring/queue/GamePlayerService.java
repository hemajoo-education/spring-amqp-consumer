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
package com.hemajoo.education.spring.queue;

import com.hemajoo.education.spring.queue.game.config.GameQueueConfiguration;
import com.hemajoo.education.spring.queue.wow.message.broker.RabbitMQService;
import com.hemajoo.education.wow.queue.commons.EventType;
import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import com.hemajoo.education.wow.queue.config.IMessageBrokerConfiguration;
import com.hemajoo.education.wow.queue.event.message.BaseMessage;
import com.hemajoo.education.wow.queue.event.message.EventNotificationMessage;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Log4j2
public class GamePlayerService extends RabbitMQService
{
//    private final RabbitTemplate template;

    public GamePlayerService(final @NonNull RabbitTemplate template, final SenderIdentity identity, final @NonNull GameQueueConfiguration queueConfiguration)
    {
        super(template, identity, queueConfiguration);
    }

    @RabbitListener(queues = { "com.hemajoo.education.spring.amqp.player.AKGHY14589JUIK.default" })
    public void receivedEventMessage(final @NonNull BaseMessage message)
    {
        if (message instanceof EventNotificationMessage)
        {
            receivedEventNotificationMessage((EventNotificationMessage) message);
        }
    }

    public void receivedEventNotificationMessage(final @NonNull EventNotificationMessage message)
    {
        EventType eventType;

        LOGGER.debug(String.format("Received on queue: '%s' message of type: '%s'", IMessageBrokerConfiguration.QUEUE_PLAYER_1_EVENT, message.getType()));

        switch (message.getType().getCategoryType())
        {
            case MESSAGE_CATEGORY_EVENT:
                if (EventNotificationMessage.MessageType.MESSAGE_EVENT_NOTIFICATION_REGISTRATION_ACCEPTED.equals(message.getType()))
                {
                    eventType = (EventType) message.getData();
                }
                else if (EventNotificationMessage.MessageType.MESSAGE_EVENT_NOTIFICATION_REGISTRATION_REJECTED.equals(message.getType()))
                {
                    eventType = (EventType) message.getData();
                }
                break;

            case MESSAGE_CATEGORY_EVENT_NOTIFICATION:
                if (EventNotificationMessage.MessageType.MESSAGE_EVENT_NOTIFICATION_REGISTRATION_ACCEPTED.equals(message.getType()))
                {
                    // Registration to event is accepted!
                    EventType event = (EventType) message.getData();
                    LOGGER.debug(String.format("Registration to event: '%s - %s' has been accepted", event.getCategoryType(), event.getEventName()));
                }
                else if (EventNotificationMessage.MessageType.MESSAGE_EVENT_NOTIFICATION_REGISTRATION_REJECTED.equals(message.getType()))
                {
                    // Registration to event is accepted!
                    EventType event = (EventType) message.getData();
                    LOGGER.warn(String.format("Registration to event: '%s - %s' has been rejected!", event.getCategoryType(), event.getEventName()));
                }
                break;

            case MESSAGE_CATEGORY_EVENT_ARENA:
                break;

            case MESSAGE_CATEGORY_EVENT_RAID:
                break;

            case MESSAGE_CATEGORY_ACTION_HOUSE:
                break;

            case MESSAGE_CATEGORY_EVENT_DUNGEON:
                break;

            case MESSAGE_CATEGORY_EVENT_BATTLEGROUND:
                break;
        }
    }
}

package com.hemajoo.education.wow.queue.actor;

import com.hemajoo.education.wow.queue.commons.EventType;
import com.hemajoo.education.wow.queue.config.IMessageBrokerConfiguration;
import com.hemajoo.education.wow.queue.event.message.BaseMessage;
import com.hemajoo.education.wow.queue.event.message.EventNotificationMessage;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class PlayerService
{
    private final RabbitTemplate template;

    public PlayerService(RabbitTemplate template)
    {
        this.template = template;
    }

    @RabbitListener(queues = { IMessageBrokerConfiguration.QUEUE_PLAYER_1_EVENT })
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

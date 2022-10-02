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
    private final RabbitTemplate rabbitTemplate;

    public PlayerService(RabbitTemplate rabbitTemplate)
    {
        this.rabbitTemplate = rabbitTemplate;
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

        LOGGER.debug(String.format("Received on queue: '%s' a message of type: '%s'", IMessageBrokerConfiguration.QUEUE_PLAYER_1_EVENT, message.getMessageProtocol().getMessageType()));

        switch (message.getMessageProtocol().getMessageCategoryType())
        {
            case MESSAGE_CATEGORY_EVENT:
                if (EventNotificationMessage.MessageType.MESSAGE_EVENT_NOTIFICATION_REGISTRATION_ACCEPTED.equals(message.getMessageProtocol().getMessageType()))
                {
                    eventType = (EventType) message.getData();
                }
                else if (EventNotificationMessage.MessageType.MESSAGE_EVENT_NOTIFICATION_REGISTRATION_REJECTED.equals(message.getMessageProtocol().getMessageType()))
                {
                    eventType = (EventType) message.getData();
                }
                break;
        }
    }
}

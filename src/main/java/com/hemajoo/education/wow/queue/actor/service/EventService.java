package com.hemajoo.education.wow.queue.actor.service;

import com.hemajoo.education.wow.queue.commons.EventType;
import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import com.hemajoo.education.wow.queue.commons.SenderType;
import com.hemajoo.education.wow.queue.config.IMessageBrokerConfiguration;
import com.hemajoo.education.wow.queue.event.message.EventNotificationMessage;
import com.hemajoo.education.wow.queue.event.message.EventRequestMessage;
import com.hemajoo.education.wow.queue.util.MessageRouter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

@Log4j2
@Component
public class EventService
{
    private final RabbitTemplate rabbitTemplate;

    private EnumMap<EventType, List<Integer>> arenas = new EnumMap<>(EventType.class);

    public EventService(RabbitTemplate rabbitTemplate)
    {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = { IMessageBrokerConfiguration.QUEUE_SERVICE_EVENT })
    public void receiveServiceEvent(final @NonNull EventRequestMessage message)
    {
        LOGGER.debug(String.format("Received on queue: '%s' message of type: '%s'", IMessageBrokerConfiguration.QUEUE_SERVICE_EVENT, message.getClass().getSimpleName()));

        EventType eventType = (EventType) message.getData();
        if (eventType != null)
        {
            switch (eventType.getCategoryType())
            {
                case ARENA:
                    List<Integer> participants = arenas.get(eventType);
                    if (participants == null)
                    {
                        participants = new ArrayList<>();
                    }
                    Integer playerId = participants.stream().filter(participant -> participant.intValue() == message.getSender().getId().intValue()).findFirst().orElse(null);
                    if (playerId != null)
                    {
                        // Send an error to the sender as it is already registered for this event!
                        EventNotificationMessage response = new EventNotificationMessage(
                                EventNotificationMessage.MessageType.MESSAGE_EVENT_NOTIFICATION_REGISTRATION_REJECTED,
                                SenderIdentity.builder() // TODO Have a static method!
                                        .withType(SenderType.SERVICE_EVENT)
                                        .withId(-1)
                                        .build(),
                                eventType);

                        MessageRouter.sendPlayerMessage(rabbitTemplate, response, message.getSender());
                    }
                    else
                    {
                        participants.add(message.getSender().getId());
                        arenas.put(eventType, participants);
                        LOGGER.info(String.format("Player id: %s added to participants of event type: %s", message.getSender().getId(), eventType));

                        // Send response to sender to confirm registration
                        EventNotificationMessage response = new EventNotificationMessage(
                                EventNotificationMessage.MessageType.MESSAGE_EVENT_NOTIFICATION_REGISTRATION_ACCEPTED,
                                SenderIdentity.builder()
                                        .withType(SenderType.SERVICE_EVENT)
                                        .withId(-1)
                                        .build(),
                                eventType);

                        MessageRouter.sendPlayerMessage(rabbitTemplate,response, message.getSender());
                    }
                    break;

                case BATTLEGROUND:
                    break;

                case RAID:
                    break;

                case DUNGEON:
                    break;
            }
        }
    }
}

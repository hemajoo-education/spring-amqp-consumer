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

import com.hemajoo.commons.exception.NotYetImplementedException;
import com.hemajoo.education.spring.queue.game.config.GameQueueConfiguration;
import com.hemajoo.education.spring.queue.game.config.rabbitmq.IGameRabbitMQNames;
import com.hemajoo.education.spring.queue.wow.message.broker.IMessage;
import com.hemajoo.education.spring.queue.wow.message.broker.RabbitMQService;
import com.hemajoo.education.wow.queue.commons.EventType;
import com.hemajoo.education.wow.queue.commons.MessageException;
import com.hemajoo.education.wow.queue.commons.ParticipantType;
import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import com.hemajoo.education.wow.queue.config.IMessageBrokerConfiguration;
import com.hemajoo.education.wow.queue.event.message.EventNotificationMessage;
import com.hemajoo.education.wow.queue.util.MessageRouter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

@Log4j2
public class GameEventService extends RabbitMQService
{
    private final EnumMap<EventType, List<String>> arenas = new EnumMap<>(EventType.class);

    public GameEventService(final @NonNull RabbitTemplate template, final SenderIdentity identity, final @NonNull GameQueueConfiguration queueConfiguration)
    {
        super(template, identity, queueConfiguration);
    }

    /**
     * Invoked when a message is available in the <b>game event service</b> queue.
     * @param message Message to process.
     */
    @RabbitListener(queues = { IGameRabbitMQNames.SERVICE_EVENT_QUEUE_NAME})
    public final void receivedMessage(final @NonNull IMessage message) throws MessageException
    {
        LOGGER.debug(String.format("Queue: '%s' delivered a message of type: '%s'", IMessageBrokerConfiguration.QUEUE_SERVICE_EVENT, message.getClass().getSimpleName()));

        switch (message.getType().getCategoryType())
        {
            case MESSAGE_CATEGORY_EVENT:
                processEventMessage(message);
                break;

            default:
                throw new NotYetImplementedException(String.format("Message of type: '%s : %s' is not yet handled!", message.getType().getCategoryType(), message.getType()));
        }
    }

    /**
     * Process <b>event request</b> messages.
     * @param message Message to process.
     * @throws MessageException Thrown when an error occurred with the message.
     */
    private void processEventMessage(final @NonNull IMessage message) throws MessageException
    {
        if (message.getData() == null)
        {
            throw new MessageException(String.format("Unable to process message: '%s:%s' because its content data is null!", message.getType().getCategoryType(), message.getType()));
        }

        EventType eventType = (EventType) message.getData();
        switch (eventType.getCategoryType())
        {
            case ARENA:
                processArenaEventMessage(message);
                break;

            case BATTLEGROUND:
                processBattlegroundEventMessage(message);
                break;

            case RAID:
                processRaidEventMessage(message);
                break;

            case DUNGEON:
                processDungeonEventMessage(message);
                break;
        }
    }

    /**
     * Process <b>arena event</b> messages.
     * @param message Message to process.
     * @throws MessageException Thrown when an error occurred with the message.
     */
    private void processArenaEventMessage(final @NonNull IMessage message) throws MessageException
    {
        EventType event = (EventType) message.getData();

        List<String> participants = arenas.get(event);
        if (participants == null)
        {
            participants = new ArrayList<>();
        }
        String playerReference = participants.stream().filter(participant -> participant.equals(message.getSender().getReference())).findFirst().orElse(null);
        if (playerReference != null)
        {
            // Send an error to the sender (player) as it is already registered for this event!
            EventNotificationMessage response = new EventNotificationMessage(EventNotificationMessage.MessageType.MESSAGE_EVENT_NOTIFICATION_REGISTRATION_REJECTED, getIdentity(), event);
            sendMessage(response, message.getSender().getTopicOrRoutingKey());
            MessageRouter.sendPlayerMessage(getTemplate(), response, message.getSender());
        }
        else
        {
            participants.add(message.getSender().getReference());
            arenas.put(event, participants);
            LOGGER.info(String.format("Player id: %s added to participants of event type: %s", message.getSender().getReference(), event));

            // Send response to sender to confirm registration
            EventNotificationMessage response = new EventNotificationMessage(
                    EventNotificationMessage.MessageType.MESSAGE_EVENT_NOTIFICATION_REGISTRATION_ACCEPTED,
                    SenderIdentity.builder()
                            .withType(ParticipantType.SERVICE_EVENT)
                            .withReference(null)
                            .build(),
                    event);

            //Exchange, RoutingKey = getExchangeInfoFor(PLAYER, AKGHY14589JUIK);

            MessageRouter.sendPlayerMessage(getTemplate(),response, message.getSender());
        }
    }

    /**
     * Process <b>battleground event</b> messages.
     * @param message Message to process.
     * @throws MessageException Thrown when an error occurred with the message.
     */
    private void processBattlegroundEventMessage(final @NonNull IMessage message) throws MessageException
    {
        // TODO Implement body.
    }

    /**
     * Process <b>raid event</b> messages.
     * @param message Message to process.
     * @throws MessageException Thrown when an error occurred with the message.
     */
    private void processRaidEventMessage(final @NonNull IMessage message) throws MessageException
    {
        // TODO Implement body.
    }

    /**
     * Process <b>dungeon event</b> messages.
     * @param message Message to process.
     * @throws MessageException Thrown when an error occurred with the message.
     */
    private void processDungeonEventMessage(final @NonNull IMessage message) throws MessageException
    {
        // TODO Implement body.
    }
}

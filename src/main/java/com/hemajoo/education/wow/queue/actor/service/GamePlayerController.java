package com.hemajoo.education.wow.queue.actor.service;

import com.hemajoo.commons.exception.NotYetImplementedException;
import com.hemajoo.education.spring.queue.game.config.GameQueueConfiguration;
import com.hemajoo.education.spring.queue.wow.message.broker.IMessage;
import com.hemajoo.education.wow.queue.commons.EventType;
import com.hemajoo.education.wow.queue.commons.ParticipantType;
import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import com.hemajoo.education.wow.queue.config.IMessageBrokerConfiguration;
import com.hemajoo.education.wow.queue.event.message.EventRequestMessage;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.NonNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/game/player")
public class GamePlayerController
{
    private final RabbitTemplate rabbitTemplate;

    public GamePlayerController(RabbitTemplate rabbitTemplate)
    {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Send an event registration message to the game event service.
     * @param senderReference Reference of the sender (player).
     * @param eventName Name of the event the player wants to register.
     */
    @PostMapping(value = "/register/dungeon")
    public void registerDungeon(
            @Parameter(description = "The reference of the sender.") final @NotNull @RequestParam String senderReference,
            @Parameter(description = "The name of the event (arena, battleground, raid or dungeon).") final @NotNull @RequestParam String eventName)
    {
        EventRequestMessage message = new EventRequestMessage(
                SenderIdentity.builder()
                                .withType(ParticipantType.PLAYER)
                                .withReference(senderReference)
                                .build(),
                EventType.from(eventName));

        rabbitTemplate.convertAndSend(IMessageBrokerConfiguration.EXCHANGE_SERVICE_EVENT, IMessageBrokerConfiguration.ROUTING_KEY, message);
    }

    private void deliver(final @NonNull GameQueueConfiguration queueConfiguration, final @NonNull IMessage message)
    {
        switch (queueConfiguration.getExchangeConfiguration().getExchangeType())
        {
            case DIRECT:
                deliverDirect(queueConfiguration, message);
                break;

            case TOPIC:
            case FANOUT:
            case HEADER:
                throw new NotYetImplementedException(String.format("Delivering to a queue exchange of type: '%s' is not yet handled!", queueConfiguration.getExchangeConfiguration().getExchangeType()));
        }
    }

    private void deliverDirect(final @NonNull GameQueueConfiguration queueConfiguration, final @NonNull IMessage message)
    {
        rabbitTemplate.convertAndSend(queueConfiguration.getExchangeConfiguration().getExchangeName(), queueConfiguration.getRoutingKeyConfiguration().name(), message);
    }
}

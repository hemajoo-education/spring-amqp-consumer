package com.hemajoo.education.wow.queue.actor.service;

import com.hemajoo.education.wow.queue.commons.ParticipantType;
import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import com.hemajoo.education.wow.queue.event.message.EventNotificationMessage;
import com.hemajoo.education.wow.queue.util.MessageProtocolRandomizer;
import com.hemajoo.education.wow.queue.util.MessageRouter;
import com.hemajoo.utility.random.GeneratorException;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/game/service/event")
public class GameEventServiceController
{
    private final RabbitTemplate rabbitTemplate;

    public GameEventServiceController(RabbitTemplate rabbitTemplate)
    {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Send random event notification messages to an agent.
     * @param count Number of messages to send.
     * @param key Agent key.
     * @throws GeneratorException
     */
    @PostMapping(value = "/random/notification")
    public void randomlySendEventNotificationMessagesToPlayer(
            @Parameter(description = "Number of random event notification messages to send") final @NotNull @RequestParam int count,
            @Parameter(description = "Agent key") final @NotNull @RequestParam String key) throws GeneratorException
    {
        EventNotificationMessage message;

        for (int i = 0; i < count; i++)
        {
            message = (EventNotificationMessage) MessageProtocolRandomizer.randomEventNotificationMessage(
                    SenderIdentity.builder()
                            .withType(ParticipantType.SERVICE_EVENT).build());

            MessageRouter.sendPlayerMessage(rabbitTemplate, message, key);
        }
    }
}

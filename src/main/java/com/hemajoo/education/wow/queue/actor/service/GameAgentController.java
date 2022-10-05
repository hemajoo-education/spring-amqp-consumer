package com.hemajoo.education.wow.queue.actor.service;

import com.hemajoo.education.wow.queue.commons.ParticipantType;
import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import com.hemajoo.education.wow.queue.event.message.EventNotificationMessage;
import com.hemajoo.education.wow.queue.util.MessageProtocolRandomizer;
import com.hemajoo.utility.random.GeneratorException;
import com.hemajoo.utility.string.StringExpander;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/game/service/event")
public class GameAgentController
{
    private final RabbitTemplate rabbitTemplate;

    public GameAgentController(RabbitTemplate rabbitTemplate)
    {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Send random event notification messages to an agent.
     * @param count Number of messages to send.
     * @param key Agent key.
     * @throws GeneratorException Thrown if an error occurred while generating a random event notification message.
     */
    @PostMapping(value = "/random/notification/queue")
    public void randomlySendEventNotificationMessagesThroughQueue(
            @Parameter(description = "Number of random event notification messages to send") final @NotNull @RequestParam int count,
            @Parameter(description = "Queue name") final @NotNull @RequestParam String queueName,
            @Parameter(description = "Key (if the queue name contains a variable)") final @RequestParam String key) throws GeneratorException
    {
        String name = queueName;
        EventNotificationMessage message;

        if (StringExpander.containsVariable(name))
        {
            name = StringExpander.expand(queueName, "key", key);
        }

        for (int i = 0; i < count; i++)
        {
            message = (EventNotificationMessage) MessageProtocolRandomizer.randomEventNotificationMessage(
                    SenderIdentity.builder()
                            .withType(ParticipantType.SERVICE_EVENT)
                            .build());

            rabbitTemplate.convertAndSend(name, message); // Directly send a message to a queue
        }
    }

    /**
     * Send random event notification messages to an agent.
     * @param count Number of messages to send.
     * @param exchange Exchange.
     * @param routingKey Routing key.
     * @throws GeneratorException Thrown if an error occurred while generating a random event notification message.
     */
    @PostMapping(value = "/random/notification/direct-exchange")
    public void randomlySendEventNotificationMessagesThroughDirectExchange(
            @Parameter(description = "Number of random event notification messages to send") final @NotNull @RequestParam int count,
            @Parameter(description = "Exchange") final @NotNull @RequestParam String exchange,
            @Parameter(description = "Routing key") final @RequestParam String routingKey) throws GeneratorException
    {
        EventNotificationMessage message;

        for (int i = 0; i < count; i++)
        {
            message = (EventNotificationMessage) MessageProtocolRandomizer.randomEventNotificationMessage(
                    SenderIdentity.builder()
                            .withType(ParticipantType.SERVICE_EVENT)
                            .build());

            rabbitTemplate.convertAndSend(exchange, routingKey, message); // Send a message to an exchange with a routing key
        }
    }
}

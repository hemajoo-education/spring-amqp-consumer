package com.hemajoo.education.wow.queue.util;

import com.hemajoo.education.spring.amqp.core.message.protocol.BaseMessage;
import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

@UtilityClass
public class MessageRouter
{
    public static void sendPlayerMessage(final @NonNull RabbitTemplate template, final @NonNull BaseMessage message, final SenderIdentity identity)
    {
        sendPlayerMessage(template, message, identity.getReference());
    }

    public static void sendPlayerMessage(final @NonNull RabbitTemplate template, final @NonNull BaseMessage message, final String playerReference)
    {
        MessageProperties properties = new MessageProperties();
        properties.setHeader("playerReference", playerReference);
        MessageConverter messageConverter = new SimpleMessageConverter();
        Message m = messageConverter.toMessage(message, properties);

//        template.convertAndSend(IMessageBrokerConfiguration.EXCHANGE_PLAYER_EVENT, "", m);
        //template.convertAndSend(IGameRabbitMQNames.PLAYER_EVENT_EXCHANGE_NAME, "", m);

        // Send message directly to a queue
        template.convertAndSend("com.hemajoo.education.spring.amqp.player.AKGHY14589JUIK.event", message);
    }
}

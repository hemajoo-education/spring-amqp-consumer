package com.hemajoo.education.wow.queue.util;

import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import com.hemajoo.education.wow.queue.config.IMessageBrokerConfiguration;
import com.hemajoo.education.wow.queue.event.message.BaseMessage;
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
    public static void sendPlayerMessage(final @NonNull RabbitTemplate template, final @NonNull BaseMessage message, final SenderIdentity playerIdentity)
    {
        sendPlayerMessage(template, message, playerIdentity.getId());
    }

    public static void sendPlayerMessage(final @NonNull RabbitTemplate template, final @NonNull BaseMessage message, final int playerId)
    {
        MessageProperties properties = new MessageProperties();
        properties.setHeader("playerId", Integer.toString(playerId));
        MessageConverter messageConverter = new SimpleMessageConverter();
        Message m = messageConverter.toMessage(message, properties);

        template.convertAndSend(IMessageBrokerConfiguration.EXCHANGE_PLAYER_EVENT, "", m);
    }
}

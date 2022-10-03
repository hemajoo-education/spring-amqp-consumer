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
package com.hemajoo.education.spring.queue.wow.message.broker;

import com.hemajoo.education.spring.queue.game.config.GameQueueConfiguration;
import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RabbitMQService implements IRabbitMQService
{
    @Getter
    private final RabbitTemplate template;

    @Getter
    private final GameQueueConfiguration queueConfiguration;

//    @Getter
//    private final Queue queue;
//
//    @Getter
//    private Exchange exchange;
//
//    @Getter
//    private final ExchangeType exchangeType;
//
//    @Getter
//    private Binding binding;

    @Getter
    private final SenderIdentity identity;

//    @Getter
//    private Header header;

    @Builder(setterPrefix = "with")
    public RabbitMQService(final @NonNull RabbitTemplate template, final @NonNull SenderIdentity identity, final @NonNull GameQueueConfiguration queueConfiguration)
    {
        this.template = template;
        this.queueConfiguration = queueConfiguration;
//        this.queue = new Queue(queueName);
//        this.exchangeType = exchangeType;
        this.identity = identity;

//        switch (exchangeType)
//        {
//            case DIRECT:
//                this.exchange = new DirectExchange(exchangeName);
//                if (routingKey != null)
//                {
//                    this.binding = BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
//                }
//                break;
//
//            case FANOUT:
//                this.exchange = new FanoutExchange(exchangeName);
//                break;
//
//            case TOPIC:
//                this.exchange = new TopicExchange(exchangeName);
//                if (topic != null)
//                {
//                    this.binding = BindingBuilder.bind(queue).to(exchange).with(topic).noargs();
//                }
//                break;
//
//            case HEADER:
//                this.exchange = new HeadersExchange(exchangeName);
//                break;
//        }
    }

//    public void setHeader(final @NonNull Header header)
//    {
//
//    }

    @Override
    public final void sendMessage(final @NonNull IMessage message, final String topicOrRoutingKey /*, final MessageHeader header*/)
    {
//        EventRequestMessage message = new EventRequestMessage(
//                SenderIdentity.builder()
//                        .withType(ParticipantType.PLAYER)
//                        .withId(senderId)
//                        .build(),
//                EventType.from(dungeonName));

//        template.convertAndSend(exchange.getName(), topicOrRoutingKey != null ? topicOrRoutingKey : "", message);
        template.convertAndSend(queueConfiguration.getExchangeConfiguration().getExchangeName(), topicOrRoutingKey != null ? topicOrRoutingKey : "", message);
    }
}

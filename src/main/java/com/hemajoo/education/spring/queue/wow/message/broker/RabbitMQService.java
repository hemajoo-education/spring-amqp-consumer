/*
 * The MIT License
 *
 * Copyright (c) 2019-2022 - Hemajoo Systems Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to
 * do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package com.hemajoo.education.spring.queue.wow.message.broker;

import com.hemajoo.education.spring.queue.wow.actor.service.event.ExchangeType;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.aspectj.bridge.IMessage;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RabbitMQService implements IRabbitMQService
{
    @Getter
    private final RabbitTemplate template;

    @Getter
    private Queue queue;

    @Getter
    private Exchange exchange;

    @Getter
    private ExchangeType exchangeType;

    @Getter
    private Binding binding;

//    @Getter
//    private Header header;

    @Builder(setterPrefix = "with")
    public RabbitMQService(final @NonNull RabbitTemplate template, final @NonNull String queueName, final @NonNull String exchangeName, final ExchangeType exchangeType, final String routingKey, final String topic)
    {
        this.template = template;
        this.queue = new Queue(queueName);
        this.exchangeType = exchangeType;

        switch (exchangeType)
        {
            case DIRECT:
                this.exchange = new DirectExchange(exchangeName);
                if (routingKey != null)
                {
                    this.binding = BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
                }
                break;

            case FANOUT:
                this.exchange = new FanoutExchange(exchangeName);
                break;

            case TOPIC:
                this.exchange = new TopicExchange(exchangeName);
                if (topic != null)
                {
                    this.binding = BindingBuilder.bind(queue).to(exchange).with(topic).noargs();
                }
                break;

            case HEADER:
                this.exchange = new HeadersExchange(exchangeName);
                break;
        }
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
//                        .withType(SenderType.PLAYER)
//                        .withId(senderId)
//                        .build(),
//                EventType.from(dungeonName));

        template.convertAndSend(exchange.getName(), topicOrRoutingKey != null ? topicOrRoutingKey : "", message);
    }
}

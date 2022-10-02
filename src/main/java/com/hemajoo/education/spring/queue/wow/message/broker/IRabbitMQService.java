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

import com.hemajoo.education.spring.queue.wow.actor.service.event.ExchangeType;
import lombok.NonNull;
import org.aspectj.bridge.IMessage;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public interface IRabbitMQService
{
    Queue getQueue();

    ExchangeType getExchangeType();

    Exchange getExchange();

    Binding getBinding();

    RabbitTemplate getTemplate();

    void sendMessage(final @NonNull IMessage message, final String topicOrRoutingKey /*, final MessageHeader header*/);
}

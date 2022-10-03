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
package com.hemajoo.education.spring.queue.game.config;

import com.hemajoo.education.spring.queue.game.config.rabbitmq.ExchangeConfigurationType;
import com.hemajoo.education.spring.queue.game.config.rabbitmq.IGameRabbitMQNames;
import com.hemajoo.education.spring.queue.game.config.rabbitmq.RoutingKeyConfigurationType;
import lombok.Getter;

public enum GameQueueConfiguration
{
    MQ_SERVICE_EVENT(
            IGameRabbitMQNames.SERVICE_EVENT_QUEUE_NAME,
            ExchangeConfigurationType.EXCHANGE_SERVICE_DIRECT,
            RoutingKeyConfigurationType.EVENT),

    MQ_PLAYER_EVENT(
            IGameRabbitMQNames.PLAYER_EVENT_QUEUE_NAME,
            ExchangeConfigurationType.EXCHANGE_SERVICE_DIRECT,
            RoutingKeyConfigurationType.EVENT);


    @Getter
    private final String queueName;

    @Getter
    private final ExchangeConfigurationType exchangeConfiguration;

    @Getter
    private final RoutingKeyConfigurationType routingKeyConfiguration;

    GameQueueConfiguration(final String queueName, final ExchangeConfigurationType exchangeConfiguration, final RoutingKeyConfigurationType routingKeyConfiguration)
    {
        this.queueName = queueName;
        this.exchangeConfiguration = exchangeConfiguration;
        this.routingKeyConfiguration = routingKeyConfiguration;
    }
}

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
package com.hemajoo.education.wow.queue;

public interface IGameRabbitMQNames
{
    // QUEUES
    String SERVICE_EVENT_QUEUE_NAME = "com.hemajoo.education.spring.message.broker.game.service.event.queue";

    String PLAYER_EVENT_QUEUE_NAME = "com.hemajoo.education.spring.message.broker.player.AKGHY14589JUIK.event.queue"; // TODO Dynamically build exchange name: com.hemajoo.education.spring-rabbitmq.player.${playerReference}.event.queue


    // EXCHANGES

    String SERVICE_EXCHANGE_NAME = "com.hemajoo.education.spring.message.broker.game.service.direct-exchange";
    String PLAYER_EVENT_EXCHANGE_NAME = "com.hemajoo.education.spring.message.broker.game.player.event.direct-exchange";
}


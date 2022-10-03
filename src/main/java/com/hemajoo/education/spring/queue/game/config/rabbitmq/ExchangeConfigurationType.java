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
package com.hemajoo.education.spring.queue.game.config.rabbitmq;

import lombok.Getter;
import lombok.NonNull;

public enum ExchangeConfigurationType
{
    EXCHANGE_SERVICE_DIRECT(ExchangeType.DIRECT, "com.hemajoo.education.spring.message.broker.game.service.direct-exchange"),

    EXCHANGE_PLAYER_DIRECT(ExchangeType.DIRECT, "com.hemajoo.education.spring.message.broker.game.player.event.direct-exchange");

    @Getter
    private final ExchangeType exchangeType;

    @Getter
    private final String exchangeName;

    ExchangeConfigurationType(final ExchangeType exchangeType, final @NonNull String exchangeName)
    {
        this.exchangeType = exchangeType;
        this.exchangeName = exchangeName;
    }
}
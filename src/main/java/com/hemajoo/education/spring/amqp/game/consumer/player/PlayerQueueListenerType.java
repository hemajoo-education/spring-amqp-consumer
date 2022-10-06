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
package com.hemajoo.education.spring.amqp.game.consumer.player;

import lombok.Getter;
import lombok.NonNull;

public enum PlayerQueueListenerType
{
    /**
     * Message listener for the <b>default</b> queue.
     */
    DEFAULT("onDefaultMessage", "DEFAULT"),

    /**
     * Message listener for the <b>chat</b> queue.
     */
    CHAT("onChatMessage", "CHAT"),

    /**
     * Message listener for the <b>event</b> queue.
     */
    EVENT("onEventMessage", "EVENT"),

    /**
     * Message listener for the <b>system</b> queue.
     */
    SYSTEM("onSystemMessage", "SYSTEM"),

    /**
     * Message listener for the <b>world</b> queue.
     */
    WORLD("onWorldMessage", "WORLD"),

    /**
     * Message listener for the <b>zone</b> queue.
     */
    ZONE("onZoneMessage", "ZONE"),

    /**
     * Message listener for the <b>postal</b> queue.
     */
    POSTAL("onPostalMessage", "POSTAL"),

    /**
     * Message listener for the <b>bank</b> queue.
     */
    BANK("onBankMessage", "BANK"),

    /**
     * Message listener for the <b>quest</b> queue.
     */
    QUEST("onQuestMessage", "QUEST"),

    /**
     * Message listener for the <b>auction house</b> queue.
     */
    AUCTION_HOUSE("onAuctionHouseMessage", "AH");

    /**
     * Message listener name.
     */
    @Getter
    private final String messageListenerName;

    /**
     * Routing key.
     */
    @Getter
    private final String routingKey;

    /**
     * Create a new player listener message type.
     * @param messageListenerName Player message listener type.
     * @param routingKey Routing key.
     */
    PlayerQueueListenerType(final @NonNull String messageListenerName, final @NonNull String routingKey)
    {
        this.messageListenerName = messageListenerName;
        this.routingKey = routingKey;
    }
}

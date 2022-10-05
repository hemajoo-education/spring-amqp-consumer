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
package com.hemajoo.education.spring.amqp.game.protocol;

import lombok.Getter;
import lombok.NonNull;

public enum QueueType
{
    /**
     * <b>Default</b> queue.
     */
    DEFAULT("default"),

    /**
     * Chat queue.
     */
    CHAT("chat"),

    /**
     * Event queue.
     */
    EVENT("event"),

    /**
     * System queue.
     */
    SYSTEM("system"),

    /**
     * World queue.
     */
    WORLD("world"),

    /**
     * Zone queue.
     */
    ZONE("zone"),

    /**
     * POSTAL queue.
     */
    POSTAL("postal"),

    /**
     * Bank queue.
     */
    BANK("bank"),

    /**
     * Quest queue.
     */
    QUEST("quest"),

    /**
     * Auction house queue.
     */
    AUCTION_HOUSE("ah");

    /**
     * Queue extension.
     */
    @Getter
    private final String extension;

    /**
     * Create a new queue type.
     * @param extension Queue extension.
     */
    QueueType(final @NonNull String extension)
    {
        this.extension = extension;
    }
}

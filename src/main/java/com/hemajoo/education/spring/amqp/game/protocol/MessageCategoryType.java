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

public enum MessageCategoryType
{
    /**
     * Event messages.
     */
    MESSAGE_CATEGORY_EVENT,

    /**
     * Event notification messages.
     */
    MESSAGE_CATEGORY_EVENT_NOTIFICATION,

    /**
     * Chat messages.
     */
    MESSAGE_CATEGORY_CHAT,

    /**
     * World messages.
     */
    MESSAGE_CATEGORY_WORLD,

    /**
     * System messages.
     */
    MESSAGE_CATEGORY_SYSTEM,

    /**
     * Group messages.
     */
    MESSAGE_CATEGORY_GROUP,

    /**
     * Auction house messages.
     */
    MESSAGE_CATEGORY_AUCTION_HOUSE,

    /**
     * Postal messages.
     */
    MESSAGE_CATEGORY_POSTAL,

    /**
     * Bank messages.
     */
    MESSAGE_CATEGORY_BANK,

    /**
     * Quest messages.
     */
    MESSAGE_CATEGORY_QUEST;
}

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
package com.hemajoo.education.spring.queue.wow;

import com.hemajoo.education.spring.amqp.agent.PlayerAgent;
import com.hemajoo.education.spring.amqp.base.agent.QueueType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class Runner implements CommandLineRunner
{
    @Autowired
    private PlayerAgent player;

    @Override
    public void run(String... args) throws Exception
    {
        // Create a dedicated queue for EVENT messages
        player.addQueueDefinition(
                QueueType.EVENT,
                "com.hemajoo.education.spring.amqp.player.${key}.event",
                player.getEventService(),
                "onEventMessage");

        // Create a dedicated queue for CHAT messages
        player.addQueueDefinition(QueueType.CHAT, "com.hemajoo.education.spring.amqp.player.${key}.chat", "onChatMessage");

        // Create a dedicated queue for AUCTION_HOUSE messages
        player.addQueueDefinition(QueueType.AUCTION_HOUSE, "com.hemajoo.education.spring.amqp.player.${key}.ah", "onAuctionHouseMessage");

        // Create a direct exchange and a binding for the EVENT routing key
        // All EVENT messages will be routed to the EVENT queue
        //player.addDirectExchangeDefinition(QueueType.EVENT, "com.hemajoo.education.spring.amqp.player.${key}.direct-exchange", "EVENT");

        // Create a direct exchange and a binding for the CHAT routing key
        // All EVENT messages will be routed to the CHAT queue
        player.addDirectExchangeDefinition(QueueType.CHAT, "com.hemajoo.education.spring.amqp.player.${key}.direct-exchange", "CHAT");

        // Create a direct exchange and a binding for the AUCTION_HOUSE routing key
        // All EVENT messages will be routed to the AUCTION_HOUSE queue
        player.addDirectExchangeDefinition(QueueType.AUCTION_HOUSE, "com.hemajoo.education.spring.amqp.player.${key}.direct-exchange", "AUCTION_HOUSE");
    }
}

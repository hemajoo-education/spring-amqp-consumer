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
        player.addQueueDefinition(QueueType.EVENT, "com.hemajoo.education.spring.amqp.player.${key}.event", "onEventMessage");
    }
}

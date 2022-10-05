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
package com.hemajoo.education.spring.amqp.agent;

import com.hemajoo.education.spring.amqp.base.agent.IRabbitAgent;
import com.hemajoo.education.spring.amqp.base.agent.RabbitAgent;
import com.hemajoo.education.spring.queue.wow.message.broker.IMessage;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.TimeUnit;

@Log4j2
public class EventService
{
    private final IRabbitAgent parent;

    public EventService(final @NonNull RabbitAgent parent)
    {
        this.parent = parent;
    }

    public void onEventMessage(@NonNull IMessage message)
    {
        LOGGER.debug(String.format("'%s:%s' received event message with category: '%s', type: '%s'", parent.getType(), parent.getKey(), message.getType().getCategoryType(), message.getType()));

        sleep(1);
    }

    private void sleep(final int nanoseconds)
    {
        try
        {
            TimeUnit.NANOSECONDS.sleep(nanoseconds);
        }
        catch (InterruptedException ie)
        {
            Thread.currentThread().interrupt();
        }
    }
}

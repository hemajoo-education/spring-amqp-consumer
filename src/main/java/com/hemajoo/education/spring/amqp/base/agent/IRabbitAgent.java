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
package com.hemajoo.education.spring.amqp.base.agent;

import lombok.NonNull;
import org.springframework.amqp.core.Queue;

/**
 * An <b>AMQP</b> (Advanced Messaging Querying Protocol) agent.
 * <br><br>
 * <b>Notes:</b><br>
 * An AMQP agent has can declare its own dedicated queues, exchanges and bindings.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface IRabbitAgent
{
    /**
     * Return the agent's key.
     * @return Key.
     */
    String getKey();

    AgentType getType();

    /**
     * Return a queue given its queue type. If no specific queue has been declared, then the agent's default queue is returned.
     * @param queueType Queue type.
     * @return Queue.
     */
    Queue getQueueByType(final @NonNull QueueType queueType);

    void addQueueDefinition(final @NonNull QueueType queueType, final @NonNull String queueName, final @NonNull Object delegate, final String methodListener) throws AgentConfigurationException;

    /**
     * Adds a queue definition for the agent.
     * @param queueType Queue type.
     * @param queueName Queue name (can contain variables to substitute such as the agent key).
     * @param methodListener If not null, a dynamic message listener will be created for this queue.
     * @throws AgentConfigurationException Thrown if an error occurred when initializing the agent.
     */
    void addQueueDefinition(final @NonNull QueueType queueType, final @NonNull String queueName, final String methodListener) throws AgentConfigurationException;

    void removeQueueDefinition(final @NonNull QueueType queueType);

    void updateQueueDefinition(final @NonNull QueueType queueType, final @NonNull String queueName);

    void addDirectExchangeDefinition(final @NonNull QueueType queueType, final @NonNull String exchangeName, final String routingKey) throws AgentConfigurationException;
}

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
package com.hemajoo.education.spring.amqp.core.agent;

import com.hemajoo.education.spring.amqp.game.protocol.QueueType;
import lombok.NonNull;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

/**
 * An <b>AMQP</b> (Advanced Messaging Querying Protocol) <b>RabbitMQ</b> queue(s) consumer.
 * <br><br>
 * <b>Notes:</b><br>
 * A RabbitMQ consumer can declare and setup dynamically its own dedicated queues, exchanges and bindings.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface IRabbitMQConsumer
{
    /**
     * Return the consumer type.
     * @return Type.
     */
    RabbitMQConsumerType getType();

    /**
     * Return the consumer key.
     * @return Key.
     */
    String getKey();

    /**
     * Return the queue handled by this consumer given a queue type (if no specific queue has been declared, the consumer's <b>default</b> queue is returned).
     * @param queueType Queue type.
     * @return Queue.
     */
    Queue getQueue(final @NonNull QueueType queueType);

    /**
     * Return the message listener handled by this consumer and associated to a given queue type (if no specific queue has been declared, the consumer's <b>default</b> queue is returned).
     * @param queueType Queue type.
     * @return Message listener container attached to the given queue.
     */
    SimpleMessageListenerContainer getMessageListenerContainer(final QueueType queueType);

    void addQueueDefinition(final @NonNull QueueType queueType, final @NonNull String queueName, final @NonNull Object delegate, final String methodListener) throws RabbitMQConsumerException;

    /**
     * Add a queue definition for this <b>RabbitMQ</b> consumer.
     * @param queueType Queue type.
     * @param queueName Queue name (can contain variables to substitute such as the agent key).
     * @param methodListener If not null, a dynamic message listener will be created for this queue.
     * @throws RabbitMQConsumerException Thrown when an error occurred while configuring a <b>RabbitMQ</b> consumer.
     */
    void addQueueDefinition(final @NonNull QueueType queueType, final @NonNull String queueName, final String methodListener) throws RabbitMQConsumerException;

    void removeQueueDefinition(final @NonNull QueueType queueType);

    void updateQueueDefinition(final @NonNull QueueType queueType, final @NonNull String queueName);

    void addDirectExchangeDefinition(final @NonNull QueueType queueType, final @NonNull String exchangeName, final String routingKey) throws RabbitMQConsumerException;
}

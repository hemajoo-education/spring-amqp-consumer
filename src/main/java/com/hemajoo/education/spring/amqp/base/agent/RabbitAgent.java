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

import com.hemajoo.commons.exception.NotYetImplementedException;
import com.hemajoo.education.spring.queue.wow.message.broker.IMessage;
import com.hemajoo.utility.string.StringExpander;
import com.hemajoo.utility.string.StringExpanderException;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.EnumMap;

@Log4j2
public abstract class RabbitAgent implements IRabbitAgent
{
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private final AmqpAdmin rabbitAdmin;

    @Autowired
    private final ConnectionFactory connectionFactory;

    /**
     * Agent type.
     */
    @Getter
    private final AgentType type;

    /**
     * Agent key.
     */
    @Getter
    private final String key;

    /**
     * Collection of queues (by queue type).
     */
    private final EnumMap<QueueType, String> queues = new EnumMap<>(QueueType.class);

    /**
     * Create a new <b>RabbitMQ</b> agent.
     * @param type Agent type.
     * @param key Agent key.
     */
    protected RabbitAgent(final AgentType type, final @NonNull String key, final QueueType defaultQueueType, final @NonNull String defaultQueueName) throws AgentConfigurationException
    {
        this.type = type;
        this.key = key;
        this.connectionFactory = new CachingConnectionFactory("localhost");
        this.rabbitTemplate = new RabbitTemplate(connectionFactory);
        this.rabbitAdmin = new RabbitAdmin(connectionFactory);

        addQueueDefinition(defaultQueueType, defaultQueueName, "onDefaultMessage");
    }

    @Override
    public final String getQueueNameFor(@NonNull QueueType queueType)
    {
        String name = queues.get(queueType);

        return name != null ? name : queues.get(QueueType.DEFAULT);
    }

    @Override
    public final void addQueueDefinition(final @NonNull QueueType queueType, final @NonNull String queueName, final String methodListener) throws AgentConfigurationException
    {
        String name = queueName;

        if (queues.containsKey(queueType))
        {
            throw new AgentConfigurationException(String.format("Cannot declare queue: '%s' for agent: '%s-%s' because a queue is already declared for type: '%s'!", queueName, type, key, queueType));
        }

        if (StringExpander.containsVariable(name))
        {
            try
            {
                name = StringExpander.expandVariables(this, name);
                queues.put(queueType, name);
                rabbitAdmin.declareQueue(new Queue(name));

                LOGGER.debug(String.format("Successfully declared queue: '%s' for agent: '%s-%s'", name, type, key));

                if (methodListener != null)
                {
                    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
                    container.setConnectionFactory(connectionFactory);
                    container.setQueueNames(name);
                    container.setMessageListener(new MessageListenerAdapter(this, methodListener));
                    container.start();
                }
            }
            catch (StringExpanderException e)
            {
                throw new AgentConfigurationException(e);
            }
        }
    }

    @Override
    public final void removeQueueDefinition(@NonNull QueueType queueType)
    {
        throw new NotYetImplementedException("Not yet implemented!");
    }

    @Override
    public final void updateQueueDefinition(@NonNull QueueType queueType, @NonNull String queueName)
    {
        throw new NotYetImplementedException("Not yet implemented!");
    }

    public abstract void onDefaultMessage(final @NonNull IMessage message);
}

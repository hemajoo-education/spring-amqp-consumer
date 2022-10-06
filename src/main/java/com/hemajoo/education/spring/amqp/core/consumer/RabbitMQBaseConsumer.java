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
package com.hemajoo.education.spring.amqp.core.consumer;

import com.hemajoo.commons.exception.NotYetImplementedException;
import com.hemajoo.education.spring.amqp.core.message.protocol.IMessage;
import com.hemajoo.education.spring.amqp.game.protocol.QueueType;
import com.hemajoo.education.wow.queue.ExchangeType;
import com.hemajoo.utility.string.StringExpander;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.util.EnumMap;

/**
 * Provide an abstract implementation of a <b>RabbitMQ</b> queue(s) consumer.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@PropertySource("classpath:application.properties")
@Log4j2
public abstract class RabbitMQBaseConsumer implements IRabbitMQConsumer
{
    /**
     * Name of the listener method used to pull messages from the consumer's <b>default</b> queue.
     */
    private static final String LISTENER_METHOD_DEFAULT = "onDefaultMessage";

    @Value("${amqp.variable.placeholder.character}")
    private String amqpVariablePlaceholderCharacter;

    /**
     * <b>AMQP</b> template object.
     */
    private final RabbitTemplate rabbitTemplate;

    /**
     * <b>AMQP</b> administration object.
     */
    @Autowired
    private final AmqpAdmin rabbitAdmin;

    /**
     * <b>AMQP</b> connection factory.
     */
    @Autowired
    private final ConnectionFactory connectionFactory;

    /**
     * Consumer type.
     */
    @Getter
    private final RabbitMQConsumerType type;

    /**
     * Consumer key.
     */
    @Getter
    private final String key;

    /**
     * Collection of queues handled by this consumer (by queue type).
     */
    private final EnumMap<QueueType, Queue> queues = new EnumMap<>(QueueType.class);

    /**
     * Collection of listener containers handled by this consumer (by queue type)
     */
    private final EnumMap<QueueType, SimpleMessageListenerContainer> containers = new EnumMap<>(QueueType.class);

    /**
     * Create a new <b>RabbitMQ</b> base consumer.
     * @param type Consumer type.
     * @param key Consumer key (identifier).
     * @throws RabbitMQConsumerException Thrown when an error occurred while configuring a <b>RabbitMQ</b> consumer.
     */
    protected RabbitMQBaseConsumer(final RabbitMQConsumerType type, final String key) throws RabbitMQConsumerException
    {
        this.type = type;
        this.key = key == null ? RandomStringUtils.random(16, true, true).toUpperCase() : key;
        this.connectionFactory = new CachingConnectionFactory("localhost"); // TODO Should be externalized
        this.rabbitTemplate = new RabbitTemplate(connectionFactory); // Serialized message converter
        this.rabbitAdmin = new RabbitAdmin(connectionFactory);
    }

    @Override
    public final Queue getQueue(@NonNull QueueType queueType)
    {
        Queue queue = queues.get(queueType);

        return queue != null ? queue : queues.get(QueueType.DEFAULT);
    }

    @Override
    public final SimpleMessageListenerContainer getMessageListenerContainer(final QueueType queueType)
    {
        return containers.get(queueType);
    }

    @Override
    public final void createQueue(final @NonNull QueueType queueType, final @NonNull String queueName, final String methodListener) throws RabbitMQConsumerException
    {
        createQueue(queueType, queueName, this, methodListener);
    }

    @Override
    public final void createQueue(final @NonNull QueueType queueType, final @NonNull String queueName, final @NonNull Object delegate, final String methodListener) throws RabbitMQConsumerException
    {
        String name = queueName + queueType.getExtension();

        if (queues.containsKey(queueType))
        {
            throw new RabbitMQConsumerException(String.format("Cannot declare queue: '%s' for agent: '%s-%s' because a queue is already declared for type: '%s'!", queueName, type, key, queueType));
        }

        name = StringExpander.expand(amqpVariablePlaceholderCharacter, name, "type", type.name().toUpperCase());
        name = StringExpander.expand(amqpVariablePlaceholderCharacter, name, "key", key);

        Queue queue = new Queue(name);
        queues.put(queueType, queue);
        rabbitAdmin.declareQueue(queue);

        LOGGER.debug(String.format("Declared queue: '%s' for consumer: '%s-%s'", name, type, key));

        if (methodListener != null)
        {
            SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
            container.setConnectionFactory(connectionFactory);
            container.setQueueNames(name);
            container.setMessageListener(new MessageListenerAdapter(delegate, methodListener));

            container.start();
            containers.put(queueType, container);
        }
    }

    @Override
    public final void addExchange(final @NonNull QueueType queueType, final @NonNull ExchangeType exchangeType, final @NonNull String exchangeName, final String routingKey)
    {
        switch (exchangeType)
        {
            case DIRECT:
                createDirectExchange(queueType, exchangeName, routingKey);
                break;

            case TOPIC:
                break;

            case FANOUT:
                break;

            case HEADER:
                break;
        }
    }

    /**
     * Create a direct exchange and its binding.
     * @param queueType Queue type.
     * @param exchangeName Direct exchange name.
     * @param routingKey Routing key.
     */
    private void createDirectExchange(final @NonNull QueueType queueType, final @NonNull String exchangeName, final String routingKey)
    {
        String name = exchangeName;
        Queue queue = getQueue(queueType);

        if (queue != null)
        {
            name = StringExpander.expand(amqpVariablePlaceholderCharacter, name, "type", type.name().toUpperCase());
            name = StringExpander.expand(amqpVariablePlaceholderCharacter, name, "key", key);
            DirectExchange exchange = new DirectExchange(name);
            rabbitAdmin.declareExchange(exchange);
            rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));

            LOGGER.debug(String.format("Declared direct exchange: '%s' for consumer: '%s-%s'", name, type, key));
        }
    }


    @Override
    public final void deleteQueue(@NonNull QueueType queueType)
    {
        throw new NotYetImplementedException("Not yet implemented!");
    }

    @Override
    public final void updateQueue(@NonNull QueueType queueType, @NonNull String queueName)
    {
        throw new NotYetImplementedException("Not yet implemented!");
    }

    public abstract void onDefaultMessage(final @NonNull IMessage message) throws RabbitMQConsumerException;
}

package com.hemajoo.education.wow.queue;

import com.hemajoo.education.wow.queue.event.consumer.WowServiceEvent;
import com.hemajoo.education.wow.queue.event.producer.WowEventMessageController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackageClasses = { WowEventMessageController.class, WowServiceEvent.class })
@SpringBootApplication
public class QueueMessagingApplication
{
//    public static final String topicExchangeName = "spring-boot-exchange";
//
//    public static final String queueName = "spring-boot";

//    @Bean
//    Queue queue()
//    {
//        return new Queue(queueName, false);
//    }

//    @Bean
//    TopicExchange exchange()
//    {
//        return new TopicExchange(topicExchangeName);
//    }

//    @Bean
//    Binding binding(Queue queue, TopicExchange exchange)
//    {
//        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
//    }

//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter)
//    {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(queueName);
//        container.setMessageListener(listenerAdapter);
//        return container;
//    }

//    @Bean
//    MessageListenerAdapter listenerAdapter(Receiver receiver)
//    {
//        return new MessageListenerAdapter(receiver, "receiveMessage");
//    }

    public static void main(String[] args)
    {
        SpringApplication.run(QueueMessagingApplication.class, args);
    }
}

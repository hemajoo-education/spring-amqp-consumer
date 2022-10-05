package com.hemajoo.education.spring.queue.wow;

import com.hemajoo.education.spring.amqp.agent.PlayerAgent;
import com.hemajoo.education.spring.amqp.base.agent.AgentConfigurationException;
import com.hemajoo.education.spring.amqp.base.agent.AgentType;
import com.hemajoo.education.spring.amqp.base.agent.QueueType;
import com.hemajoo.education.wow.queue.actor.service.GameAgentController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackageClasses = { GameAgentController.class, Runner.class /*, EventService.class, GamePlayerService.class*/ })
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class SpringBootWowQueueApplication
{
    @Bean
    PlayerAgent playerAgent() throws AgentConfigurationException
    {
        return new PlayerAgent(
                AgentType.PLAYER,
                "AKGHY14589JUIK",
                QueueType.DEFAULT,
                "com.hemajoo.education.spring.amqp.player.${key}.default");
    }

//    @Bean
//    GameEventService gameEventService()
//    {
//        return new GameEventService(
//                new RabbitTemplate(),
//                SenderIdentity.builder()
//                        .withType(ParticipantType.SERVICE_EVENT)
//                        .withReference(null)
//                        .build(),
//                GameQueueConfiguration.MQ_SERVICE_EVENT);
//    }

//    @Bean
//    GamePlayerService gamePlayerService()
//    {
//        return new GamePlayerService(
//                new RabbitTemplate(),
//                SenderIdentity.builder()
//                        .withType(ParticipantType.PLAYER)
//                        .withReference("AKGHY14589JUIK")
//                        .build(),
//                GameQueueConfiguration.MQ_PLAYER_EVENT);
//    }

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
        SpringApplication.run(SpringBootWowQueueApplication.class, args);
    }
}

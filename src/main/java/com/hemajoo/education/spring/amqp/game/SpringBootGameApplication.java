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
package com.hemajoo.education.spring.amqp.game;

import com.hemajoo.education.spring.amqp.core.consumer.RabbitMQConsumerException;
import com.hemajoo.education.spring.amqp.core.consumer.RabbitMQConsumerType;
import com.hemajoo.education.spring.amqp.game.agent.player.PlayerConsumer;
import com.hemajoo.education.spring.amqp.game.rest.controller.RestEventMessageController;
import com.hemajoo.education.spring.amqp.game.rest.controller.RestSystemMessageController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackageClasses = { RestEventMessageController.class, RestSystemMessageController.class, Runner.class })
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class SpringBootGameApplication
{
    @Bean
    PlayerConsumer playerAgent() throws RabbitMQConsumerException
    {
        return new PlayerConsumer(RabbitMQConsumerType.PLAYER,"AK098YHFG336QSWX","com.hemajoo.education.spring.amqp.${type}.${key}.default");
    }

    public static void main(String[] args)
    {
        SpringApplication.run(SpringBootGameApplication.class, args);
    }
}

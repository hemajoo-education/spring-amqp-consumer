package com.hemajoo.education.wow.queue.config;

public interface IMessageBrokerConfiguration
{
    /**
     * Name of the generic <b>event service</b> queue.
     */
    String QUEUE_SERVICE_EVENT = "wow.service.event";

    /**
     * Name of the generic <b>event service</b> exchange.
     */
    String EXCHANGE_SERVICE_EVENT = "wow.service.event.direct-exchange";

    /**
     * Name of the <b>routing key</b>.
     */
    String ROUTING_KEY = "SERVICE_EVENT";
}

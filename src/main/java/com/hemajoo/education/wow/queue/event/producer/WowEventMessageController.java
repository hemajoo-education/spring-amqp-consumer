package com.hemajoo.education.wow.queue.event.producer;

import com.hemajoo.education.wow.queue.config.IMessageBrokerConfiguration;
import com.hemajoo.education.wow.queue.event.message.EventRequestMessage;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/wow/event")
public class WowEventMessageController
{
    private final RabbitTemplate template;

//    private final Receiver receiver;

    public WowEventMessageController(RabbitTemplate template/*, Receiver receiver*/)
    {
        this.template = template;
//        this.receiver = receiver;
    }

    @PostMapping(value = "/register/battleground" )
//    public void eventBattlegroundRegistration(final @ParameterObject EventRequestMessage event)
    public void eventBattlegroundRegistration(final @NotNull @RequestParam String senderId, final @NotNull @RequestParam String battleground)
    {
        EventRequestMessage message = EventRequestMessage.builder()
                .withSenderType("PLAYER")
                .withSenderIdentity(senderId)
                .withMessageCategoryType("MESSAGE_CATEGORY_EVENT")
                .withMessageType("MESSAGE_EVENT_REGISTRATION_REQUEST")
                .withEventType("BATTLEGROUND")
                .withEventReference(battleground)
                .build();

//        template.convertAndSend(QueueMessagingApplication.topicExchangeName, "foo.bar.baz", message);
        template.convertAndSend(IMessageBrokerConfiguration.EXCHANGE_SERVICE_EVENT, IMessageBrokerConfiguration.ROUTING_KEY, message);
    }

    @PostMapping(value = "/register/arena" )
    public void registerArena(final @NotNull @RequestParam String senderId, final @NotNull @RequestParam String arenaName)
    {
        EventRequestMessage message = EventRequestMessage.builder()
                .withSenderType("PLAYER")
                .withSenderIdentity(senderId)
                .withMessageCategoryType("MESSAGE_CATEGORY_EVENT")
                .withMessageType("MESSAGE_EVENT_REGISTRATION_REQUEST")
                .withEventType("ARENA")
                .withEventReference(arenaName)
                .build();

        template.convertAndSend(IMessageBrokerConfiguration.EXCHANGE_SERVICE_EVENT, IMessageBrokerConfiguration.ROUTING_KEY, message);
    }

    @PostMapping(value = "/register/raid" )
    public void registerRaid(final @NotNull @RequestParam String senderId, final @NotNull @RequestParam String raidName)
    {
        EventRequestMessage message = EventRequestMessage.builder()
                .withSenderType("PLAYER")
                .withSenderIdentity(senderId)
                .withMessageCategoryType("MESSAGE_CATEGORY_EVENT")
                .withMessageType("MESSAGE_EVENT_REGISTRATION_REQUEST")
                .withEventType("RAID")
                .withEventReference(raidName)
                .build();

        template.convertAndSend(IMessageBrokerConfiguration.EXCHANGE_SERVICE_EVENT, IMessageBrokerConfiguration.ROUTING_KEY, message);
    }


    @PostMapping(value = "/register/dungeon")
    public void registerDungeon(@Parameter(description = "The identifier of the sender.") final @NotNull @RequestParam String senderId,
                                @Parameter(description = "The name of the dungeon.") final @NotNull @RequestParam String dungeonName)
    {
        EventRequestMessage message = EventRequestMessage.builder()
                .withSenderType("PLAYER")
                .withSenderIdentity(senderId)
                .withMessageCategoryType("MESSAGE_CATEGORY_EVENT")
                .withMessageType("MESSAGE_EVENT_REGISTRATION_REQUEST")
                .withEventType("DUNGEON")
                .withEventReference(dungeonName)
                .build();

        template.convertAndSend(IMessageBrokerConfiguration.EXCHANGE_SERVICE_EVENT, IMessageBrokerConfiguration.ROUTING_KEY, message);
    }
}

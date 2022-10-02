package com.hemajoo.education.wow.queue.actor.service;

import com.hemajoo.education.wow.queue.commons.EventType;
import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import com.hemajoo.education.wow.queue.commons.SenderType;
import com.hemajoo.education.wow.queue.config.IMessageBrokerConfiguration;
import com.hemajoo.education.wow.queue.event.message.EventNotificationMessage;
import com.hemajoo.education.wow.queue.event.message.EventRequestMessage;
import com.hemajoo.education.wow.queue.util.MessageProtocolRandomizer;
import com.hemajoo.education.wow.queue.util.MessageRouter;
import com.hemajoo.utility.random.GeneratorException;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/wow/event")
public class EventServiceController
{
    private final RabbitTemplate rabbitTemplate;

//    private final Receiver receiver;

    public EventServiceController(RabbitTemplate rabbitTemplate/*, Receiver receiver*/)
    {
        this.rabbitTemplate = rabbitTemplate;
//        this.receiver = receiver;
    }

//    @PostMapping(value = "/register/battleground" )
////    public void eventBattlegroundRegistration(final @ParameterObject EventRequestMessage event)
//    public void eventBattlegroundRegistration(final @NotNull @RequestParam String senderId, final @NotNull @RequestParam String battleground)
//    {
//        EventRequestMessage message = EventRequestMessage.builder()
//                .withSenderType("PLAYER")
//                .withSenderIdentity(senderId)
//                .withMessageCategoryType("MESSAGE_CATEGORY_EVENT")
//                .withMessageType("MESSAGE_EVENT_REGISTRATION_REQUEST")
//                .withEventType("BATTLEGROUND")
//                .withEventReference(battleground)
//                .build();
//
////        template.convertAndSend(SpringBootWowQueueApplication.topicExchangeName, "foo.bar.baz", message);
//        template.convertAndSend(IMessageBrokerConfiguration.EXCHANGE_SERVICE_EVENT, IMessageBrokerConfiguration.ROUTING_KEY, message);
//    }
//
//    @PostMapping(value = "/register/arena" )
//    public void registerArena(final @NotNull @RequestParam String senderId, final @NotNull @RequestParam String arenaName)
//    {
//        EventRequestMessage message = EventRequestMessage.builder()
//                .withSenderType("PLAYER")
//                .withSenderIdentity(senderId)
//                .withMessageCategoryType("MESSAGE_CATEGORY_EVENT")
//                .withMessageType("MESSAGE_EVENT_REGISTRATION_REQUEST")
//                .withEventType("ARENA")
//                .withEventReference(arenaName)
//                .build();
//
//        template.convertAndSend(IMessageBrokerConfiguration.EXCHANGE_SERVICE_EVENT, IMessageBrokerConfiguration.ROUTING_KEY, message);
//    }
//
//    @PostMapping(value = "/register/raid" )
//    public void registerRaid(final @NotNull @RequestParam String senderId, final @NotNull @RequestParam String raidName)
//    {
//        EventRequestMessage message = EventRequestMessage.builder()
//                .withSenderType("PLAYER")
//                .withSenderIdentity(senderId)
//                .withMessageCategoryType("MESSAGE_CATEGORY_EVENT")
//                .withMessageType("MESSAGE_EVENT_REGISTRATION_REQUEST")
//                .withEventType("RAID")
//                .withEventReference(raidName)
//                .build();
//
//        template.convertAndSend(IMessageBrokerConfiguration.EXCHANGE_SERVICE_EVENT, IMessageBrokerConfiguration.ROUTING_KEY, message);
//    }

    @PostMapping(value = "/register/dungeon")
    public void registerDungeon(
            @Parameter(description = "The identifier of the sender.") final @NotNull @RequestParam int senderId,
            @Parameter(description = "The name of the dungeon.") final @NotNull @RequestParam String dungeonName)
    {
        EventRequestMessage message = new EventRequestMessage(
                SenderIdentity.builder()
                                .withType(SenderType.PLAYER)
                                .withId(senderId)
                                .build(),
                EventType.from(dungeonName));

        rabbitTemplate.convertAndSend(IMessageBrokerConfiguration.EXCHANGE_SERVICE_EVENT, IMessageBrokerConfiguration.ROUTING_KEY, message);
    }

    @PostMapping(value = "/random/notification")
    public void randomlySendEventNotificationMessagesToPlayer(
            @Parameter(description = "Number of random event notification messages to send") final @NotNull @RequestParam int count,
            @Parameter(description = "Identifier of the player") final @NotNull @RequestParam int playerId) throws GeneratorException
    {
        EventNotificationMessage message;

        for (int i = 0; i < count; i++)
        {
            message = (EventNotificationMessage) MessageProtocolRandomizer.randomEventNotificationMessage(
                    SenderIdentity.builder()
                            .withType(SenderType.SERVICE_EVENT).build());

            MessageRouter.sendPlayerMessage(rabbitTemplate, message, playerId);
        }
    }
}

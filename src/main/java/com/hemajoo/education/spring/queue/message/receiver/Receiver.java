package com.hemajoo.education.spring.queue.message.receiver;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class Receiver
{
//    private final static String QUEUE_WOW_ARENA = "wow.arena";
//    private final static String QUEUE_WOW_BATTLEGROUND = "wow.battleground";
//    private final static String QUEUE_WOW_RAID = "wow.raid";
//    private final static String QUEUE_WOW_INSTANCE = "wow.instance";
//
//    @RabbitListener(queues = { QUEUE_WOW_ARENA })
//    public void receiveMessageForArena(EventRequestMessage event)
//    {
//        LOGGER.info(String.format("Received a message of type: '%s' with name '%s' for avatar: '%s' with level: '%s'",
//                event.getEventType(), event.getEventName(), event.getAvatarName(), event.getAvatarLevel()));
//    }
//
//    @RabbitListener(queues = { QUEUE_WOW_BATTLEGROUND })
//    public void receiveMessageForBattleground(EventRequestMessage event)
//    {
//        LOGGER.info(String.format("Received a request to join battleground: %s for avatar: %s", event.getAvatarName()));
//    }
//
//    @RabbitListener(queues = { QUEUE_WOW_RAID })
//    public void receiveMessageForRaid(EventRequestMessage event)
//    {
//        LOGGER.info(String.format("Received a request to join raid: %s for avatar: %s", event.getAvatarName()));
//    }
//
//    @RabbitListener(queues = { QUEUE_WOW_INSTANCE })
//    public void receiveMessageForInstance(EventRequestMessage event)
//    {
//        LOGGER.info(String.format("Received a request to join instance: %s for avatar: %s", event.getAvatarName()));
//    }
}

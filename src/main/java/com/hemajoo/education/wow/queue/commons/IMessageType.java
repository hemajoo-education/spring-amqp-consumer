package com.hemajoo.education.wow.queue.commons;

import com.hemajoo.education.wow.queue.event.message.EventNotificationMessage;

import java.io.Serializable;
import java.util.Arrays;

public interface IMessageType extends Serializable
{
    MessageCategoryType getCategoryType();

    static EventNotificationMessage.MessageType from(EventNotificationMessage.MessageType type)
    {
        return Arrays.stream(EventNotificationMessage.MessageType.values()).filter(e -> e == type).findFirst().orElse(null);
    }
}

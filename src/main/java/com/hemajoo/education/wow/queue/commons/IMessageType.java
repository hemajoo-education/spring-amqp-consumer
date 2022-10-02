package com.hemajoo.education.wow.queue.commons;

import java.io.Serializable;

public interface IMessageType extends Serializable
{
    MessageCategoryType getCategoryType();

    Class<?> getDataClass();

//    static EventNotificationMessage.MessageType from(EventNotificationMessage.MessageType type)
//    {
//        return Arrays.stream(EventNotificationMessage.MessageType.values()).filter(e -> e == type).findFirst().orElse(null);
//    }
}

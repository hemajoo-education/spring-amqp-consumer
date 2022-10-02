package com.hemajoo.education.wow.queue.event.message;

import com.hemajoo.education.wow.queue.commons.EventType;
import com.hemajoo.education.wow.queue.commons.IMessageType;
import com.hemajoo.education.wow.queue.commons.MessageCategoryType;
import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Schema(description = "Event notification message")
public class EventNotificationMessage extends BaseMessage
{
    public enum MessageType implements IMessageType
    {
        MESSAGE_EVENT_NOTIFICATION_REGISTRATION_ACCEPTED(MessageCategoryType.MESSAGE_CATEGORY_EVENT_NOTIFICATION, EventType.class), // SERVICE AGENT -> PLAYER

        MESSAGE_EVENT_NOTIFICATION_REGISTRATION_REJECTED(MessageCategoryType.MESSAGE_CATEGORY_EVENT_NOTIFICATION, EventType.class), // SERVICE AGENT -> PLAYER

        MESSAGE_EVENT_NOTIFICATION_REMAINING_TIME_CHANGED(MessageCategoryType.MESSAGE_CATEGORY_EVENT_NOTIFICATION, EventType.class), // SERVICE AGENT -> PLAYER

        MESSAGE_EVENT_NOTIFICATION_PAUSED(MessageCategoryType.MESSAGE_CATEGORY_EVENT_NOTIFICATION, EventType.class),

        MESSAGE_EVENT_NOTIFICATION_RESUMED(MessageCategoryType.MESSAGE_CATEGORY_EVENT_NOTIFICATION, EventType.class),

        MESSAGE_EVENT_NOTIFICATION_CANCELLED(MessageCategoryType.MESSAGE_CATEGORY_EVENT_NOTIFICATION, EventType.class); // SERVICE AGENT -> PLAYER

        @Getter
        private MessageCategoryType categoryType;

        @Getter
        private Class<?> dataClass;

        MessageType(final MessageCategoryType categoryType, Class<?> dataClass)
        {
            this.categoryType = categoryType;
            this.dataClass = dataClass;
        }
    }

    public EventNotificationMessage(IMessageType messageType, SenderIdentity sender, Object data)
    {
        super(sender, messageType, data);
    }
}

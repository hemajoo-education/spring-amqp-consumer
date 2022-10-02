package com.hemajoo.education.wow.queue.event.message;

import com.hemajoo.education.wow.queue.commons.IMessageType;
import com.hemajoo.education.wow.queue.commons.MessageCategoryType;
import com.hemajoo.education.wow.queue.commons.MessageProtocol;
import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Schema(description = "Event notification message")
public class EventNotificationMessage extends BaseMessage
{
    @Schema(description = "Message")
    @Getter
    private String message;

    @Schema(description = "Message content")
    @Getter
    private Object data;

    public enum MessageType implements IMessageType
    {
        MESSAGE_EVENT_NOTIFICATION_REGISTRATION_ACCEPTED(MessageCategoryType.MESSAGE_CATEGORY_EVENT_NOTIFICATION), // SERVICE AGENT -> PLAYER

        MESSAGE_EVENT_NOTIFICATION_REGISTRATION_REJECTED(MessageCategoryType.MESSAGE_CATEGORY_EVENT_NOTIFICATION), // SERVICE AGENT -> PLAYER

        MESSAGE_EVENT_NOTIFICATION_ESTIMATES_TIME_CHANGED(MessageCategoryType.MESSAGE_CATEGORY_EVENT_NOTIFICATION), // SERVICE AGENT -> PLAYER

        MESSAGE_EVENT_NOTIFICATION_PAUSED(MessageCategoryType.MESSAGE_CATEGORY_EVENT_NOTIFICATION),

        MESSAGE_EVENT_NOTIFICATION_RESUMED(MessageCategoryType.MESSAGE_CATEGORY_EVENT_NOTIFICATION),

        MESSAGE_EVENT_NOTIFICATION_CANCELLED(MessageCategoryType.MESSAGE_CATEGORY_EVENT_NOTIFICATION); // SERVICE AGENT -> PLAYER

        @Getter
        private MessageCategoryType categoryType;

        MessageType(final MessageCategoryType categoryType)
        {
            this.categoryType = categoryType;
        }

//        public static MessageType from(MessageType type)
//        {
//            return Arrays.stream(MessageType.values()).filter(e -> e == type).findFirst().orElse(null);
//        }
    }

    public EventNotificationMessage(MessageProtocol messageProtocol, SenderIdentity senderIdentity, Object data)
    {
        super(senderIdentity, messageProtocol);

        this.data = data;
    }

    public EventNotificationMessage(MessageProtocol messageProtocol, SenderIdentity senderIdentity, String message, Object data)
    {
        super(senderIdentity, messageProtocol);

        this.message = message;
        this.data = data;
    }
}

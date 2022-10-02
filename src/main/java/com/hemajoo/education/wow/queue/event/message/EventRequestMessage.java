package com.hemajoo.education.wow.queue.event.message;

import com.hemajoo.education.wow.queue.commons.EventType;
import com.hemajoo.education.wow.queue.commons.IMessageType;
import com.hemajoo.education.wow.queue.commons.MessageCategoryType;
import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Schema(description = "Event request message")
public class EventRequestMessage extends BaseMessage
{
    public enum MessageType implements IMessageType
    {
        MESSAGE_EVENT_REGISTRATION_REQUEST(MessageCategoryType.MESSAGE_CATEGORY_EVENT, EventType.class); // PLAYER -> SERVICE AGENT

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

    public EventRequestMessage(SenderIdentity sender, EventType event)
    {
        super(sender,MessageType.MESSAGE_EVENT_REGISTRATION_REQUEST, event);
    }
}

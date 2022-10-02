package com.hemajoo.education.wow.queue.event.message;

import com.hemajoo.education.wow.queue.commons.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Schema(description = "Event request message")
public class EventRequestMessage extends BaseMessage
{
    @Schema(description = "Event type")
    @Getter
    private EventType eventType;

    public enum MessageType implements IMessageType
    {
        MESSAGE_EVENT_REGISTRATION_REQUEST(MessageCategoryType.MESSAGE_CATEGORY_EVENT); // PLAYER -> SERVICE AGENT

        @Getter
        private MessageCategoryType categoryType;

        MessageType(final MessageCategoryType categoryType)
        {
            this.categoryType = categoryType;
        }

//        public static EventNotificationMessage.MessageType from(EventNotificationMessage.MessageType type)
//        {
//            return Arrays.stream(EventNotificationMessage.MessageType.values()).filter(e -> e == type).findFirst().orElse(null);
//        }
    }

    public EventRequestMessage(SenderIdentity senderIdentity, EventType event)
    {
        super(senderIdentity, MessageProtocol.builder()
                .withMessageCategoryType(MessageCategoryType.MESSAGE_CATEGORY_EVENT)
                .withMessageType(EventRequestMessage.MessageType.MESSAGE_EVENT_REGISTRATION_REQUEST)
                .build());

        this.eventType = event;
    }
}

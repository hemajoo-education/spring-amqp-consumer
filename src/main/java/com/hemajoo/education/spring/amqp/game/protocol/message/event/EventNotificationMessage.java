package com.hemajoo.education.spring.amqp.game.protocol.message.event;

import com.hemajoo.education.spring.amqp.core.message.protocol.BaseMessage;
import com.hemajoo.education.spring.amqp.core.message.protocol.IMessageType;
import com.hemajoo.education.spring.amqp.game.protocol.MessageCategoryType;
import com.hemajoo.education.wow.queue.commons.EventType;
import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString(callSuper = true)
@Schema(description = "Event notification message")
public class EventNotificationMessage extends BaseMessage
{
    public enum MessageType implements IMessageType
    {
        // Notify a participant to an event its registration has been accepted.
        MESSAGE_EVENT_NOTIFICATION_REGISTRATION_ACCEPTED(
                MessageCategoryType.MESSAGE_CATEGORY_EVENT_NOTIFICATION,
                EventType.class),

        // Notify a participant to an event its registration has been rejected.
        MESSAGE_EVENT_NOTIFICATION_REGISTRATION_REJECTED(
                MessageCategoryType.MESSAGE_CATEGORY_EVENT_NOTIFICATION,
                EventType.class),

        // Notify to a registered participant the estimated time for the event to start has changed.
        MESSAGE_EVENT_NOTIFICATION_REMAINING_TIME_CHANGED(
                MessageCategoryType.MESSAGE_CATEGORY_EVENT_NOTIFICATION,
                EventType.class),

        // Notify to a registered participant the event setup process has been paused.
        MESSAGE_EVENT_NOTIFICATION_PAUSED(
                MessageCategoryType.MESSAGE_CATEGORY_EVENT_NOTIFICATION,
                EventType.class),

        // Notify to a registered participant the event setup process has been resumed.
        MESSAGE_EVENT_NOTIFICATION_RESUMED(
                MessageCategoryType.MESSAGE_CATEGORY_EVENT_NOTIFICATION,
                EventType.class),

        // Notify to a registered participant an event has been cancelled.
        MESSAGE_EVENT_NOTIFICATION_CANCELLED(
                MessageCategoryType.MESSAGE_CATEGORY_EVENT_NOTIFICATION,
                EventType.class);

        /**
         * Message category.
         */
        @Getter
        private final MessageCategoryType categoryType;

        /**
         * Message content class.
         */
        @Getter
        private final Class<?> contentClass;

        /**
         * Create a new message type.
         * @param categoryType Message category type.
         * @param contentClass Message content class.
         */
        MessageType(final MessageCategoryType categoryType, final Class<?> contentClass)
        {
            this.categoryType = categoryType;
            this.contentClass = contentClass;
        }

        @Override
        public Enum<? extends IMessageType> getProtocolId()
        {
            return this;
        }
    }

    /**
     * Create a new event notification message.
     * @param messageType Message type.
     * @param content Message content.
     * @param sender Sender.
     */
    public EventNotificationMessage(final @NonNull IMessageType messageType, final Object content, final SenderIdentity sender)
    {
        super(messageType, content, sender);
    }
}

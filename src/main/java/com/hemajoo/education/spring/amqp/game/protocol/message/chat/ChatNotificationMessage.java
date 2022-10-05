package com.hemajoo.education.spring.amqp.game.protocol.message.chat;

import com.hemajoo.education.spring.amqp.core.message.protocol.BaseMessage;
import com.hemajoo.education.spring.amqp.core.message.protocol.IMessageType;
import com.hemajoo.education.spring.amqp.game.protocol.MessageCategoryType;
import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString(callSuper = true)
@Schema(description = "Chat notification message")
public class ChatNotificationMessage extends BaseMessage
{
    public enum MessageType implements IMessageType
    {
        // Notify of a chat message.
        MESSAGE_CHAT_NOTIFICATION(
                MessageCategoryType.MESSAGE_CATEGORY_CHAT,
                String.class);
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
     * Create a new <b>chat notification message</b>.
     * @param messageType Message type.
     * @param content Message content.
     * @param sender Sender.
     */
    public ChatNotificationMessage(final @NonNull IMessageType messageType, final Object content, final SenderIdentity sender)
    {
        super(messageType, content, sender);
    }
}

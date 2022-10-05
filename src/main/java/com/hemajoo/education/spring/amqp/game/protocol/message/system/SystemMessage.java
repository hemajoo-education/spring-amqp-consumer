/*
 * (C) Copyright Hemajoo Systems Inc.  2022 - All Rights Reserved
 * -----------------------------------------------------------------------------------------------
 * All information contained herein is, and remains the property of
 * Hemajoo Inc. and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Hemajoo Inc. and its
 * suppliers and may be covered by U.S. and Foreign Patents, patents
 * in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained from
 * Hemajoo Systems Inc.
 * -----------------------------------------------------------------------------------------------
 */
package com.hemajoo.education.spring.amqp.game.protocol.message.system;

import com.hemajoo.education.spring.amqp.core.message.protocol.BaseMessage;
import com.hemajoo.education.spring.amqp.core.message.protocol.IMessageType;
import com.hemajoo.education.spring.amqp.game.protocol.MessageCategoryType;
import com.hemajoo.education.spring.amqp.game.protocol.QueueType;
import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString(callSuper = true)
@Schema(description = "System message")
public class SystemMessage extends BaseMessage
{
    public enum MessageType implements IMessageType
    {
        // Request to pause a consumer listener for a specific queue.
        MESSAGE_SYSTEM_CONSUMER_QUEUE_PAUSE(
                MessageCategoryType.MESSAGE_CATEGORY_SYSTEM,
                QueueType.class),

        // Request to resume a previously paused consumer listener for a specific queue.
        MESSAGE_SYSTEM_CONSUMER_QUEUE_RESUME(
                MessageCategoryType.MESSAGE_CATEGORY_SYSTEM,
                QueueType.class);

        /**
         * Message category.
         */
        @Getter
        private final MessageCategoryType categoryType;

        /**
         * Message content.
         */
        @Getter
        private final Class<?> contentClass;

        /**
         * Create a new message type.
         * @param categoryType Message category type.
         * @param contentClass Message content class.
         */
        MessageType(final MessageCategoryType categoryType, Class<?> contentClass)
        {
            this.categoryType = categoryType;
            this.contentClass = contentClass;
        }

        /**
         * Return the message identifier (useful when used in switch statement).
         * @return Message identifier.
         */
        @Override
        public Enum<? extends IMessageType> getProtocolId()
        {
            return this;
        }
    }

    /**
     * Create a new event request message.
     * @param messageType Message type.
     * @param content Message content.
     * @param sender Sender.
     */
    public SystemMessage(final @NonNull IMessageType messageType, final Object content, final SenderIdentity sender)
    {
        super(messageType, content, sender);
    }
}

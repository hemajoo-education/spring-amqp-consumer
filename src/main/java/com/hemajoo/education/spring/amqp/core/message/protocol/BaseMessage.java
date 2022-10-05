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
package com.hemajoo.education.spring.amqp.core.message.protocol;

import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString
public abstract class BaseMessage implements IMessage
{
    @Getter
    @Schema(description = "Message type")
    private final IMessageType type;

    @Getter
    @Schema(description = "Message content/data")
    private final Object content;

    @Getter
    @Schema(description = "Sender")
    private final SenderIdentity sender;

    public BaseMessage(final @NonNull IMessageType type, final Object content, final SenderIdentity sender)
    {
        this.type = type;
        this.content = content;
        this.sender = sender;
    }
}

package com.hemajoo.education.wow.queue.event.message;

import com.hemajoo.education.wow.queue.commons.MessageProtocol;
import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Schema(description = "BaseMessage")
public class BaseMessage implements Serializable
{
    @Schema(description = "Sender identity")
    @Getter
    private SenderIdentity senderIdentity;

    @Schema(description = "BaseMessage protocol")
    @Getter
    private MessageProtocol messageProtocol;

    public BaseMessage(final @NonNull SenderIdentity senderIdentity, final @NonNull MessageProtocol messageProtocol)
    {
        this.senderIdentity = senderIdentity;
        this.messageProtocol = messageProtocol;
    }
}

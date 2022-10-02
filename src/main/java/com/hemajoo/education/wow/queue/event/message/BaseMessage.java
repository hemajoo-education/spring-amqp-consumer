package com.hemajoo.education.wow.queue.event.message;

import com.hemajoo.education.spring.queue.wow.message.broker.IMessage;
import com.hemajoo.education.wow.queue.commons.IMessageType;
import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString
@Schema(description = "BaseMessage")
public class BaseMessage implements IMessage
{
    @Getter
    private SenderIdentity sender;

    @Getter
    private IMessageType type;

    @Getter
    private Object data;

    public BaseMessage(final @NonNull SenderIdentity sender, final @NonNull IMessageType type, final Object data)
    {
        this.sender = sender;
        this.type = type;
        this.data = data;
    }
}

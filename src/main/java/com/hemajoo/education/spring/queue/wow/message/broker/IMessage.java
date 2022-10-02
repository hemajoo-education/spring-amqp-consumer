package com.hemajoo.education.spring.queue.wow.message.broker;

import com.hemajoo.education.wow.queue.commons.IMessageType;
import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public interface IMessage extends Serializable
{
    @Schema(description = "Message type")
    IMessageType getType();

    @Schema(description = "Message data")
    Object getData();

    @Schema(description = "Message sender")
    SenderIdentity getSender();
}

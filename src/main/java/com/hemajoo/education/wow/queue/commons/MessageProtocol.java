package com.hemajoo.education.wow.queue.commons;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Builder(setterPrefix = "with")
public class MessageProtocol implements Serializable
{
    @Schema(description = "BaseMessage category type")
    @Getter
    private MessageCategoryType messageCategoryType; // MESSAGE_CATEGORY_EVENT

    @Schema(description = "BaseMessage type")
    @Getter
    private IMessageType messageType; // MESSAGE_EVENT_REGISTRATION_REQUEST
}

package com.hemajoo.education.wow.queue.event.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Schema(description = "Event request message")
@Builder(setterPrefix = "with")
public class EventRequestMessage implements Serializable
{
    @Schema(description = "Sender type")
    @Getter
    private String senderType; // PLAYER

    @Schema(description = "Sender identity")
    @Getter
    private String senderIdentity; // 1

    @Schema(description = "Message category type")
    @Getter
    private String messageCategoryType; // MESSAGE_CATEGORY_EVENT

    @Schema(description = "Message type")
    @Getter
    private String messageType; // MESSAGE_EVENT_REGISTRATION_REQUEST

    @Schema(description = "Event type")
    @Getter
    private String eventType; // BATTLEGROUND

    @Schema(description = "Event reference")
    @Getter
    private String eventReference; // Warsong Gulch
}

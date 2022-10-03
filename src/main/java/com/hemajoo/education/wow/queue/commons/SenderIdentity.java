package com.hemajoo.education.wow.queue.commons;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Schema(description = "Message sender identity")
@Builder(setterPrefix = "with")
public class SenderIdentity implements Serializable
{
    @Getter
    @Schema(description = "Sender type")
    private ParticipantType type;

    @Getter
    @Schema(description = "Sender reference")
    private String reference;

    @Getter
    @Schema(description = "Sender topic or routing key")
    private String topicOrRoutingKey;
}

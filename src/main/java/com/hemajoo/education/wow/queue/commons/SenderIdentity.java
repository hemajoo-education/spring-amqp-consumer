package com.hemajoo.education.wow.queue.commons;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Schema(description = "Sender identity")
@Builder(setterPrefix = "with")
public class SenderIdentity implements Serializable
{
    @Schema(description = "Sender type")
    @Getter
    private SenderType type;

    @Schema(description = "Sender identity")
    @Getter
    private Integer id;

}

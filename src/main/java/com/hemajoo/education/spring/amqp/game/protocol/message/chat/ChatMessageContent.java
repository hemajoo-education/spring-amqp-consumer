package com.hemajoo.education.spring.amqp.game.protocol.message.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
@Schema(description = "Chat content message")
public class ChatMessageContent implements Serializable
{
    @JsonProperty("timestamp")
    @Setter
    @Getter
    private String timestamp;

    @JsonProperty("text")
    @Setter
    @Getter
    private String text;

    @JsonProperty("senderName")
    @Setter
    @Getter
    private String senderName;

    @Builder(setterPrefix = "with")
    public ChatMessageContent(final @NonNull String text, final String senderName)
    {
        this.timestamp = LocalDateTime.now().toString();

        this.text = text;
        this.senderName = senderName;
    }
}

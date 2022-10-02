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

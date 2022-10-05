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

import java.io.Serializable;

public interface IMessage extends Serializable
{
    /**
     * Return the type of the message.
     * @return Message type.
     */
    IMessageType getType();

    /**
     * Return the content of the message.
     * @return Message content.
     */
    Object getContent();

    /**
     * Return the sender of the message (if one has been specified).
     * @return Message sender.
     */
    SenderIdentity getSender();
}

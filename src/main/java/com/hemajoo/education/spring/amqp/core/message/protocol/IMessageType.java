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

import com.hemajoo.education.spring.amqp.game.protocol.MessageCategoryType;

import java.io.Serializable;

public interface IMessageType extends Serializable
{
    /**
     * Return the message category type.
     * @return Message category type.
     */
    MessageCategoryType getCategoryType();

    Enum<? extends IMessageType> getProtocolId();

    /**
     * Return the message content class (if one is provided in the message protocol).
     * @return Content class.
     */
    Class<?> getContentClass();
}

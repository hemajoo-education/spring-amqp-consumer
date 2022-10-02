package com.hemajoo.education.wow.queue.commons;

import java.io.Serializable;

public interface IMessageType extends Serializable
{
    MessageCategoryType getCategoryType();

    Class<?> getDataClass();
}

package com.hemajoo.education.wow.queue.util;

import com.hemajoo.education.wow.queue.commons.EventType;
import com.hemajoo.education.wow.queue.commons.IMessageType;
import com.hemajoo.education.wow.queue.commons.MessageProtocol;
import com.hemajoo.education.wow.queue.commons.SenderIdentity;
import com.hemajoo.education.wow.queue.event.message.BaseMessage;
import com.hemajoo.education.wow.queue.event.message.EventNotificationMessage;
import com.hemajoo.utility.random.GeneratorException;
import com.hemajoo.utility.random.RandomEnumGenerator;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageProtocolRandomizer
{
    /**
     * Message category enumeration generator.
     */
    private static final RandomEnumGenerator EventNotificationMessageGenerator = new RandomEnumGenerator(EventNotificationMessage.MessageType.class);


    public BaseMessage randomEventNotificationMessage(SenderIdentity identity) throws GeneratorException
    {
        IMessageType type = (IMessageType) EventNotificationMessageGenerator.generate();

        return new EventNotificationMessage(MessageProtocol.builder()
                .withMessageCategoryType(type.getCategoryType())
                .withMessageType(type)
                .build(),
                identity,
                EventType.CIRCLE_OF_BLOOD);
    }
}

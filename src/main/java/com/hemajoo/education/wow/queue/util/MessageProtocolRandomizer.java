package com.hemajoo.education.wow.queue.util;

import com.hemajoo.education.wow.queue.commons.EventType;
import com.hemajoo.education.wow.queue.commons.IMessageType;
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
    private static final RandomEnumGenerator EventNotificationMessageGenerator = new RandomEnumGenerator(EventNotificationMessage.MessageType.class)
            .exclude(EventNotificationMessage.MessageType.MESSAGE_EVENT_NOTIFICATION_REGISTRATION_ACCEPTED)
            .exclude(EventNotificationMessage.MessageType.MESSAGE_EVENT_NOTIFICATION_REGISTRATION_REJECTED);

    private static final RandomEnumGenerator EventTypeGenerator = new RandomEnumGenerator(EventType.class);


    public BaseMessage randomEventNotificationMessage(SenderIdentity identity) throws GeneratorException
    {
        return new EventNotificationMessage((IMessageType) EventNotificationMessageGenerator.generate(), identity, EventTypeGenerator.generate());
    }
}

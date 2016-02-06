package com.magnetic.samza.idp.task;

import org.apache.samza.system.IncomingMessageEnvelope;
import org.apache.samza.system.OutgoingMessageEnvelope;
import org.apache.samza.system.SystemStream;
import org.apache.samza.task.MessageCollector;
import org.apache.samza.task.StreamTask;
import org.apache.samza.task.TaskCoordinator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.InvalidProtocolBufferException;
import com.magnetic.common.Event;

/**
 * This task reads events from kafka topic called alert, extracts the actors and
 * sends them to a kafka topic called actor-parser
 * 
 * @author aravind
 *
 */
public class ExtractActorsStreamTask implements StreamTask
{
    private static final Logger log = LoggerFactory.getLogger(ExtractActorsStreamTask.class);
    private static final SystemStream OUTPUT_STREAM = new SystemStream("kafka", "actor-feed");

    @Override
    public void process(IncomingMessageEnvelope envelope, MessageCollector collector, TaskCoordinator coordinator)
    {
        log.info("Inside process..");
        Event event;
        try
        {
            byte[] ba = (byte[]) envelope.getMessage();
            event = Event.parseFrom(ba);
            if (event == null)
            {
                log.info("Event was null.");
                return;
            }
        } catch (InvalidProtocolBufferException e)
        {
            e.printStackTrace();
            log.info("Error parsing alert open event");
            return;
        }

        Object message = event.getActor().toByteArray();
        collector.send(new OutgoingMessageEnvelope(OUTPUT_STREAM, message));
    }
}

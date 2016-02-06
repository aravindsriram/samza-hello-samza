package com.magnetic.samza.idp.task;


import org.apache.samza.system.IncomingMessageEnvelope;
import org.apache.samza.task.MessageCollector;
import org.apache.samza.task.StreamTask;
import org.apache.samza.task.TaskCoordinator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.InvalidProtocolBufferException;
import com.googlecode.protobuf.format.JsonFormat;
import com.magnetic.common.Actor;
import com.magnetic.samza.idp.system.RestHelper;

/**
 * This task reads events from kafka topic called alert, extracts the actors and
 * sends them to a kafka topic called actor-parser
 * 
 * @author aravind
 *
 */
public class NotifyIDServiceStreamTask implements StreamTask
{
    private static final Logger log = LoggerFactory.getLogger(NotifyIDServiceStreamTask.class);
    private static String ID_SERVICE_URL = "http://localhost:9080/idp/actors";
    private static JsonFormat jsonFormat = new JsonFormat();

    @Override
    public void process(IncomingMessageEnvelope envelope, MessageCollector collector, TaskCoordinator coordinator)
    {
        log.info("Inside process..");
        Actor actor;
        try
        {
            byte[] ba = (byte[]) envelope.getMessage();
            actor = Actor.parseFrom(ba);
            if (actor == null)
            {
                log.info("Deserialized protobuf object Actor is null!");
                return;
            }
        } catch (InvalidProtocolBufferException e)
        {
            e.printStackTrace();
            log.info("Error deserializing protobuf object Actor!");
            return;
        }
        
        String json = "";
        try
        {
            json = jsonFormat.printToString(actor);
        } catch (Exception e)
        {
            e.printStackTrace();
        } 
        if(json == null || json.length() ==0){
            log.info("Error converting actor object into JSON.");
            return;
        }
        
        RestHelper.doPost(ID_SERVICE_URL, json);
    }
}

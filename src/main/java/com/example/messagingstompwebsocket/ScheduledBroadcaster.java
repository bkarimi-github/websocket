package com.example.messagingstompwebsocket;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.time.LocalTime;

@Component
public class ScheduledBroadcaster {
    private final MessageSendingOperations<String> messageSendingOperations;
    public ScheduledBroadcaster(MessageSendingOperations<String> messageSendingOperations) {
        this.messageSendingOperations = messageSendingOperations;
        System.out.println("Registered Broadcaster");
    }


    //@Scheduled(fixedDelay = 2000)
    public void sendPeriodicMessages() {
        String broadcast = String.format("server periodic message %s via the broker", LocalTime.now());
        this.messageSendingOperations.convertAndSend("/topic/greetings", broadcast);
        System.out.print("Sent message");
    }
}
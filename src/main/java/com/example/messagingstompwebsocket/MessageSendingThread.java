package com.example.messagingstompwebsocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

@Component
public class MessageSendingThread extends Thread{
    @Autowired
    public SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public synchronized void start() {
        System.out.println("Starting a publisher thread");
    }

    @Override
    public void run(){
        System.out.println("Running Publisher Thread");
        for(int i=0;i<100;++i)
        {
            try{
                simpMessagingTemplate.convertAndSend("/topic/greetings", new Greeting("Published Hello " + i +" times !"));
                Thread.sleep(1000);
            }
            catch(Exception e){}

        }
    }
}

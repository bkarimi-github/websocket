package com.example.messagingstompwebsocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;
import java.time.LocalTime;

@EnableScheduling
@Controller
public class GreetingController {


	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Scheduled(fixedRate = 2000)
	public void notifications() {
		String broadcast = String.format("server periodic message %s via the broker", LocalTime.now());
		simpMessagingTemplate.convertAndSend("/topic/greetings", broadcast);
	}

	@MessageMapping("/hello")
	@SendTo(value = {"/topic/greetings"})
	public Greeting greeting(HelloMessage message) throws Exception {
		Thread.sleep(1000); // simulated delay
		return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}


	@MessageMapping("/hello2")
	@SendToUser(value = {"/queue/greetings"})
	public Greeting greeting2(HelloMessage message, Principal user, SimpMessageHeaderAccessor headerAccessor) throws Exception {
		Thread.sleep(500); // simulated delay
		//String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
		//System.out.println(sessionId);
		//headerAccessor.setSessionId(sessionId);

		return new Greeting("You said, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}

}

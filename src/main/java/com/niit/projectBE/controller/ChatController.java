package com.niit.projectBE.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
	private static final Logger Log =  LoggerFactory.getLogger(ChatController.class);
	@RequestMapping("/chat/info")
	@MessageMapping("/chat")
	@SendTo("/topic/message")
	
	public OutPutMessage sendMessage(Message message)
	{
		Log.debug("Calling the method sendMessage()");
		Log.debug("Message Id:" + message.getId());
		Log.debug("Message:"+ message.getMessage());
		return new OutPutMessage(message,new Date());
	}
	

}

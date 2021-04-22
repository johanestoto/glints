package com.glints.toto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.glints.toto.model.ActionMessage;
import com.glints.toto.model.MessageType;
 
/**
 * Class for WebSocket event listener
 * @author Johanes Toto Indarto
 */
@Component
public class WebSocketEventListener {
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

		String username = (String) headerAccessor.getSessionAttributes().get("username");
		if (username != null) {
			ActionMessage chatMessage = new ActionMessage();
			chatMessage.setMessageType(MessageType.LEAVE);
			chatMessage.setName(username);
			messagingTemplate.convertAndSend("/topic/move", chatMessage);
		}
	}
}

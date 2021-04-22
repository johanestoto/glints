package com.glints.toto.model;

 
/**
 * Class for data transfer on realtime message at HUMAN opponent
 * @author Johanes Toto Indarto
 */
public class ActionMessage {
	private String name;
	private String tileId;
	private boolean joined;
	private MessageType messageType;
	
	public String getTileId() {
		return tileId;
	}

	public void setTileId(String tileId) {
		this.tileId = tileId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isJoined() {
		return joined;
	}

	public void setJoined(boolean joined) {
		this.joined = joined;
	}
	
	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
	
}

package com.mallorca.model.incoming;

import com.mallorca.model.UserId;

public class Messaging {
	private UserId sender;
	private UserId recipient;
	private long timestamp;
	private Message message;
	private Postback postback;
	public UserId getSender() {
		return sender;
	}
	public void setSender(UserId sender) {
		this.sender = sender;
	}
	public UserId getRecipient() {
		return recipient;
	}
	public void setRecipient(UserId recipient) {
		this.recipient = recipient;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public Postback getPostback() {
		return postback;
	}
	public void setPostback(Postback postback) {
		this.postback = postback;
	}
	@Override
	public String toString() {
		return "Messaging [sender=" + sender + ", recipient=" + recipient + ", timestamp=" + timestamp + ", message="
				+ message + ", postback=" + postback + "]";
	}
	
}

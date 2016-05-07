package com.mallorca.model.outgoing.generic;

import com.mallorca.model.UserId;

public class MessageRequest {
	private UserId recipient;
	private Message message;
	public UserId getRecipient() {
		return recipient;
	}
	public void setRecipient(UserId recipient) {
		this.recipient = recipient;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "MessageRequest [recipient=" + recipient + ", message=" + message + "]";
	}
	
}

package com.mallorca.model.outgoing;

import com.mallorca.model.UserId;

public class SimpleMessageRequest {
	private UserId recipient;
	private SimpleMessage message;
	public UserId getRecipient() {
		return recipient;
	}
	public void setRecipient(UserId recipient) {
		this.recipient = recipient;
	}
	public SimpleMessage getMessage() {
		return message;
	}
	public void setMessage(SimpleMessage message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "SimpleMessageRequest [recipient=" + recipient + ", message=" + message + "]";
	}
	
}

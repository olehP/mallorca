package com.mallorca.model.outgoing.generic;

public class Message {
	private Attachment attachment;

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	@Override
	public String toString() {
		return "Message [attachment=" + attachment + "]";
	}
	
}

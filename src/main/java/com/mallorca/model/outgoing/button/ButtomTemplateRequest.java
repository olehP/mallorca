package com.mallorca.model.outgoing.button;

import java.util.List;

import com.mallorca.model.UserId;
import com.mallorca.model.outgoing.generic.Button;

public class ButtomTemplateRequest {

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

	public static Builder getBuilder() {
		return new Builder();
	}

	public static class Builder {

		private UserId recipient;
		private List<Button> buttons;
		private String text;
		private Builder() {
		}

		public Builder recipient(UserId recipient) {
			this.recipient =recipient;
			return this;
		}

		public Builder text(String text) {
			this.text = text;
			return this;
		}

		public Builder buttons(List<Button> buttons) {
			this.buttons = buttons;
			return this;
		}

		public ButtomTemplateRequest build() {
			 ButtomTemplateRequest request = new ButtomTemplateRequest();
			 Message message = new Message();
			request.setMessage(message);
			Attachment attachment = new Attachment();
			message.setAttachment(attachment);
			attachment.setType("template");
			Payload payload = new Payload();
			attachment.setPayload(payload);
			payload.setTemplateType("button");
			payload.setButtons(buttons);
			payload.setText(text);
			request.setRecipient(recipient);
			return request;
		}

	}
}

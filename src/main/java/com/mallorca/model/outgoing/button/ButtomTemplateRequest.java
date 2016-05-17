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

		private ButtomTemplateRequest request = new ButtomTemplateRequest();

		private Builder() {
			request.setMessage(new Message());
			request.getMessage().setAttachment(new Attachment());
			request.getMessage().getAttachment().setType("template");
			request.getMessage().getAttachment().setPayload(new Payload());
			request.getMessage().getAttachment().getPayload().setTemplateType("button");
		}

		public Builder recipient(UserId recipient) {
			request.setRecipient(recipient);
			return this;
		}

		public Builder text(String text) {
			request.getMessage().getAttachment().getPayload().setText(text);
			return this;
		}

		public Builder buttons(List<Button> buttons) {
			request.getMessage().getAttachment().getPayload().setButtons(buttons);
			return this;
		}

		public ButtomTemplateRequest build() {
			return request;
		}

	}
}

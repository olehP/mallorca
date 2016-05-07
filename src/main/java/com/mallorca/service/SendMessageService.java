package com.mallorca.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mallorca.model.UserId;
import com.mallorca.model.outgoing.SimpleMessage;
import com.mallorca.model.outgoing.SimpleMessageRequest;
import com.mallorca.model.outgoing.generic.Attachment;
import com.mallorca.model.outgoing.generic.Button;
import com.mallorca.model.outgoing.generic.Message;
import com.mallorca.model.outgoing.generic.MessageElement;
import com.mallorca.model.outgoing.generic.MessageRequest;
import com.mallorca.model.outgoing.generic.Payload;

@Service
public class SendMessageService {
	@Value("${facebook.access.token}")
	private String FACEBOOK_TOKEN;
	@Value("${facebook.messaging.url}")
	private String MESSAGING_URL;
	@Autowired
	private RestTemplate restTemplate;
	
	public void sendSimpleMessage(UserId recipient, String text){
		SimpleMessageRequest request = new SimpleMessageRequest();
		request.setRecipient(recipient);
		SimpleMessage message = new SimpleMessage();
		message.setText(text);
		request.setMessage(message);
		System.out.println(restTemplate.postForObject(MESSAGING_URL, request, String.class));
	}
	
	public void sendGenericMessages(UserId recipient, List<String> photoUrls, String title){
		MessageRequest messageRequest  =new MessageRequest();
		messageRequest.setRecipient(recipient);
		Message message = new Message();
		messageRequest.setMessage(message);
		Attachment attachment = new Attachment();
		message.setAttachment(attachment);
		attachment.setType("template");
		Payload payload = new Payload();
		attachment.setPayload(payload);
		payload.setTemplateType("generic");
		List<MessageElement> elements = new LinkedList<>();
		payload.setElements(elements);
		List<Button> buttons = new LinkedList<>();
		Button addButton = new Button();
		addButton.setPayload("ADD_TO_LIST");
		addButton.setTitle("Add to moments");
		addButton.setType("postback");
		Button doneButton = new Button();
		doneButton.setPayload("ADD_TO_DONE");
		doneButton.setTitle("Already done");
		doneButton.setType("postback");
		buttons.add(addButton);
		buttons.add(doneButton);
		for(String photo:photoUrls){
			MessageElement messageElement = new MessageElement();
			messageElement.setImageUrl(photo);
			messageElement.setTitle(title);
			messageElement.setSubtitle("Some subtitle");
			messageElement.setButtons(buttons);
			elements.add(messageElement);
		}
		restTemplate.postForObject(MESSAGING_URL, messageRequest, String.class);
	}
}
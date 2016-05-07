package com.mallorca.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mallorca.model.incoming.Attachment;
import com.mallorca.model.incoming.MessageRecieved;
import com.mallorca.model.incoming.Messaging;

@Service
public class RecievedMessageService {
	@Autowired
	private SendMessageService sendMessageService;

	public void proccessMessage(MessageRecieved messageRecieved) {
		Messaging messaging = messageRecieved.getEntry().get(0).getMessaging().get(0);
		if (messaging.getMessage().getText() != null) {
			sendMessageService.sendSimpleMessage(messaging.getSender(), messaging.getMessage().getText());
		} else {
			System.out.println("Message is empty");
		}
		if (messaging.getMessage().getAttachments() != null) {
			for (Attachment attachment : messaging.getMessage().getAttachments()) {
				if ("location".equals(attachment.getType())) {
					sendMessageService.sendSimpleMessage(messaging.getSender(),
							attachment.getTitle() + " " + attachment.getPayload());
				}
			}

			sendMessageService.sendGenericMessages(messaging.getSender(),
					Arrays.asList("http://s3.favim.com/orig/46/bucket-list-night-stars-Favim.com-421161.jpg",
							"http://www.tatertwins.com/wp-content/uploads/2012/01/48c51595430a2a658e3c20386f6b1e16.jpg",
							"http://data.whicdn.com/images/19648592/large.jpg",
							"http://s3.favim.com/orig/46/bucket-list-night-stars-Favim.com-421161.jpg",
							"http://www.tatertwins.com/wp-content/uploads/2012/01/48c51595430a2a658e3c20386f6b1e16.jpg",
							"http://data.whicdn.com/images/19648592/large.jpg",
							"http://s3.favim.com/orig/46/bucket-list-night-stars-Favim.com-421161.jpg",
							"http://www.tatertwins.com/wp-content/uploads/2012/01/48c51595430a2a658e3c20386f6b1e16.jpg",
							"http://data.whicdn.com/images/19648592/large.jpg"),
					"Live this moment");
		}
	}
}

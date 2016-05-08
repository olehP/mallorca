package com.mallorca.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mallorca.dao.MomentDAO;
import com.mallorca.dao.UserDAO;
import com.mallorca.dao.UserMomentDAO;
import com.mallorca.entity.Moment;
import com.mallorca.entity.User;
import com.mallorca.entity.UserMoment;
import com.mallorca.entity.UserMomentState;
import com.mallorca.model.UserId;
import com.mallorca.model.incoming.MessageRecieved;
import com.mallorca.model.incoming.Messaging;
import com.mallorca.model.outgoing.button.Attachment;
import com.mallorca.model.outgoing.button.ButtomTemplateRequest;
import com.mallorca.model.outgoing.button.Message;
import com.mallorca.model.outgoing.button.Payload;
import com.mallorca.model.outgoing.generic.Button;
import com.mallorca.util.Constants;

@Service
public class RecievedMessageService {
	@Autowired
	private SendMessageService sendMessageService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private MomentDAO momentDAO;
	@Autowired
	private UserMomentDAO userMomentDAO;
	
	public void proccessMessage(MessageRecieved messageRecieved) {
		Messaging messaging = messageRecieved.getEntry().get(0).getMessaging().get(0);
		if(!userService.createIfNotExist(messaging.getSender())){
			if (messaging.getMessage()!=null &&messaging.getMessage().getText() != null) {
			userService.sendMoments(messaging.getSender(),messaging.getMessage().getText(), 0 );
			return;
			}
			if (messaging.getPostback()!=null&& messaging.getPostback().getPayload()!=null){
				String payload =  messaging.getPostback().getPayload();
				if (payload.startsWith("NEXT")){
					int page = Integer.parseInt(payload.substring(payload.indexOf('_')+1));
					User user = userDAO.findByChatId(messaging.getSender().getId());
					userService.sendMoments(messaging.getSender(), user.getLastQuery(), page);
					return;
				} 
				if (payload.startsWith("TODO")){
					Integer momentId = Integer.parseInt(payload.substring(payload.indexOf('_')+1));
					User user = userDAO.findByChatId(messaging.getSender().getId());
					Moment moment = momentDAO.findOne(momentId);
					moment.setTodoClicked(moment.getTodoClicked()+1);
					momentDAO.save(moment);
					UserMoment userMoment = new UserMoment();
					userMoment.setMoment(moment);
					userMoment.setUser(user);
					userMoment.setState(UserMomentState.TODO);
					userMomentDAO.save(userMoment);
					sendMessageService.sendSimpleMessage(messaging.getSender(), Constants.TODO_ADDED_MESSAGE);
					return;
				}
				
				if (payload.startsWith("DONE")){
					Integer momentId = Integer.parseInt(payload.substring(payload.indexOf('_')+1));
					User user = userDAO.findByChatId(messaging.getSender().getId());
					Moment moment = momentDAO.findOne(momentId);
					moment.setDoneClicked(moment.getDoneClicked()+1);
					UserMoment userMoment = userMomentDAO.findByUserAndMoment(user, moment);
					userMoment.setState(UserMomentState.DONE);
					userMomentDAO.save(userMoment);
					sendMessageService.sendSimpleMessage(messaging.getSender(), Constants.DONE_MESSAGE);
					return;
				}
				if (payload.startsWith("MENU")){
					showMenu(messaging.getSender());
					return;
				}
				
				if (payload.equals("MY_TODO")){
					
				}
				
			}
			
		}
		
	
//		if (messaging.getMessage().getText() != null) {
//			
//			sendMessageService.sendSimpleMessage(messaging.getSender(), messaging.getMessage().getText());
//		} else {
//			System.out.println("Message is empty");
//		}
//		if (messaging.getMessage().getAttachments() != null) {
//			for (Attachment attachment : messaging.getMessage().getAttachments()) {
//				if ("location".equals(attachment.getType())) {
//					sendMessageService.sendSimpleMessage(messaging.getSender(),
//							attachment.getTitle() + " " + attachment.getPayload());
//				}
//			}

		/*	sendMessageService.sendGenericMessages(messaging.getSender(),
					Arrays.asList("http://s3.favim.com/orig/46/bucket-list-night-stars-Favim.com-421161.jpg",
							"http://www.tatertwins.com/wp-content/uploads/2012/01/48c51595430a2a658e3c20386f6b1e16.jpg",
							"http://data.whicdn.com/images/19648592/large.jpg",
							"http://s3.favim.com/orig/46/bucket-list-night-stars-Favim.com-421161.jpg",
							"http://www.tatertwins.com/wp-content/uploads/2012/01/48c51595430a2a658e3c20386f6b1e16.jpg",
							"http://data.whicdn.com/images/19648592/large.jpg",
							"http://s3.favim.com/orig/46/bucket-list-night-stars-Favim.com-421161.jpg",
							"http://www.tatertwins.com/wp-content/uploads/2012/01/48c51595430a2a658e3c20386f6b1e16.jpg",
							"http://data.whicdn.com/images/19648592/large.jpg"),
					"Live this moment"); */
		
	}
	
	public void showMenu(UserId userId){
		ButtomTemplateRequest request = new ButtomTemplateRequest();
		Message message = new Message();
		request.setMessage(message);
		Attachment attachment = new Attachment();
		message.setAttachment(attachment);
		attachment.setType("template");
		Payload payload = new Payload();
		attachment.setPayload(payload);
		payload.setTemplateType("button");
		payload.setText("Menu");
		List<Button> buttons = new LinkedList<>();
		Button todoButton = new Button();
		todoButton.setPayload("MY_TODO");
		todoButton.setTitle("My todo");
		todoButton.setType("postback");
		Button doneButton = new Button();
		doneButton.setPayload("MY_DONE");
		doneButton.setTitle("MY done");
		doneButton.setType("postback");
		buttons.add(todoButton);
		buttons.add(doneButton);
		payload.setButtons(buttons);
	}
	
}

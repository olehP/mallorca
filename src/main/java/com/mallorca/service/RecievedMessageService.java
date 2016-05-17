package com.mallorca.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mallorca.dao.MomentDAO;
import com.mallorca.dao.UserDAO;
import com.mallorca.dao.UserMomentDAO;
import com.mallorca.entity.ChatState;
import com.mallorca.entity.Moment;
import com.mallorca.entity.User;
import com.mallorca.entity.UserMoment;
import com.mallorca.entity.UserMomentState;
import com.mallorca.model.UserId;
import com.mallorca.model.incoming.MessageRecieved;
import com.mallorca.model.incoming.Messaging;
import com.mallorca.model.outgoing.button.ButtomTemplateRequest;
import com.mallorca.model.outgoing.generic.Button;
import com.mallorca.util.Messages;

@Service
public class RecievedMessageService {

	@Autowired
	private SendMessageService sendMessageService;
	@Autowired
	private UserService userService;
	@Autowired
	private MomentsService momentService;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private MomentDAO momentDAO;
	@Autowired
	private UserMomentDAO userMomentDAO;

	public void proccessMessage(MessageRecieved messageRecieved) {
		Messaging messaging = messageRecieved.getEntry().get(0).getMessaging().get(0);
		if (!userService.createIfNotExist(messaging.getSender())) {
			User user = userDAO.findByChatId(messaging.getSender().getId());
			if (messaging.getMessage() != null && messaging.getMessage().getText() != null) {
				if (user.getChatState() == ChatState.MOMENT_TEXT) {
					momentService.addText(messaging.getMessage().getText(), messaging.getSender());
				} else if (user.getChatState() == ChatState.SEARCH) {
					userService.sendMoments(messaging.getSender(), messaging.getMessage().getText(), 0);
				}
				return;
			}
			if (messaging.getPostback() != null && messaging.getPostback().getPayload() != null) {
				userService.setSearchState(user);
				String payload = messaging.getPostback().getPayload();
				if (payload.startsWith("NEXT")) {
					int page = Integer.parseInt(payload.substring(payload.indexOf('_') + 1));
					userService.sendMoments(messaging.getSender(), user.getLastQuery(), page);
					return;
				}
				if (payload.startsWith("TODO")) {
					Integer momentId = Integer.parseInt(payload.substring(payload.indexOf('_') + 1));
					Moment moment = momentDAO.findOne(momentId);
					UserMoment existingUserMoment = userMomentDAO.findByUserAndMoment(user, moment);
					if (existingUserMoment != null && (existingUserMoment.getState() == UserMomentState.DONE || existingUserMoment.getState() == UserMomentState.DONE)) {
						sendMessageService.sendSimpleMessage(messaging.getSender(), Messages.ALREADY_ADDED);
						return;
					}
					moment.setTodoClicked(moment.getTodoClicked() + 1);
					momentDAO.save(moment);

					UserMoment userMoment = new UserMoment();
					userMoment.setMoment(moment);
					userMoment.setUser(user);
					userMoment.setState(UserMomentState.TODO);
					userMomentDAO.save(userMoment);
					sendMessageService.sendSimpleMessage(messaging.getSender(), Messages.TODO_ADDED_MESSAGE);
					return;
				}

				if (payload.startsWith("DONE")) {
					Integer momentId = Integer.parseInt(payload.substring(payload.indexOf('_') + 1));
					Moment moment = momentDAO.findOne(momentId);
					moment.setDoneClicked(moment.getDoneClicked() + 1);
					momentDAO.save(moment);
					UserMoment userMoment = userMomentDAO.findByUserAndMoment(user, moment);
					userMoment.setState(UserMomentState.DONE);
					userMomentDAO.save(userMoment);
					sendMessageService.sendSimpleMessage(messaging.getSender(), Messages.DONE_MESSAGE);
					return;
				}
				if (payload.equals("MENU")) {
					showMenu(messaging.getSender());
					return;
				}

				if (payload.equals("MY_TODO")) {
					momentService.showMyTodo(messaging.getSender());
					return;
				}

				if (payload.equals("MY_DONE")) {
					momentService.showMyDone(messaging.getSender());
					return;
				}

				if (payload.startsWith("DOIT")) {
					Integer momentId = Integer.parseInt(payload.substring(payload.indexOf('_') + 1));
					Moment moment = momentDAO.findOne(momentId);
					moment.setDoitClicked(moment.getDoitClicked() == null ? 1 : moment.getDoitClicked() + 1);
					momentDAO.save(moment);
					momentService.showDoIt(messaging.getSender(), momentId);
					return;
				}
				if (payload.startsWith("BOOK")) {
					Integer momentId = Integer.valueOf(payload.substring(payload.lastIndexOf('_') + 1));
					Moment moment = momentDAO.findOne(momentId);
					String payloadValue = payload.substring(payload.indexOf('_') + 1, payload.lastIndexOf('_'));
					switch (payloadValue) {
					case "ALL":
						moment.setBookAllClicked(moment.getBookAllClicked() == null ? 1 : moment.getBookAllClicked() + 1);
						break;
					case "HOTEL":
						moment.setBookHotelClicked(moment.getBookHotelClicked() == null ? 1 : moment.getBookHotelClicked() + 1);
						break;
					case "ACTIVITY":
						moment.setBookActivityClicked(moment.getBookActivityClicked() == null ? 1 : moment.getBookActivityClicked() + 1);
						break;
					default:
						break;
					}
					momentDAO.save(moment);
					sendMessageService.sendSimpleMessage(messaging.getSender(), "Sorry, booking is not available now, but we are working on it. Thanks for using our bot.");
				}
				return;
			}
			if (messaging.getMessage() != null && messaging.getMessage().getAttachments() != null && messaging.getMessage().getAttachments().get(0).getType().equals("image")) {
				com.mallorca.model.incoming.Payload payloadImage = messaging.getMessage().getAttachments().get(0).getPayload();
				Moment moment = new Moment();
				moment.setImageUrl(payloadImage.getUrl());
				moment.setCreator(user);
				momentDAO.save(moment);
				userService.setUserState(user, ChatState.MOMENT_TEXT);
				sendMessageService.sendSimpleMessage(messaging.getSender(), Messages.SEND_MOMENT_TEXT);
			}

			if (messaging.getMessage() != null && messaging.getMessage().getAttachments() != null && messaging.getMessage().getAttachments().get(0).getType().equals("location")) {
				if (user.getChatState() == ChatState.MOMENT_LOCATION) {
					momentService.setMomentLocation(messaging.getSender(), messaging.getMessage().getAttachments().get(0).getPayload().getCoordinates());
					sendMessageService.sendSimpleMessage(messaging.getSender(), "Thanks for sharing with us");
				}
			}

		}
	}

	public void showMenu(UserId userId) {
		List<Button> buttons = new LinkedList<>();
		Button todoButton = new Button();
		todoButton.setPayload("MY_TODO");
		todoButton.setTitle("My todo");
		todoButton.setType("postback");
		Button doneButton = new Button();
		doneButton.setPayload("MY_DONE");
		doneButton.setTitle("MY done");
		doneButton.setType("postback");
		Button dateButton = new Button();
		dateButton.setPayload("DATE");
		dateButton.setTitle("Personal settings");
		dateButton.setType("postback");
		buttons.add(dateButton);
		buttons.add(todoButton);
		buttons.add(doneButton);
		ButtomTemplateRequest request = ButtomTemplateRequest.getBuilder().recipient(userId).text("Menu").buttons(buttons).build();
		sendMessageService.sendButtonsMessage(request);
	}

}

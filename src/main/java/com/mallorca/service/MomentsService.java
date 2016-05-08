package com.mallorca.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mallorca.dao.MomentDAO;
import com.mallorca.dao.UserDAO;
import com.mallorca.entity.Moment;
import com.mallorca.entity.User;
import com.mallorca.entity.UserMomentState;
import com.mallorca.model.UserId;
import com.mallorca.model.outgoing.generic.Button;
import com.mallorca.model.outgoing.generic.MessageElement;
import com.mallorca.util.Constants;

@Service
public class MomentsService {
	@Autowired
	private MomentDAO momentDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private SendMessageService sendMessageService;
	
	private static final int PER_PAGE = 2;
	private int getOffset(int page){
		if (page == 0){
			return PER_PAGE;
		} 
		return page*PER_PAGE;
	}
	
	public List<MessageElement> getMoments(String query, String userId, Integer page){
		List<Moment> moments;
		User user  = userDAO.findByChatId(userId);
		user.setLastQuery(query);
		userDAO.save(user);
		if (query.trim().isEmpty()){
			moments = momentDAO.searchWithoutQuery(page, getOffset(page),  user.getId());
		} else {
			String[] splited = query.split(" ");
			String first = splited[0];
			String second = "___#";
			if (splited.length > 1){
				second = splited[1];
			}
			moments = momentDAO.searchMoments("%"+first+"%","%"+second+"%", page, getOffset(page), user.getId());
		}
		List<MessageElement> elements = new LinkedList<>();
		for(Moment moment: moments){
			MessageElement messageElement = new MessageElement();
			messageElement.setImageUrl(moment.getImageUrl());
			messageElement.setTitle("Todo: " + moment.getTodoClicked() + " times");
			messageElement.setSubtitle("Done: " + moment.getDoneClicked() + " times");
			elements.add(messageElement);
			List<Button> buttons = new LinkedList<>();
			Button addButton = new Button();
			addButton.setPayload("TODO_"+moment.getId());
			addButton.setTitle("todo");
			addButton.setType("postback");
			Button doneButton = new Button();
			doneButton.setPayload("DONE_"+moment.getId());
			doneButton.setTitle("Done");
			doneButton.setType("postback");
			Button doItButton = new Button();
			doItButton.setPayload("DOIT_"+moment.getId());
			doItButton.setTitle("to it");
			doItButton.setType("postback");
			buttons.add(addButton);
			buttons.add(doneButton);
			messageElement.setButtons(buttons);
		}
		MessageElement messageElement = new MessageElement();
		messageElement.setImageUrl("http://s3.favim.com/orig/46/bucket-list-night-stars-Favim.com-421161.jpg");
		messageElement.setTitle("Live that moment");
		messageElement.setSubtitle("Powered by LNUTeam");
		elements.add(messageElement);
		List<Button> buttons = new LinkedList<>();
		Button addButton = new Button();
		addButton.setPayload("NEXT_"+(++page));
		addButton.setTitle("Next");
		addButton.setType("postback");
		Button doneButton = new Button();
		doneButton.setPayload("MENU");
		doneButton.setTitle("Menu");
		doneButton.setType("postback");
		buttons.add(addButton);
		buttons.add(doneButton);
		messageElement.setButtons(buttons);
		
		return elements;
	}
	
	
	public void showMyTodo(UserId userId){
		User user  = userDAO.findByChatId(userId.getId());
		List<Moment> moments = momentDAO.findByUserAndStatus(user, UserMomentState.TODO);
		if (moments.isEmpty()){
			sendMessageService.sendSimpleMessage(userId, Constants.EMPTY_TODO_LIST);
		}
		List<MessageElement> elements = new LinkedList<>();
		for(Moment moment: moments){
			MessageElement messageElement = new MessageElement();
			messageElement.setImageUrl(moment.getImageUrl());
			messageElement.setTitle("Todo: " + moment.getTodoClicked() + " times");
			messageElement.setSubtitle("Done: " + moment.getDoneClicked() + " times");
			elements.add(messageElement);
			List<Button> buttons = new LinkedList<>();
			Button addButton = new Button();
			addButton.setPayload("DOIT_"+moment.getId());
			addButton.setTitle("to it");
			addButton.setType("postback");
			Button doneButton = new Button();
			doneButton.setPayload("DONE_"+moment.getId());
			doneButton.setTitle("Done");
			doneButton.setType("postback");
			buttons.add(addButton);
			buttons.add(doneButton);
			messageElement.setButtons(buttons);
		}
		MessageElement messageElement = new MessageElement();
		messageElement.setImageUrl("http://s3.favim.com/orig/46/bucket-list-night-stars-Favim.com-421161.jpg");
		messageElement.setTitle("Live that moment");
		messageElement.setSubtitle("Powered by LNUTeam");
		elements.add(messageElement);
		List<Button> buttons = new LinkedList<>();
		Button doneButton = new Button();
		doneButton.setPayload("MENU");
		doneButton.setTitle("Menu");
		doneButton.setType("postback");
		buttons.add(doneButton);
		messageElement.setButtons(buttons);
		
		
	}
	
	public void showMyDone(UserId userId){
		User user  = userDAO.findByChatId(userId.getId());
		List<Moment> moments = momentDAO.findByUserAndStatus(user, UserMomentState.DONE);
		if (moments.isEmpty()){
			sendMessageService.sendSimpleMessage(userId, Constants.EMPTY_TODO_LIST);
		}
		List<MessageElement> elements = new LinkedList<>();
		for(Moment moment: moments){
			MessageElement messageElement = new MessageElement();
			messageElement.setImageUrl(moment.getImageUrl());
			messageElement.setTitle("Todo: " + moment.getTodoClicked() + " times");
			messageElement.setSubtitle("Done: " + moment.getDoneClicked() + " times");
			elements.add(messageElement);
			List<Button> buttons = new LinkedList<>();
			Button addButton = new Button();
			addButton.setPayload("SHAREIT_"+moment.getId());
			addButton.setTitle("share it");
			addButton.setType("postback");
			buttons.add(addButton);
			messageElement.setButtons(buttons);
		}
		MessageElement messageElement = new MessageElement();
		messageElement.setImageUrl("http://s3.favim.com/orig/46/bucket-list-night-stars-Favim.com-421161.jpg");
		messageElement.setTitle("Live that moment");
		messageElement.setSubtitle("Powered by LNUTeam");
		elements.add(messageElement);
		List<Button> buttons = new LinkedList<>();
		Button doneButton = new Button();
		doneButton.setPayload("MENU");
		doneButton.setTitle("Menu");
		doneButton.setType("postback");
		buttons.add(doneButton);
		messageElement.setButtons(buttons);
		
		
	}
}

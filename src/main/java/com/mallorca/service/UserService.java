package com.mallorca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mallorca.dao.UserDAO;
import com.mallorca.entity.User;
import com.mallorca.model.UserId;
import com.mallorca.model.outgoing.generic.MessageElement;
import com.mallorca.util.Constants;

@Service
public class UserService {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private SendMessageService sendMessageService;
	@Autowired
	private MomentsService momentsService;
	public boolean  createIfNotExist(UserId chatId){
		User user = userDAO.findByChatId(chatId.getId());
		if (user == null){
			user = new User();
			user.setChatId(chatId.getId());
			userDAO.save(user);
			sendMessageService.sendSimpleMessage(chatId, Constants.START_MESSAGE);
			List<MessageElement> messageElements = momentsService.getMoments("", chatId.getId(), 0);
			sendMessageService.sendGenericMessages(chatId, messageElements);
			return true;
		}
		return false;
	}
	
	public void sendMoments(UserId chatId, String query, int page){
		query = query.toLowerCase();
		List<MessageElement> messageElements = momentsService.getMoments(query, chatId.getId(), page);
		if (messageElements.size()>1){
		sendMessageService.sendGenericMessages(chatId, messageElements);
		} else {
			sendMessageService.sendSimpleMessage(chatId, Constants.BAD_QUERY_MESSAGE);
		}
	}
}

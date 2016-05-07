package com.mallorca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mallorca.dao.UserDAO;
import com.mallorca.entity.User;

@Service
public class UserService {
	@Autowired
	private UserDAO userDAO;
	
	public void createIfNotExist(String chatId){
		User user = userDAO.findByChatId(chatId);
		if (user == null){
			user = new User();
			user.setChatId(chatId);
			userDAO.save(user);
		}
	}
}

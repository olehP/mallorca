package com.mallorca.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mallorca.entity.User;

public interface UserDAO extends JpaRepository<User, Integer>{
	User findByChatId(String chatId);
}

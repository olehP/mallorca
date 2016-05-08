package com.mallorca.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mallorca.entity.Moment;
import com.mallorca.entity.User;
import com.mallorca.entity.UserMoment;

public interface UserMomentDAO extends JpaRepository<UserMoment, Integer> {
	UserMoment findByUserAndMoment(User user, Moment moment);
}

package com.mallorca.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mallorca.entity.Moment;
import com.mallorca.entity.User;
import com.mallorca.entity.UserMoment;
import com.mallorca.entity.UserMomentState;

public interface UserMomentDAO extends JpaRepository<UserMoment, Integer> {
	UserMoment findByUserAndMoment(User user, Moment moment);
}

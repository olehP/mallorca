package com.mallorca.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mallorca.entity.ReceivedMessage;

public interface ReceivedMessageDAO extends JpaRepository<ReceivedMessage, Long>{

}

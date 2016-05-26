package com.mallorca.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mallorca.converter.LocalDateTimeConverter;

@Entity
@Table(name = "recieved_messages")
public class ReceivedMessage {
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "received_at")
	@Convert(converter=LocalDateTimeConverter.class)
	private LocalDateTime receivedAt;

	@Column(name = "text")
	private String text;
	
	@Column(name = "chat_state")
	@Enumerated(EnumType.STRING)
	private ChatState chatState;
	
	@ManyToOne
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getReceivedAt() {
		return receivedAt;
	}

	public void setReceivedAt(LocalDateTime receivedAt) {
		this.receivedAt = receivedAt;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ChatState getChatState() {
		return chatState;
	}

	public void setChatState(ChatState chatState) {
		this.chatState = chatState;
	}

}

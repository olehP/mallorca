package com.mallorca.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "chat_id")
	private String chatId;

	@Column(name = "password")
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "location_lat")
	private Double loactionLat;

	@Column(name = "location_lng")
	private Double locationLng;

	@Column(name = "location_name")
	private String locationName;

	@Column(name = "last_query")
	private String lastQuery;

	@Column(name = "chat_state")
	@Enumerated(EnumType.STRING)
	private ChatState chatState;

	public String getLastQuery() {
		return lastQuery;
	}

	public void setLastQuery(String lastQuery) {
		this.lastQuery = lastQuery;
	}

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public Double getLoactionLat() {
		return loactionLat;
	}

	public void setLoactionLat(Double loactionLat) {
		this.loactionLat = loactionLat;
	}

	public Double getLocationLng() {
		return locationLng;
	}

	public void setLocationLng(Double locationLng) {
		this.locationLng = locationLng;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public ChatState getChatState() {
		return chatState;
	}

	public void setChatState(ChatState chatState) {
		this.chatState = chatState;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", chatId=" + chatId + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName + ", loactionLat=" + loactionLat + ", locationLng=" + locationLng + ", locationName=" + locationName + ", lastQuery=" + lastQuery + ", chatState=" + chatState + "]";
	}

}
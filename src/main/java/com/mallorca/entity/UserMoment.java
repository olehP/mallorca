package com.mallorca.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mallorca.converter.LocalDateTimeConverter;

@Entity
@Table(name = "user_moment")
public class UserMoment {

	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "moment_id")
	private Moment moment;

	@Column(name = "state")
	@Enumerated(EnumType.STRING)
	private UserMomentState state;

	@Column(name = "modified")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime modified;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Moment getMoment() {
		return moment;
	}

	public void setMoment(Moment moment) {
		this.moment = moment;
	}

	public UserMomentState getState() {
		return state;
	}

	public void setState(UserMomentState state) {
		this.state = state;
	}

	public LocalDateTime getModified() {
		return modified;
	}

	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}

	@Override
	public String toString() {
		return "UserMoment [id=" + id + ", user=" + user + ", moment=" + moment + ", state=" + state + ", modified=" + modified + "]";
	}

}

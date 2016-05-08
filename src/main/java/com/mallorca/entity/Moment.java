package com.mallorca.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "moment")
public class Moment {
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(name = "image_url")
	private String imageUrl;
	
	@Column(name = "text")
	private String text;
	
	@Column(name = "todo_clicked")
	private Integer todoClicked;
	
	@Column(name = "done_clicked")
	private Integer doneClicked;
	
	@Column(name = "location_lng")
	private Double locationLng;
	
	@Column(name = "location_lat")
	private Double locationLat;
	
	@Column(name = "location_name")
	private String locationName;
	
	@Column(name = "activity_code")
	private String activityCode;

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getTodoClicked() {
		return todoClicked;
	}

	public void setTodoClicked(Integer todoClicked) {
		this.todoClicked = todoClicked;
	}

	public Integer getDoneClicked() {
		return doneClicked;
	}

	public void setDoneClicked(Integer doneClicked) {
		this.doneClicked = doneClicked;
	}

	public Double getLocationLng() {
		return locationLng;
	}

	public void setLocationLng(Double locationLng) {
		this.locationLng = locationLng;
	}

	public Double getLocationLat() {
		return locationLat;
	}

	public void setLocationLat(Double locationLat) {
		this.locationLat = locationLat;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	
}

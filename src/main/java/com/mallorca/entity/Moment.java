package com.mallorca.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	private Integer todoClicked = 0;

	@Column(name = "done_clicked")
	private Integer doneClicked = 0;

	@Column(name = "doit_clicked")
	private Integer doitClicked = 0;

	@Column(name = "book_hotel_clicked")
	private Integer bookHotelClicked = 0;

	@Column(name = "book_activity_clicked")
	private Integer bookActivityClicked = 0;

	@Column(name = "book_all_clicked")
	private Integer bookAllClicked = 0;

	@Column(name = "location_lng")
	private Double locationLng;

	@Column(name = "location_lat")
	private Double locationLat;

	@Column(name = "location_name")
	private String locationName;

	@Column(name = "activity_code")
	private String activityCode;

	@ManyToOne
	@JoinColumn(name = "creator_id")
	private User creator;

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

	public Integer getDoitClicked() {
		return doitClicked;
	}

	public void setDoitClicked(Integer doitClicked) {
		this.doitClicked = doitClicked;
	}

	public Integer getBookHotelClicked() {
		return bookHotelClicked;
	}

	public void setBookHotelClicked(Integer bookHotelClicked) {
		this.bookHotelClicked = bookHotelClicked;
	}

	public Integer getBookActivityClicked() {
		return bookActivityClicked;
	}

	public void setBookActivityClicked(Integer bookActivityClicked) {
		this.bookActivityClicked = bookActivityClicked;
	}

	public Integer getBookAllClicked() {
		return bookAllClicked;
	}

	public void setBookAllClicked(Integer bookAllClicked) {
		this.bookAllClicked = bookAllClicked;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	@Override
	public String toString() {
		return "Moment [id=" + id + ", imageUrl=" + imageUrl + ", text=" + text + ", todoClicked=" + todoClicked + ", doneClicked=" + doneClicked + ", doitClicked=" + doitClicked + ", bookHotelClicked=" + bookHotelClicked + ", bookActivityClicked=" + bookActivityClicked + ", bookAllClicked=" + bookAllClicked + ", locationLng=" + locationLng + ", locationLat=" + locationLat + ", locationName="
				+ locationName + ", activityCode=" + activityCode + "]";
	}

}

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
	
	
}

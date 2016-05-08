package com.mallorca.model.outgoing.generic;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageElement {
	private String title;
	@JsonProperty("image_url")
	private String imageUrl;
	private String subtitle;
	private List<Button> buttons;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public List<Button> getButtons() {
		return buttons;
	}

	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
	}

	@Override
	public String toString() {
		return "MessageElement [title=" + title + ", imageUrl=" + imageUrl + ", subtitle=" + subtitle + ", buttons="
				+ buttons + "]";
	}

}

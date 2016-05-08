package com.mallorca.model.outgoing.button;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mallorca.model.outgoing.generic.Button;

public class Payload {
	@JsonProperty("template_type")
	private String templateType;
	private String text;
	private List<Button> buttons;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<Button> getButtons() {
		return buttons;
	}
	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
	}
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	@Override
	public String toString() {
		return "Payload [templateType=" + templateType + ", text=" + text + ", buttons=" + buttons + "]";
	}

	
}

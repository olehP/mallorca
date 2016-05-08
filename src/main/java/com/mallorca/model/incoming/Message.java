package com.mallorca.model.incoming;

import java.util.List;

public class Message {
	private String mid;
	private Integer seq;
	private List<Attachment> attachments;
	private String text;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	@Override
	public String toString() {
		return "Message [mid=" + mid + ", seq=" + seq + ", attachments=" + attachments + ", text=" + text + "]";
	}

	
}

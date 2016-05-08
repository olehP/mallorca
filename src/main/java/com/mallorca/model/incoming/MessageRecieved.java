package com.mallorca.model.incoming;

import java.util.List;

public class MessageRecieved {

	private String object;
	private List<Entry> entry;
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public List<Entry> getEntry() {
		return entry;
	}
	public void setEntry(List<Entry> entry) {
		this.entry = entry;
	}
	@Override
	public String toString() {
		return "MessageRecieved [object=" + object + ", entry=" + entry + "]";
	}
	
}

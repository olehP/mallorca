package com.mallorca.model.incoming;

public class Postback {
	private String payload;

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "Postback [payload=" + payload + "]";
	}
	
}

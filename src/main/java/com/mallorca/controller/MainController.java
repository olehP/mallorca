package com.mallorca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.mallorca.model.incoming.MessageRecieved;
import com.mallorca.service.RecievedMessageService;
import com.mallorca.service.UserService;

@RestController
public class MainController {
	@Autowired
	private RecievedMessageService recievedMessageService;

	@RequestMapping("/")
	private void index(@RequestBody MessageRecieved messageRecieved) {
		try {
			recievedMessageService.proccessMessage(messageRecieved);
		} catch (HttpClientErrorException e) {
			System.out.println(e.getResponseBodyAsString());
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}

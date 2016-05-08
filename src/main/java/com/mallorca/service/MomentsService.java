package com.mallorca.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelbeds.distribution.hotel_api_sdk.types.HotelApiSDKException;
import com.hotelbeds.hotelapimodel.auto.model.Hotel;
import com.hotelbeds.tab.sdk.model.pojo.PurchasableActivity;
import com.mallorca.dao.MomentDAO;
import com.mallorca.dao.UserDAO;
import com.mallorca.dao.UserMomentDAO;
import com.mallorca.entity.Moment;
import com.mallorca.entity.User;
import com.mallorca.entity.UserMoment;
import com.mallorca.entity.UserMomentState;
import com.mallorca.model.UserId;
import com.mallorca.model.outgoing.generic.Button;
import com.mallorca.model.outgoing.generic.MessageElement;
import com.mallorca.util.Constants;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.filters.Caption;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;

@Service
public class MomentsService {
	@Autowired
	private MomentDAO momentDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private UserMomentDAO userMomentDAO;
	@Autowired
	private SendMessageService sendMessageService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private HotelService hotelService;
	@Autowired
	private TransportService transportService;
	
	
	private static final int PER_PAGE = 9;
	private static final LocalDate dateFrom = LocalDate.now().plusDays(5);
	private static final LocalDate dateTo = LocalDate.now().plusDays(7);
	private int getOffset(int page){
		if (page == 0){
			return PER_PAGE;
		} 
		return page*PER_PAGE;
	}
	
	public List<MessageElement> getMoments(String query, String userId, Integer page){
		List<Moment> moments;
		User user  = userDAO.findByChatId(userId);
		user.setLastQuery(query);
		userDAO.save(user);
		if (query.trim().isEmpty()){
			moments = momentDAO.searchWithoutQuery(page, getOffset(page),  user.getId());
		} else {
			String[] splited = query.split(" ");
			String first = splited[0];
			String second = "___#";
			if (splited.length > 1){
				second = splited[1];
			}
			moments = momentDAO.searchMoments("%"+first+"%","%"+second+"%", page, getOffset(page), user.getId());
		}
		List<MessageElement> elements = new LinkedList<>();
		for(Moment moment: moments){
			MessageElement messageElement = new MessageElement();
			messageElement.setImageUrl(moment.getImageUrl());
			messageElement.setTitle("Todo: " + moment.getTodoClicked() + " times");
			messageElement.setSubtitle("Done: " + moment.getDoneClicked() + " times");
			elements.add(messageElement);
			List<Button> buttons = new LinkedList<>();
			Button addButton = new Button();
			addButton.setPayload("TODO_"+moment.getId());
			addButton.setTitle("todo");
			addButton.setType("postback");
			Button doneButton = new Button();
			doneButton.setPayload("DONE_"+moment.getId());
			doneButton.setTitle("Done");
			doneButton.setType("postback");
			Button doItButton = new Button();
			doItButton.setPayload("DOIT_"+moment.getId());
			doItButton.setTitle("do it");
			doItButton.setType("postback");
			buttons.add(addButton);
			buttons.add(doneButton);
			buttons.add(doItButton);
			messageElement.setButtons(buttons);
		}
		MessageElement messageElement = new MessageElement();
		messageElement.setImageUrl("http://cs636630.vk.me/v636630337/7863/veqAlGqktPs.jpg");
		messageElement.setTitle("Live that moment");
		messageElement.setSubtitle("Powered by LNUTeam");
		elements.add(messageElement);
		List<Button> buttons = new LinkedList<>();
		Button addButton = new Button();
		addButton.setPayload("NEXT_"+(++page));
		addButton.setTitle("Next");
		addButton.setType("postback");
		Button doneButton = new Button();
		doneButton.setPayload("MENU");
		doneButton.setTitle("Menu");
		doneButton.setType("postback");
		buttons.add(addButton);
		buttons.add(doneButton);
		messageElement.setButtons(buttons);
		
		return elements;
	}
	
	
	public void showMyTodo(UserId userId){
		User user  = userDAO.findByChatId(userId.getId());
		List<Moment> moments = momentDAO.findByUserAndStatus(user, UserMomentState.TODO);
		if (moments.isEmpty()){
			sendMessageService.sendSimpleMessage(userId, Constants.EMPTY_TODO_LIST);
			return;
		}
		List<MessageElement> elements = new LinkedList<>();
		for(Moment moment: moments){
			MessageElement messageElement = new MessageElement();
			messageElement.setImageUrl(moment.getImageUrl());
			messageElement.setTitle("Todo: " + moment.getTodoClicked() + " times");
			messageElement.setSubtitle("Done: " + moment.getDoneClicked() + " times");
			elements.add(messageElement);
			List<Button> buttons = new LinkedList<>();
			Button addButton = new Button();
			addButton.setPayload("DOIT_"+moment.getId());
			addButton.setTitle("do it");
			addButton.setType("postback");
			Button doneButton = new Button();
			doneButton.setPayload("DONE_"+moment.getId());
			doneButton.setTitle("Done");
			doneButton.setType("postback");
			buttons.add(addButton);
			buttons.add(doneButton);
			messageElement.setButtons(buttons);
		}
		MessageElement messageElement = new MessageElement();
		messageElement.setImageUrl("http://cs636630.vk.me/v636630337/7863/veqAlGqktPs.jpg");
		messageElement.setTitle("Live that moment");
		messageElement.setSubtitle("Powered by LNUTeam");
		elements.add(messageElement);
		List<Button> buttons = new LinkedList<>();
		Button doneButton = new Button();
		doneButton.setPayload("MENU");
		doneButton.setTitle("Menu");
		doneButton.setType("postback");
		buttons.add(doneButton);
		messageElement.setButtons(buttons);
		sendMessageService.sendGenericMessages(userId, elements);
		
	}
	
	public void showMyDone(UserId userId){
		User user  = userDAO.findByChatId(userId.getId());
		List<Moment> moments = momentDAO.findByUserAndStatus(user, UserMomentState.DONE);
		if (moments.isEmpty()){
			sendMessageService.sendSimpleMessage(userId, Constants.EMPTY_DONE_LIST);
			return;
		}
		List<MessageElement> elements = new LinkedList<>();
		for(Moment moment: moments){
			MessageElement messageElement = new MessageElement();
			messageElement.setImageUrl(moment.getImageUrl());
			messageElement.setTitle("Todo: " + moment.getTodoClicked() + " times");
			messageElement.setSubtitle("Done: " + moment.getDoneClicked() + " times");
			elements.add(messageElement);
			List<Button> buttons = new LinkedList<>();
			Button addButton = new Button();
			addButton.setPayload("SHAREIT_"+moment.getId());
			addButton.setTitle("share it");
			addButton.setType("postback");
			buttons.add(addButton);
			messageElement.setButtons(buttons);
		}
		MessageElement messageElement = new MessageElement();
		messageElement.setImageUrl("http://cs636630.vk.me/v636630337/7863/veqAlGqktPs.jpg");
		messageElement.setTitle("Live that moment");
		messageElement.setSubtitle("Powered by LNUTeam");
		elements.add(messageElement);
		List<Button> buttons = new LinkedList<>();
		Button doneButton = new Button();
		doneButton.setPayload("MENU");
		doneButton.setTitle("Menu");
		doneButton.setType("postback");
		buttons.add(doneButton);
		messageElement.setButtons(buttons);
		sendMessageService.sendGenericMessages(userId, elements);
	}
	
	public void showDoIt(UserId userId, Integer momentId){
		User user  = userDAO.findByChatId(userId.getId());
		Moment moment = momentDAO.findOne(momentId);
		List<Double> prices = new ArrayList<>();
		List<MessageElement> elements = new LinkedList<>();
		
		if (moment.getActivityCode()!=null){
		MessageElement activity = getActivity(moment.getActivityCode(), prices);
		elements.add(activity);
		}
		MessageElement hotel = getHotel(moment.getLocationLat().toString(), moment.getLocationLng().toString(), prices);
		MessageElement ride = getTransport(user.getLocationName(), moment.getLocationName(), prices);
		MessageElement messageElement = new MessageElement();
		messageElement.setImageUrl("http://cs636630.vk.me/v636630337/7863/veqAlGqktPs.jpg");// TODO:add logo
		messageElement.setTitle("Book whole trip");
		double total = prices.stream().mapToDouble(price->price).sum();
		messageElement.setSubtitle("Total price: " + total + "€");
		List<Button> buttons = new LinkedList<>();
		Button doneButton = new Button();
		doneButton.setPayload("BOOK");
		doneButton.setTitle("Book all");
		doneButton.setType("postback");
		buttons.add(doneButton);
		messageElement.setButtons(buttons);
		
		elements.add(hotel);
		elements.add(ride);
		elements.add(messageElement);
		sendMessageService.sendGenericMessages(userId, elements);
	}
	
	private MessageElement getActivity(String activityCode,List<Double> prices){
		
		try {
			PurchasableActivity activity= activityService.getActivityByCode(activityCode, transformLocalToDate(dateFrom), transformLocalToDate(dateTo));
		
		
		MessageElement messageElement = new MessageElement();
		messageElement.setImageUrl(activity.getContent().getMedia().getImages().get(0).getUrls().get(0).getResource());
		System.out.println("Activity:" + activity.getContent().getMedia().getImages().get(0).getUrls().get(0).getResource());
		messageElement.setTitle("Book " + activity.getName());
		messageElement.setSubtitle("Price: " + activity.getAmountsFrom().get(0).getAmount().toString() + "€");
		prices.add(activity.getAmountsFrom().get(0).getAmount().doubleValue());
		List<Button> buttons = new LinkedList<>();
		Button doneButton = new Button();
		doneButton.setPayload("BOOK");
		doneButton.setTitle("Book");
		doneButton.setType("postback");
		buttons.add(doneButton);
		messageElement.setButtons(buttons);
		return messageElement;
		} catch (Exception e) {
		throw new RuntimeException(e);
		}
	}
	
	private MessageElement getHotel(String latitude, String longitude,List<Double> prices){
		
		try {
			Hotel 	hotel = hotelService.findCheapesHotelInRadius(dateFrom, dateTo, latitude, longitude);
			prices.add(hotel.getMinRate().doubleValue());
		MessageElement messageElement = new MessageElement();
		messageElement.setImageUrl("http://cdn.precioyviajes.com/00/40/08/29hotelbeds-logo-resized_gp.jpg");
		System.err.println("Hotel: " + hotelService.getHotelImage(hotel.getCode()));
		messageElement.setTitle("Book " + hotel.getName());
		messageElement.setSubtitle("Price: " + (double)((int)(hotel.getMinRate().doubleValue()*100))/100 + "€");
		List<Button> buttons = new LinkedList<>();
		Button doneButton = new Button();
		doneButton.setPayload("BOOK");
		doneButton.setTitle("Book");
		doneButton.setType("postback");
		buttons.add(doneButton);
		messageElement.setButtons(buttons);
		return messageElement;
		} catch (HotelApiSDKException e) {
			throw new RuntimeException(e);
		}
	}

	private MessageElement getTransport(String from, String to,List<Double> prices){
		double price = transportService.getMinPrice(from, to);
		prices.add(price);
		MessageElement messageElement = new MessageElement();
		messageElement.setImageUrl("http://www.rome2rio.com/images/press/Rome2rio_logo_black.png");
		messageElement.setTitle("Book the ride");
		messageElement.setSubtitle("Price: " + (double)((int)(price*100))/100  + "€");
		List<Button> buttons = new LinkedList<>();
		Button doneButton = new Button();
		doneButton.setUrl(transportService.getRome2RioLink(from, to));
		doneButton.setTitle("Go to Rome2Rio");
		doneButton.setType("web_url");
		buttons.add(doneButton);
		messageElement.setButtons(buttons);
		return messageElement;
	}
	
	private Date transformLocalToDate(LocalDate localDate){
		return  Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public void addText(String text, UserId sender) {
		text = text.substring(text.indexOf(":")+1);
		User user  = userDAO.findByChatId(sender.getId());
	
		List<Moment> moments = momentDAO.findByUserAndStatus(user, UserMomentState.DEFINED);
		if (moments!=null&& !moments.isEmpty()){
			Moment moment = moments.get(0);
			UserMoment userMoment = userMomentDAO.findByUserAndMoment(user,moment);
			userMoment.setState(UserMomentState.CREATED);
			userMomentDAO.save(userMoment);
			moment.setText(text);
		//      Set up the caption properties
		     String caption = text;
		     Font font = new Font("Helvetica", Font.BOLD, 200);
		     Color c = Color.WHITE;
		     Position position = Positions.CENTER;
		     int insetPixels = 0;
		     
		     // Apply caption to the image
		     Caption filter = new Caption(caption, font, c, position, insetPixels);
		     String name = UUID.randomUUID().toString()+".jpg";
		     moment.setImageUrl("https://hembara.com/images/"+name);
		     momentDAO.save(moment);
		     try {
				Thumbnails.of(new URL(moment.getImageUrl()).openStream())
				    .size(2560, 1600)
				    .addFilter(filter)
				    .toFile(new File("/home/apache-tomcat-8.0.33/webapps/ROOT/images/"+name));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     
		 
		
		}
		
	}
}

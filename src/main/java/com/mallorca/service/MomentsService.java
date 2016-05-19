package com.mallorca.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.hotelbeds.distribution.hotel_api_sdk.types.HotelApiSDKException;
import com.hotelbeds.hotelapimodel.auto.model.Hotel;
import com.hotelbeds.tab.sdk.model.pojo.PurchasableActivity;
import com.mallorca.dao.MomentDAO;
import com.mallorca.dao.UserDAO;
import com.mallorca.entity.ChatState;
import com.mallorca.entity.Moment;
import com.mallorca.entity.User;
import com.mallorca.entity.UserMomentState;
import com.mallorca.exception.TextOnImageException;
import com.mallorca.model.UserId;
import com.mallorca.model.incoming.Coordinates;
import com.mallorca.model.outgoing.generic.Button;
import com.mallorca.model.outgoing.generic.MessageElement;
import com.mallorca.util.Messages;
import com.mallorca.util.TextOnImageUtil;

@Service
public class MomentsService {

	@Autowired
	private MomentDAO momentDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private SendMessageService sendMessageService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private HotelService hotelService;
	@Autowired
	private TransportService transportService;
	@Autowired
	private UserService userService;

	private static final int PER_PAGE = 9;
	private static final LocalDate dateFrom = LocalDate.now().plusDays(5);
	private static final LocalDate dateTo = LocalDate.now().plusDays(7);

	private static int getOffset(int page) {
		return page * PER_PAGE;
	}

	public List<MessageElement> getMoments(String query, String userId, int page) {
		List<Moment> moments;
		User user = userDAO.findByChatId(userId);
		user.setLastQuery(query);
		userDAO.save(user);
		if (query.trim().isEmpty()) {
			moments = momentDAO.searchWithoutQuery(getOffset(page), PER_PAGE, user.getId());
		} else {
			String[] splited = query.split(" ");
			String first = splited[0];
			String second = "___#";
			if (splited.length > 1) {
				second = splited[1];
			}
			moments = momentDAO.searchMoments("%" + first + "%", "%" + second + "%", getOffset(page), PER_PAGE, user.getId());
		}
		List<MessageElement> elements = new LinkedList<>();
		for (Moment moment : moments) {
			MessageElement messageElement = new MessageElement();
			messageElement.setImageUrl(moment.getImageUrl());
			messageElement.setTitle("Todo: " + moment.getTodoClicked() + " times");
			messageElement.setSubtitle("Done: " + moment.getDoneClicked() + " times");
			elements.add(messageElement);
			List<Button> buttons = new LinkedList<>();
			Button todoButton = new Button();
			todoButton.setPayload("TODO_" + moment.getId());
			todoButton.setTitle("Todo");
			todoButton.setType("postback");
			Button doneButton = new Button();
			doneButton.setPayload("DONE_" + moment.getId());
			doneButton.setTitle("Done");
			doneButton.setType("postback");
			Button doItButton = new Button();
			doItButton.setPayload("DOIT_" + moment.getId());
			doItButton.setTitle("Do it");
			doItButton.setType("postback");
			buttons.add(todoButton);
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
		Button nextButton = new Button();
		nextButton.setPayload("NEXT_" + (++page));
		nextButton.setTitle("Next");
		nextButton.setType("postback");
		Button menuButton = new Button();
		menuButton.setPayload("MENU");
		menuButton.setTitle("Menu");
		menuButton.setType("postback");
		buttons.add(nextButton);
		buttons.add(menuButton);
		messageElement.setButtons(buttons);

		return elements;
	}

	public void showMyTodo(UserId userId, int page) {
		User user = userDAO.findByChatId(userId.getId());
		PageRequest pageRequest = new PageRequest(page, PER_PAGE);
		List<Moment> moments = momentDAO.findByUserAndState(user, UserMomentState.TODO, pageRequest);
		if (moments.isEmpty()) {
			sendMessageService.sendSimpleMessage(userId, Messages.EMPTY_TODO_LIST);
			return;
		}
		List<MessageElement> elements = new LinkedList<>();
		for (Moment moment : moments) {
			MessageElement messageElement = new MessageElement();
			messageElement.setImageUrl(moment.getImageUrl());
			messageElement.setTitle("Todo: " + moment.getTodoClicked() + " times");
			messageElement.setSubtitle("Done: " + moment.getDoneClicked() + " times");
			elements.add(messageElement);
			List<Button> buttons = new LinkedList<>();
			Button doitButton = new Button();
			doitButton.setPayload("DOIT_" + moment.getId());
			doitButton.setTitle("Do it");
			doitButton.setType("postback");
			Button doneButton = new Button();
			doneButton.setPayload("DONE_" + moment.getId());
			doneButton.setTitle("Done");
			doneButton.setType("postback");
			buttons.add(doitButton);
			buttons.add(doneButton);
			messageElement.setButtons(buttons);
		}
		MessageElement messageElement = new MessageElement();
		messageElement.setImageUrl("http://cs636630.vk.me/v636630337/7863/veqAlGqktPs.jpg");
		messageElement.setTitle("Live that moment");
		messageElement.setSubtitle("Powered by LNUTeam");
		elements.add(messageElement);
		List<Button> buttons = new LinkedList<>();
		Button nextButton = new Button();
		nextButton.setPayload("MY_TODO_" + (++page));
		nextButton.setTitle("Next");
		nextButton.setType("postback");
		Button menuButton = new Button();
		menuButton.setPayload("MENU");
		menuButton.setTitle("Menu");
		menuButton.setType("postback");
		buttons.add(nextButton);
		buttons.add(menuButton);
		messageElement.setButtons(buttons);
		sendMessageService.sendGenericMessages(userId, elements);

	}

	public void showMyDone(UserId userId, int page) {
		User user = userDAO.findByChatId(userId.getId());
		PageRequest pageRequest = new PageRequest(page, PER_PAGE);
		List<Moment> moments = momentDAO.findByUserAndState(user, UserMomentState.DONE, pageRequest);
		if (moments.isEmpty()) {
			sendMessageService.sendSimpleMessage(userId, Messages.EMPTY_DONE_LIST);
			return;
		}
		List<MessageElement> elements = new LinkedList<>();
		for (Moment moment : moments) {
			MessageElement messageElement = new MessageElement();
			messageElement.setImageUrl(moment.getImageUrl());
			messageElement.setTitle("Todo: " + moment.getTodoClicked() + " times");
			messageElement.setSubtitle("Done: " + moment.getDoneClicked() + " times");
			elements.add(messageElement);
			List<Button> buttons = new LinkedList<>();
			Button addButton = new Button();
			addButton.setPayload("SHAREIT_" + moment.getId());
			addButton.setTitle("Share it");
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
		if (moments.size() == PER_PAGE && ++page * PER_PAGE == momentDAO.findByUserAndStateCount(user, UserMomentState.TODO)) {
			Button nextButton = new Button();
			nextButton.setPayload("MY_DONE_" + (++page));
			nextButton.setTitle("Next");
			nextButton.setType("postback");
			buttons.add(nextButton);
		}
		buttons.add(doneButton);
		messageElement.setButtons(buttons);
		sendMessageService.sendGenericMessages(userId, elements);
	}

	public void showDoIt(UserId userId, Integer momentId) {
		User user = userDAO.findByChatId(userId.getId());
		Moment moment = momentDAO.findOne(momentId);
		List<Double> prices = new ArrayList<>();
		List<MessageElement> elements = new LinkedList<>();

		if (moment.getActivityCode() != null) {
			MessageElement activity = getActivity(moment.getActivityCode(), prices, momentId);
			if (activity != null) {
				elements.add(activity);

			}
		}
		MessageElement hotel = getHotel(moment.getLocationLat().toString(), moment.getLocationLng().toString(), prices, momentId);
		MessageElement ride = getTransport(user.getLocationName(), moment.getLocationName(), prices);
		MessageElement messageElement = new MessageElement();
		messageElement.setImageUrl("http://cs636630.vk.me/v636630337/7863/veqAlGqktPs.jpg");// TODO:add logo
		messageElement.setTitle("Book whole trip");
		double total = (double) ((int) (prices.stream().mapToDouble(price -> price).sum() * 100)) / 100;
		messageElement.setSubtitle("Total price: " + total + "€");
		List<Button> buttons = new LinkedList<>();
		Button doneButton = new Button();
		doneButton.setPayload("BOOK_ALL_" + momentId);
		doneButton.setTitle("Book all");
		doneButton.setType("postback");
		buttons.add(doneButton);
		messageElement.setButtons(buttons);

		elements.add(hotel);
		elements.add(ride);
		elements.add(messageElement);
		sendMessageService.sendGenericMessages(userId, elements);
	}

	private MessageElement getActivity(String activityCode, List<Double> prices, Integer momentId) {

		try {
			PurchasableActivity activity = activityService.getActivityByCode(activityCode, transformLocalToDate(dateFrom), transformLocalToDate(dateTo));

			MessageElement messageElement = new MessageElement();
			messageElement.setImageUrl(activity.getContent().getMedia().getImages().get(0).getUrls().get(0).getResource());
			System.out.println("Activity:" + activity.getContent().getMedia().getImages().get(0).getUrls().get(0).getResource());
			messageElement.setTitle("Book " + activity.getName());
			messageElement.setSubtitle("Price: " + activity.getAmountsFrom().get(0).getAmount().toString() + "€");
			prices.add(activity.getAmountsFrom().get(0).getAmount().doubleValue());
			List<Button> buttons = new LinkedList<>();
			Button doneButton = new Button();
			doneButton.setPayload("BOOK_ACTIVITY_" + momentId);
			doneButton.setTitle("Book");
			doneButton.setType("postback");
			buttons.add(doneButton);
			messageElement.setButtons(buttons);
			return messageElement;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private MessageElement getHotel(String latitude, String longitude, List<Double> prices, Integer momentId) {
		String hotelName = "";
		String hotelPrice = "";
		try {
			Hotel hotel = hotelService.findCheapesHotelInRadius(dateFrom, dateTo, latitude, longitude);

			double price = (double) ((int) (hotel.getMinRate().doubleValue() * 100)) / 100;
			prices.add(price);
			hotelName = "Book " + hotel.getName();
			hotelPrice = "Price: " + price + "€";
		} catch (HotelApiSDKException e) {
			e.printStackTrace();
			hotelName = "Hotel";
			hotelPrice = "Book the hotel";
		}
		MessageElement messageElement = new MessageElement();
		messageElement.setImageUrl("http://cdn.precioyviajes.com/00/40/08/29hotelbeds-logo-resized_gp.jpg");
		messageElement.setTitle(hotelName);
		messageElement.setSubtitle(hotelPrice);
		List<Button> buttons = new LinkedList<>();
		Button doneButton = new Button();
		doneButton.setPayload("BOOK_HOTEL_" + momentId);
		doneButton.setTitle("Book");
		doneButton.setType("postback");
		buttons.add(doneButton);
		messageElement.setButtons(buttons);
		return messageElement;

	}

	private MessageElement getTransport(String from, String to, List<Double> prices) {
		double price = (double) ((int) (transportService.getMinPrice(from, to) * 100)) / 100;
		prices.add(price);
		MessageElement messageElement = new MessageElement();
		messageElement.setImageUrl("http://www.rome2rio.com/images/press/Rome2rio_logo_black.png");
		messageElement.setTitle("Book the ride");
		messageElement.setSubtitle("Price: " + price + "€");
		List<Button> buttons = new LinkedList<>();
		Button doneButton = new Button();
		doneButton.setUrl(transportService.getRome2RioLink(from, to));
		doneButton.setTitle("Go to Rome2Rio");
		doneButton.setType("web_url");
		buttons.add(doneButton);
		messageElement.setButtons(buttons);
		return messageElement;
	}

	private static Date transformLocalToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public void addText(String text, UserId userId) {
		User user = userDAO.findByChatId(userId.getId());
		Moment moment = momentDAO.findFirstByCreatorOrderByIdDesc(user);
		moment.setText(text);
		String name = UUID.randomUUID().toString() + ".png";
		try {
			TextOnImageUtil.putTextOnImage(text, moment.getImageUrl(), "/home/apache-tomcat-8.0.33/webapps/ROOT/images/" + name);
			moment.setImageUrl("https://hembara.com/images/" + name);
			sendMessageService.sendGenericMessages(userId, Arrays.asList(transformMoment(moment)));
			momentDAO.save(moment);
			userService.setUserState(user, ChatState.MOMENT_LOCATION);
			sendMessageService.sendSimpleMessage(userId, Messages.SEND_MOMENT_LOCATION);
		} catch (TextOnImageException e) {
			e.printStackTrace();
			sendMessageService.sendCancelMessage(userId, e.getMessage());
		}
	}

	private static MessageElement transformMoment(Moment moment) {
		MessageElement messageElement = new MessageElement();
		messageElement.setImageUrl(moment.getImageUrl());
		messageElement.setTitle("Todo: " + moment.getTodoClicked() + " times");
		messageElement.setSubtitle("Done: " + moment.getDoneClicked() + " times");
		List<Button> buttons = new LinkedList<>();
		Button addButton = new Button();
		addButton.setPayload("TODO_" + moment.getId());
		addButton.setTitle("Todo");
		addButton.setType("postback");
		Button doneButton = new Button();
		doneButton.setPayload("DONE_" + moment.getId());
		doneButton.setTitle("Done");
		doneButton.setType("postback");
		Button doItButton = new Button();
		doItButton.setPayload("DOIT_" + moment.getId());
		doItButton.setTitle("do it");
		doItButton.setType("postback");
		buttons.add(addButton);
		buttons.add(doneButton);
		buttons.add(doItButton);
		messageElement.setButtons(buttons);
		return messageElement;
	}

	public void setMomentLocation(UserId userId, Coordinates coordinates) {
		User user = userDAO.findByChatId(userId.getId());
		Moment moment = momentDAO.findFirstByCreatorOrderByIdDesc(user);
		moment.setLocationLat(coordinates.getLatitude());
		moment.setLocationLng(coordinates.getLongitude());
		momentDAO.save(moment);
		userService.setUserState(user, ChatState.SEARCH);
	}
}

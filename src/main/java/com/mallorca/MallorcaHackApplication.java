package com.mallorca;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.hotelbeds.distribution.hotel_api_sdk.types.HotelApiSDKException;
import com.hotelbeds.hotelapimodel.auto.model.Hotel;
import com.hotelbeds.tab.sdk.client.base.exception.HttpClientException;
import com.hotelbeds.tab.sdk.model.pojo.PurchasableActivity;
import com.mallorca.service.ActivityService;
import com.mallorca.service.HotelService;
import com.mallorca.service.TransportService;

@SpringBootApplication
public class MallorcaHackApplication {

	public static void main(String[] args) throws HotelApiSDKException, HttpClientException, IOException {
		//start main application
		try{
		ApplicationContext appContext = SpringApplication.run(MallorcaHackApplication.class, args);
		
		TransportService transportService = appContext.getBean(TransportService.class);
		System.out.println(transportService.getMinPrice("Berlin", "Palma") + " EUR "+transportService.getRome2RioLink("Berlin", "Palma"));
		
		
		//Activity API
		String activityCode = "E-U10-SEAWRLDSDG";
		ActivityService activityService = new ActivityService();
		PurchasableActivity purchasableActivity = activityService.getActivityByCode(activityCode, new Date(), new Date((new Date().getTime()+1000*60*60*24*2)));
	    //example output
		System.out.println(purchasableActivity.getName() + " activity was for "+purchasableActivity.getAmountsFrom().get(0).getAmount()+purchasableActivity.getCurrency() +" found.");
		
		//Hotels API 
		HotelService hotelService = new HotelService();
		//example output
		Hotel hotel = hotelService.findCheapesHotelInRadius(LocalDate.now().plusDays(20) , LocalDate.now().plusDays(30), "39.694967", "3.017201");
		System.out.println(hotel.getMinRate() + hotel.getCurrency() + " " + hotel.getName() +" in "+ hotel.getDestinationName());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
}

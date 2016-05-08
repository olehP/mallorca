package com.mallorca.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.hotelbeds.distribution.hotel_api_sdk.HotelApiClient;
import com.hotelbeds.distribution.hotel_api_sdk.helpers.AvailRoom;
import com.hotelbeds.distribution.hotel_api_sdk.helpers.Availability;
import com.hotelbeds.distribution.hotel_api_sdk.helpers.AvailRoom.AvailRoomBuilder;
import com.hotelbeds.distribution.hotel_api_sdk.helpers.Availability.Circle;
import com.hotelbeds.distribution.hotel_api_sdk.helpers.Availability.DelimitedShape;
import com.hotelbeds.distribution.hotel_api_sdk.types.HotelApiSDKException;
import com.hotelbeds.hotelapimodel.auto.messages.AvailabilityRS;
import com.hotelbeds.hotelapimodel.auto.model.Hotel;

public class HotelService {
	
	public final Long radiusInKilometers = (long) 20;
	public final double minTripAdvisorScore = 4.0;
	
	public Hotel findCheapesHotelInRadius(LocalDate checkIn, LocalDate checkOut,String latitude, String longitude) throws HotelApiSDKException{
	
		Hotel hotel = new Hotel();
		
		HotelApiClient apiClient = new HotelApiClient();		
	    apiClient.setReadTimeout(40000);
	    apiClient.init();
	    AvailRoomBuilder availRoom = AvailRoom.builder().adults(2);
	    
	    DelimitedShape shape = new Circle(latitude,longitude,radiusInKilometers); 
	  
	    AvailabilityRS availabilityRS =
	            apiClient.availability(
	                Availability.builder()
	                .language("ENG")
	                .checkIn(checkIn)
	                .checkOut(checkOut)
	                .addRoom(availRoom)
	                .withinThis(shape)
	                .limitHotelsTo(100)
	                .tripAdvisorScoreHigherThan(new BigDecimal(minTripAdvisorScore))
	                .build());
	    
		System.out.println(availabilityRS.getHotels().getTotal());
	    
	    List<Hotel> hotels= new ArrayList<Hotel>(); 	    
	    hotels = availabilityRS.getHotels().getHotels();    
	    hotels.sort((Hotel one, Hotel two)->{return one.getMinRate().compareTo(two.getMinRate());});
	    hotel = hotels.get(0);
	    if(hotel!=null){
	    	System.out.println("Hotel " + availabilityRS.getHotels().getHotels().get(0).getName() + " was found.");
	    }else{
	    	System.out.println("No hotel was found ");
	    }
	    
		return hotel;
	}

}

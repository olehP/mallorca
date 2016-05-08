package com.mallorca.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.hotelbeds.tab.sdk.client.TabActivitiesClient;
import com.hotelbeds.tab.sdk.client.base.exception.HttpClientException;
import com.hotelbeds.tab.sdk.model.domain.PaxType;
import com.hotelbeds.tab.sdk.model.pojo.PaxDistribution;
import com.hotelbeds.tab.sdk.model.pojo.PurchasableActivity;
import com.hotelbeds.tab.sdk.model.request.ActivityDetailRequest;
import com.hotelbeds.tab.sdk.model.response.ActivityDetailResponse;
import com.hotelbeds.tab.sdk.model.utils.Enum.TABEnvironment;

public class ActivityService {
	
	private final String ActivityAPIKey = "3fmkzjzp47hqrhge8mrgqbbm";
	private final String ActivityAPISecret = "q96UPcPcvp";

	public PurchasableActivity getActivityByCode(String activityCode, Date from, Date to) throws HttpClientException, IOException{
		PurchasableActivity purchasableActivity = new PurchasableActivity();
		TabActivitiesClient client = new TabActivitiesClient(ActivityAPIKey, ActivityAPISecret, TABEnvironment.TABEnvironmentTest);
    	PaxDistribution paxDistribution = new PaxDistribution();
    	paxDistribution.setAge(20);
    	paxDistribution.setType(PaxType.ADULT);
    	ArrayList<PaxDistribution> paxes = new ArrayList<PaxDistribution>();
    	paxes.add(paxDistribution);
    	ActivityDetailRequest request = new ActivityDetailRequest("E-U10-SEAWRLDSDG", from, to, paxes ,"EN");
    	ActivityDetailResponse response = client.retrieveActivityDetails(request, false);
    	System.out.println(response.getActivity().getActivityCode()); 
    	purchasableActivity = response.getActivity();
		return purchasableActivity;
	}
	
}

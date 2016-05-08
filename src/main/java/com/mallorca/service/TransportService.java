package com.mallorca.service;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TransportService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	private final String QUOTES_URL = "https://app.xapix.io/api/v1/thm16_LNUTeam/search_results";
 	
	public double getMinPrice(String from, String to) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("filter[origin]", from);
		params.add("filter[destination]", to);
		
		params.add("fields[search_results]", "destination,origin,elapsedtime,currencycode,language");
		params.add("include", "routes");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(QUOTES_URL).queryParams(params);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.set("Authorization-Token", "HTDpJrQU5wOgtjXGVbBsxmYial1noCfI");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		String decodedURL = builder.toUriString().replaceAll("%5B", "[").replaceAll("%5D", "]");
		try {
		ResponseEntity<String> response = restTemplate.exchange(decodedURL, HttpMethod.GET, entity,
				String.class);
		String responseBody = response.getBody();
		ObjectMapper mapper = new ObjectMapper();
		
			JsonNode node = mapper.readTree(responseBody);
			double minPrice = node.get("routes").elements().next().get("indicativePrices").elements().next().get("priceLow").asDouble();
			return minPrice;
		
		} catch (Exception e){
			e.printStackTrace();
		}
		Random random = new Random();
		double randomResult = random.doubles(70, 120).findFirst().getAsDouble();
		System.out.println(randomResult + " is a random result since main feature is not working.");
		return randomResult;
		
	}
	
	public String getRome2RioLink(String from, String to){
	
		String baseUrl = "http://www.rome2rio.com/s/";
		String url = baseUrl+from+"/"+to;
		return url;
		
		
	}
	
	 

}

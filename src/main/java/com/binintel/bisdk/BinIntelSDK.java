package com.binintel.bisdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BinIntelSDK {
	
	private String apiKey;
	private OkHttpClient client = new OkHttpClient();
	private final String base_url = "https://dmc9l6wlyb.execute-api.us-east-1.amazonaws.com/beta/home";
	private final String err400 = "'status': 400, 'message': 'Bad Request. You must provide valid request params.'";
	
	public BinIntelSDK(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public String getCardInfo(String card_number) {
		int card_number_int = 0;
		try {
			card_number_int = Integer.parseInt(card_number);
		} catch (NumberFormatException ex) { }
		
		if (card_number_int == 0)
			return err400;
	    
	    return this.getOneInfo(card_number_int);
	}
	
	public String getBulkCardsInfo(int[] cardsList) {
		Integer[] cardsList_ = Arrays.stream( cardsList ).boxed().toArray( Integer[]::new );
		return getBulkCardsInfo(cardsList_);
	}
	
	public String getBulkCardsInfo(Integer[] cardsList) {
		int[] cardsArray = Ints.toArray(Sets.newHashSet(cardsList));
		return getBulkInfo(cardsArray);
	}
	
	public String getBulkCardsInfo(String[] cardsList) {
		String[] cardsArray = Sets.newHashSet(cardsList).toArray(new String[0]);
		return getBulkInfo(cardsArray);
	}
	
	public String getBulkCardsInfo(ArrayList<String> cardsList) {
		String[] cardsArray = Sets.newHashSet(cardsList).toArray(new String[0]);
		return getBulkInfo(cardsArray);
	}
	
	private String getBulkInfo(int[] cardsList) {
		String cards = "";
		for (int card : cardsList) {
			if (card <= 0) continue; 
			if (cards.isEmpty()) cards = "cards=" + card;
			else cards += "&cards=" + card;
		}
		return getData(base_url + "/?" + cards);
	}
	
	private String getBulkInfo(String[] cardsList) {
		String cards = "";
		for (String card : cardsList) {
			int conv_card = 0;
			try {
				conv_card = Integer.parseInt(card);
			} catch (NumberFormatException ex) {}
			if (conv_card <= 0) continue; 
			if (cards.isEmpty()) cards = "cards=" + card;
			else cards += "&cards=" + card;
		}
		return getData(base_url + "/?" + cards);
	}
	
	public String getOneInfo(int card_number) {
		return getData(base_url + "/?card=" + card_number);
	}
	
	public String getData(String api_url) {
		String res_format = "{\"status\": 200, \"data\": %s}";
		Map<String, String> header = new HashMap<String, String>();
		header.put("Content-Type", "application/json");
		header.put("apikey", this.apiKey);
		
		System.out.println(api_url);
		
		Request request = new Request.Builder().url(api_url).headers(Headers.of(header)).build();
		try (Response response = client.newCall(request).execute()) {
	    	return String.format(res_format, response.body().string());
	    } catch (IOException ex) {
	    	return ex.getMessage();
	    }
	}
	
	
	
	
}
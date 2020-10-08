package com.binintel.bisdk;

import java.util.ArrayList;

import com.google.common.collect.Lists;

public class BinIntelTest {
	
	public static void main(String[] args) {
		BinIntelSDK bis = new BinIntelSDK("187ff000bdad40bb883a37050e72c491");
		String res = bis.getOneInfo(47346834);
		System.out.println(res);
		
		int[] cardsArr1 = new int[] {47346833, 47346834, 47346835, 47346833, 47346833};
		Integer[] cardsArr2 = new Integer[] {47346833, 47346834, 47346835, 47346833, 47346833};
		String[] cardsArr3 = new String[] {"47346833", "47346834", "-47346835", "47346833", "47346833"};
		ArrayList<String> cardsArr4 = Lists.newArrayList(cardsArr3);
		
		System.out.println(bis.getBulkCardsInfo(cardsArr1));
		System.out.println(bis.getBulkCardsInfo(cardsArr2));
		System.out.println(bis.getBulkCardsInfo(cardsArr3));
		System.out.println(bis.getBulkCardsInfo(cardsArr4));
		 
	}
}

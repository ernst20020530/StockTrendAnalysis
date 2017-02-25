package org.bwang;


import java.io.IOException;
import java.util.*;

import yahoofinance.*;

public class StockTrendAnalysis {
	
	private StockTrendAnalysis(){}
	
	public Boolean initialize(){
		try {
			stocks = YahooFinance.get(stockSymbolList);
			
	
			
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	static StockTrendAnalysis GetSingleton(){
		if(singleton == null)
			singleton = new StockTrendAnalysis();
		
		return singleton;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	static private StockTrendAnalysis singleton;
	
	static private final String[] stockSymbolList = new String[]{"XLE", "XLF"};
	
	private Map<String, Stock> stocks;
}

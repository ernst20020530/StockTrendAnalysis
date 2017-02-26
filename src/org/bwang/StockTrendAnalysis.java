package org.bwang;


import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.math.*;

import yahoofinance.*;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import org.apache.commons.math3.stat.descriptive.moment.*;

import one.util.streamex.StreamEx;

public class StockTrendAnalysis {
	
	private StockTrendAnalysis(){}
	
	public Boolean initialize(){
		try {

			m_stockDataList = YahooFinance.get(stockSymbolList).entrySet().stream().map(x->x.getValue()).map(x->new StockData(x.getSymbol(),x)).collect(Collectors.toList());
			
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	public void process(){
		Calendar from = Calendar.getInstance();
		from.set(2016, 12, 1);
		Calendar to = Calendar.getInstance();
		to.set(2017, 1, 31);
		m_stockDataList.stream().forEach(x->System.out.println(x.GetSymbol() + " " + x.GetAverage(from, to, Interval.DAILY, HistoricalQuote::getClose).getAsDouble()));
		
		m_stockDataList.stream().forEach(x->{
			List<Double> ds = null;
			try {
				ds = x.GetData(from, to, Interval.DAILY, HistoricalQuote::getClose);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			List<Double> closeDiff = StreamEx.of(ds).pairMap((a,b)->(Math.log(b)-Math.log(a))).collect(Collectors.toList());
			
		});
		
		
		
//		m_stockDataStream.filter(x->x.GetSymbol().compareTo("F") == 0).forEach(x->{
//			try {
//				x.GetData(from, to, HistoricalQuote::getClose).forEach(y->System.out.println(y));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
	}
	
	
	static StockTrendAnalysis GetSingleton(){
		if(singleton == null)
			singleton = new StockTrendAnalysis();
		
		return singleton;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StockTrendAnalysis sta = StockTrendAnalysis.GetSingleton();
		if(!sta.initialize())
			return;
		
		sta.process();
	}
	
	static private StockTrendAnalysis singleton;
	
	static private final String[] stockSymbolList = new String[]{"XLF", "XLE"};
	
	private List<StockData> m_stockDataList;
}

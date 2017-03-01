package org.bwang;


import java.io.IOException;
import java.util.*;
import java.util.stream.*;
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
		from.set(2017, 1, 1);
		Calendar to = Calendar.getInstance();
		to.set(2017, 2, 1);
		//m_stockDataList.stream().forEach(x->System.out.println(x.GetSymbol() + " " + x.GetAverage(from, to, Interval.DAILY, HistoricalQuote::getClose).getAsDouble()));
		
		m_stockDataList.stream().forEach(x->{
			List<Double> ds = null;
			try {
				ds = x.GetData(from, to, Interval.DAILY, HistoricalQuote::getClose);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//List<Double> closeDiff = StreamEx.of(ds).pairMap((a,b)->(Math.log(b)-Math.log(a))).collect(Collectors.toList());
			List<Double> closeDiff = IntStream.range(0, 20).mapToDouble(y->y).boxed().collect(Collectors.toList());
			List<List<?>> res = Algorithm.iterateAndSubCenter(closeDiff, 5, 5);

			res.forEach(z->{
				z.forEach(y->System.out.print(y + "\t"));
				System.out.println("");
			});
//			IntStream.range(0, closeDiff.size()).forEach(y->{
//				long count = closeDiff.stream().count();
//				
//				///create a empty vector, whose length is equal to the length of close diff 
//				List<Double> v = Stream.generate(()->new Double(0)).limit(count).mapToDouble(z->z).boxed().collect(Collectors.toList());
//				closeDiff.stream().skip(y - 5 >=0 ? y - 5 : 0).limit(y - 5 >=0 ? 11 : 11 + y - 5).forEach(z->System.out.print(y + " "+ z + "\t\t"));
//				System.out.println("\n");
//			});
			
//			IntStream.range(0, closeDiff.size()).forEach(i -> System.out.println(i + "\t" + closeDiff.get(i)));

		});
		
		
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
	
	static private final String[] stockSymbolList = new String[]{"MSFT"};
	
	private List<StockData> m_stockDataList;
}

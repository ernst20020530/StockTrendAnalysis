package org.bwang;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.OptionalDouble;
import java.util.function.*;
import java.util.stream.*;
import one.util.streamex.StreamEx;

import yahoofinance.Stock;
import yahoofinance.histquotes.*;

public class StockData {
	public StockData(String symbol, Stock s)
	{
		m_symbol = symbol;
		m_stock = s;
	}
	
	/**
	 * 	Get the average of one of property of HistoricalQuote
	 * the date shall be [From, to)
	 * param	Calendar from
	 * param	Calendar to
	 * param	Interval interval		DAILY, MONTHLY, WEEKLY
	 * param	Function<HistoricalQuote, BigDecimal> getProperty	get function for close open high low price
	 * */
	
	OptionalDouble GetAverage(Calendar from, Calendar to, Interval interval, Function<HistoricalQuote, BigDecimal> getProperty){
		try {
			return GetData(from, to, interval, getProperty).stream().mapToDouble(Double::doubleValue).average();
		} catch (IOException e) {
			e.printStackTrace();
			return OptionalDouble.empty();
		}
	}

	/**
	 * 	Get the list of property data from HistoricalQuote
	 * the date shall be [From, to)
	 * param	Calendar from
	 * param	Calendar to
	 * param	Interval interval		DAILY, MONTHLY, WEEKLY
	 * param	Function<HistoricalQuote, BigDecimal> getProperty	get function for close open high low price
	 * */
	
	List<Double> GetData(Calendar from, Calendar to, Interval interval, Function<HistoricalQuote, BigDecimal> getProperty) throws IOException{
		to.add(Calendar.DATE, -1);		///move to date backward
		return m_stock.getHistory(from, to, interval).stream().map(getProperty).mapToDouble(x->x.doubleValue()).boxed().collect(Collectors.toList());
	}

	/**
	 * 	Get the list of difference of property data from HistoricalQuote
	 * the date shall be [From, to)
	 * param	Calendar from
	 * param	Calendar to
	 * param	Interval interval		DAILY, MONTHLY, WEEKLY
	 * param	Function<HistoricalQuote, BigDecimal> getProperty	get function for close open high low price
	 * */
	
	List<Double> GetDataDiff(Calendar from, Calendar to, Interval interval, Function<HistoricalQuote, BigDecimal> getProperty) throws IOException{
		return StreamEx.of(GetData(from, to, interval, getProperty)).pairMap((a,b)->(Math.log(b)-Math.log(a))).collect(Collectors.toList());
	}
	
	String GetSymbol(){
		return m_symbol;
	}
	
	private String m_symbol;
	private Stock m_stock;
}

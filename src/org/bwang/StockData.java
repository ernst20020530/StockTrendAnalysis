package org.bwang;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.OptionalDouble;
import java.util.function.*;

import yahoofinance.Stock;
import yahoofinance.histquotes.*;

public class StockData {
	public StockData(String symbol, Stock s)
	{
		m_symbol = symbol;
		m_stock = s;
	}
	
	OptionalDouble GetAverage(Calendar from, Calendar to, Function<HistoricalQuote, BigDecimal> getProperty) throws IOException{
		return m_stock.getHistory(from, to).stream().map(getProperty).mapToDouble(x->x.doubleValue()).average();
	}
	
	private String m_symbol;
	private Stock m_stock;
}

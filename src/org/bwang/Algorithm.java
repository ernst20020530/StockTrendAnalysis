package org.bwang;

import java.util.stream.*;
import java.util.*;

public class Algorithm {
	
	static public List<List<?>> iterateAndSubCenter(List<?> data, long left, long right){
		return IntStream.range(0, data.size()).boxed().map(y->data.stream().
				skip(y - left >=0 ? y - left : 0).
				limit(y - left >=0 ? (left + 1 + right) : left + 1 + right + y - left).collect(Collectors.toList())
			).collect(Collectors.toList());
	}
	
}

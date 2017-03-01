package org.bwang;

import java.util.stream.*;
import java.util.*;
import java.util.function.IntToDoubleFunction;
import java.util.function.Supplier;

public class Algorithm {
	
	static public List<List<Double>> iterateAndSubCenter(List<Double> data, long left, long right){
		return IntStream.range(0, data.size()).boxed().map(y->data.stream().
				skip(y - left >=0 ? y - left : 0).
				limit(y - left >=0 ? (left + 1 + right) : left + 1 + right + y - left).collect(Collectors.toList())
			).collect(Collectors.toList());
	}
	
	static public List<List<?>> CreateVectors(List<List<Double>> subCenterList, Supplier<Double> s, long left, long right, int vectorSize){
		List<List<?>> res = new ArrayList<List<?>>();
		if(left == 0 || right == 0 || vectorSize == 0)	return res;

		
		List<Double> leftRatios = IntStream.range(0, (int)left).mapToDouble(Double::new).map(y->y/(double)left).boxed().collect(Collectors.toList());
		leftRatios.set(0, (leftRatios.get(0) + leftRatios.get(1)) / 2);
		List<Double> rightRatiostmp = IntStream.range(0, (int)right).mapToDouble(Double::new).map(y->y/(double)right).boxed().collect(Collectors.toList());
		rightRatiostmp.set(0, (rightRatiostmp.get(0) + rightRatiostmp.get(1)) / 2);
		
		int lastIdx = rightRatiostmp.size() - 1;
		List<Double> rightRatios = IntStream.rangeClosed(0, lastIdx).mapToObj(i->rightRatiostmp.get(lastIdx - i)).collect(Collectors.toList());
		leftRatios.add(new Double(1));
		List<Double> ratios = Stream.concat(leftRatios.stream(), rightRatios.stream()).collect(Collectors.toList());
		
		
		for(int i = 0; i < subCenterList.size(); ++i){
			List<Double> data = subCenterList.get(i);
			List<Double> v = Stream.generate(s).limit(vectorSize).collect(Collectors.toList());
			for(int j = (int) (i - left), k = 0, index = 0; j <= i + right; ++j, ++k){
				if(j < 0 || index >= data.size() || k >= ratios.size())	continue;
				v.set(j, (double)data.get(index++) * ratios.get(k));
			}
			
			res.add(v);
		}
		
		return res;
	}
	
}

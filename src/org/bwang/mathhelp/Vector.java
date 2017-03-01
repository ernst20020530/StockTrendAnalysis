package org.bwang.mathhelp;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class Vector {
	public Vector(double[] data){
		this.data = data;
		double m = 0;
		for(int i = 0; i < data.length; m += Math.pow(data[i++], 2));
		magnitude = Math.sqrt(m);
	}
	
	public int getDimensional(){
		return data.length;
	}
	
	public double getMagnitude(){
		return magnitude;
	}
	
	public double getValue(int index){
		if(index < 0 || index > getDimensional())
			throw new IllegalArgumentException();
		return data[index];
	}
	
	static public double calculateDotProduct(Vector v1, Vector v2) throws Exception{
		if(v1.getDimensional() != v2.getDimensional())
			throw new Exception();
		
		double dotProduct =0;
		for(int i = 0; i < v1.getDimensional(); ++i){
			dotProduct += v1.getValue(i) * v2.getValue(i);
		}
		return dotProduct;
	}
	
	static public double angle(Vector v1, Vector v2) throws Exception{
		double dotProduct = calculateDotProduct(v1, v2);
		return dotProduct / (v1.getMagnitude() * v2.getMagnitude());
	}
	
	public static void main(String[] args) {
		double d1[] = {0 , 5};
		double d2[] = {5 , 5};
		Vector v1 = new Vector(d1);
		Vector v2 = new Vector(d2);
		double angle = 0;
		try {
			angle = Vector.angle(v1, v2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(angle);
	}
	
	private final double[] data;
	private final double magnitude;
}

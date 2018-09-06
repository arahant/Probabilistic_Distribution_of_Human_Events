package com.sk.program;

public class Action {
	
	/*
	 * The Action natures are Benevolence (Blue), Indifference (Green) and Malevolence (Red)
	 * */
	private String nature;
	
	private double probabilityBlue; 	//for Benevolence 
	public static int action_count_B = 0;
	
	private double probabilityGreen; 	//for Indifference
	public static int action_count_G = 0;
	
	private double probabilityRed; 		//for Malevolence
	public static int action_count_R = 0;
	public static int action_count = 0;
	
	public String getNature() {
		return nature;
	}
	
	public double getProbability(int n) {
		if(n==1)
			return probabilityBlue;
		else if(n==2)
			return probabilityGreen;
		else
			return probabilityRed;
	}
	
	public double commitAction(int n) {
		double probability = 0d;
		if(n==1) {
			probabilityBlue = ++action_count_B/++action_count;
			probability = probabilityBlue;
		}
		else if(n==2) {
			probabilityGreen = ++action_count_G/++action_count;
			probability = probabilityGreen;
		}
		else {
			probabilityRed = ++action_count_R/++action_count;
			probability = probabilityRed;
		}
		return probability;
	}
	
	public void setNature(String nature) {
		this.nature = nature;
	}

}

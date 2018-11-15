package com.sk.program;

public class Action {

	private Nature nature;
	
	private double probabilityBlue; 	//for Benevolence
	private static int action_count_B = 0;

	private double probabilityGreen; 	//for Indifference
	private static int action_count_G = 0;

	private double probabilityRed; 		//for Malevolence
	private static int action_count_R = 0;
	private static int action_count = 0;

	public void setNature(Nature n) {
		this.nature = n;
	}
	public Nature getNature() {
		return this.nature;
	}
	
	public double getProbability(String type) {
		switch(type) {
			case Nature.BENEVOLENT:
				return probabilityBlue;
			case Nature.INDIFFERENT:
				return probabilityGreen;
			case Nature.MALEVOLENT:
				return probabilityRed;
		}
		return 0;
	}

	public double alterProbability() {
		String type = this.getNature().getNatureType();
		double probability = 0d;
		if(type.equals(Nature.BENEVOLENT)) {
			probabilityBlue = ++action_count_B/++action_count;
			probability = probabilityBlue;
		}
		else if(type.equals(Nature.INDIFFERENT)) {
			probabilityGreen = ++action_count_G/++action_count;
			probability = probabilityGreen;
		}
		else {
			probabilityRed = ++action_count_R/++action_count;
			probability = probabilityRed;
		}
		return probability;
	}

}

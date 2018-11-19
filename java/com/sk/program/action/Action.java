package com.sk.program.action;

import com.sk.program.nature.Nature;
import com.sk.store.Database;

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

	public void updateProbability() {
		String type = this.getNature().getNatureType();
		//double probability = 0d;
		switch(type) {
		case Nature.BENEVOLENT:
			probabilityBlue = ++action_count_B/++action_count;
			//probability = probabilityBlue;
			break;
		case Nature.INDIFFERENT:
			probabilityGreen = ++action_count_G/++action_count;
			//probability = probabilityGreen;
			break;
		case Nature.MALEVOLENT:
			probabilityRed = ++action_count_R/++action_count;
			//probability = probabilityRed;
			break;
		}
		//return probability;
	}
	
	public void updateActionSet() {
		Database.updateDb(this);
	}

}

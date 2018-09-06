package com.sk.program;

import java.util.List;

public class Human {
	
	//private static enum Base_Personality {Benevolent, Indifferent, Malevolent};
	//private static enum Base_Feature {Morality, Integrity, Survivalism, Domineering, Selfishness}
	
	/*
	 * The Base personalities are Benevolence (Blue), Indifference (Green) and Malevolence (Red)
	 * */
	
	public static int LIMIT = 255;
	private String personality;
	
	public String getPersonality() {
		return personality;
	}
	
	public void setPersonality(String personality) {
		this.personality = personality;
	}
	
	public void commitAction(Human actor, List<Human> targets, List<Action> actions) {
		for(int i=0;i<targets.size();i++) {
			Human target = targets.get(i);
			Action action = actions.get(i);
			target.influencePersonality(action);
			actor.influencePersonality(action);
		}
	}
	
	private void influencePersonality(Action action) {
		String[] personality_types = this.personality.split("-");
		float BH = Float.parseFloat(personality_types[0]);
		float GH = Float.parseFloat(personality_types[1]);
		float RH = Float.parseFloat(personality_types[2]);
		
		String[] action_nature = action.getNature().split("-");
		int BA = Integer.parseInt(action_nature[0]);
		int GA = Integer.parseInt(action_nature[1]);
		int RA = Integer.parseInt(action_nature[2]);
		
		float dB = (float)BA/LIMIT;
		float dG = (float)GA/LIMIT;
		float dR = (float)RA/LIMIT;
		
		BH+=dB; GH+=dG; RH+=dR;
		String new_peronality = BH+"-"+GH+"-"+RH;
		this.setPersonality(new_peronality);
	}

}

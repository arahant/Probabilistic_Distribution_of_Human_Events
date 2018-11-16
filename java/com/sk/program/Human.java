package com.sk.program;

import java.util.List;

public class Human {

	private Nature personality;

	public Nature getPersonality() {
		return personality;
	}

	public void setPersonality(Nature p) {
		this.personality = p;
	}

	// An action influences both the actor and target(s) and changes the probability of the nature of the action
	public void commitAction(Human actor, List<Human> targets, List<Action> actions) {
		for(int i=0;i<targets.size();i++) {
			Human target = targets.get(i);
			Action action = actions.get(i);
			target.influenceNature(action,false);
			actor.influenceNature(action,true);
			action.updateProbability();
		}
	}

	private void influenceNature(Action action, boolean actor) {
		String[] human_nature = this.personality.getNature().split("-");
		float AH = Float.parseFloat(human_nature[0]);
		float BH = Float.parseFloat(human_nature[1]);
		float CH = Float.parseFloat(human_nature[2]);
		float DH = Float.parseFloat(human_nature[3]);

		// the change in the nature influenced by the action
		String[] action_nature = action.getNature().getNature().split("-");
		float dA = Float.parseFloat(action_nature[0]);
		float dB = Float.parseFloat(action_nature[1]);
		float dC = Float.parseFloat(action_nature[2]);
		float dD = Float.parseFloat(action_nature[3]);

		// the influence is less pronounced on the actor than on the target
		if(actor) {
			dA*=0.01;dB*=0.01;dC*=0.01;dD*=0.01;
		}
		
		// updating the influence by the probability of the type of the human's personality
		String type = this.personality.getNatureType();
		double probability = action.getProbability(type);
		dA*=probability;dB*=probability;dC*=probability;dD*=probability;
		
		// the influence is less pronounced when the human nature is INDIFFERENT
		if(type.equals(Nature.INDIFFERENT)) {
			dA*=0.01;dB*=0.01;dC*=0.01;dD*=0.01;
		}

		// normalizing the altered nature's dimensions
		AH = (AH+dA)/(1+dA); 
		BH = (BH+dB)/(1+dB); 
		CH = (CH+dC)/(1+dC);
		DH = (DH+dD)/(1+dD);
		Nature newNature = new Nature(AH,BH,CH,DH);
		this.setPersonality(newNature);
	}

}

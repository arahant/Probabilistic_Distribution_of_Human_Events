package com.sk.program;

import java.util.List;

public class Human {

	/* 	private static enum Base_Nature {Benevolent, Indifferent, Malevolent};
	 *	private static enum Base_Personality {Morality, Immorality, Integrity, Fragility, Coexistence, Survivalism}
	 * 	The Base Natures are Benevolence (Blue), Indifference (Green) and Malevolence (Red)
	 * */

	public static int LIMIT = 255;
	private Nature personality;

	public Nature getPersonality() {
		return personality;
	}

	public void alterPersonality(Nature p) {
		this.personality = p;
	}

	// An action influences both the actor and target(s) and changes the probability of the nature of the action
	public void commitAction(Human actor, List<Human> targets, List<Action> actions) {
		for(int i=0;i<targets.size();i++) {
			Human target = targets.get(i);
			Action action = actions.get(i);
			target.influenceNature(action,false);
			actor.influenceNature(action,true);
			action.alterProbability();
		}
	}

	private void influenceNature(Action action, boolean actor) {
		String[] human_nature = this.personality.getNature().split("-");
		float RH = Float.parseFloat(human_nature[0]);
		float GH = Float.parseFloat(human_nature[1]);
		float BH = Float.parseFloat(human_nature[2]);

		String[] action_nature = action.getNature().getNature().split("-");
		int RA = Integer.parseInt(action_nature[0]);
		int GA = Integer.parseInt(action_nature[1]);
		int BA = Integer.parseInt(action_nature[2]);

		float dR = (float)RA/LIMIT;
		float dG = (float)GA/LIMIT;
		float dB = (float)BA/LIMIT;
		
		// the influence is less pronounced on the actor than on the target
		if(actor) {
			dR*=0.01;dG*=0.01;dB*=0.01;
		}
		// the influence is less pronounced when the human nature is INDIFFERENT
		String type = this.personality.getNatureType();
		if(type.equals(Nature.INDIFFERENT)) {
			dR*=0.01;dG*=0.01;dB*=0.01;
		}

		RH+=dR; GH+=dG; BH+=dB;
		Nature newNature = new Nature(RH,GH,BH);
		this.alterPersonality(newNature);
	}

}

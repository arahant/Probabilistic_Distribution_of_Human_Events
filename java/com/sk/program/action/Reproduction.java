package com.sk.program.action;

import com.sk.program.actor.Human;
import com.sk.program.nature.Nature;

public class Reproduction {
	
	private static int random = -1;
	
	public static Human reproduce(Human mate1, Human mate2) {
		// ensuring the mixing of personalities (genes) 50-50
		float[] sequence = new float[4];
		float d1 = getRandomPair(mate1);
		sequence[random] = d1;
		float d2 = getRandomPair(mate1);
		sequence[random] = d2;
		getRemainingPair(mate2,sequence);
		
		Nature newNature = new Nature(sequence[0],sequence[1],sequence[2],sequence[3]);
		newNature = mutateNature(newNature);
		Human progeny = new Human(newNature);
		return progeny;
	}
	
	private static float getRandomPair(Human mate1) {
		int random1 = Math.round((float)Math.random()*10)%4;
		while(random==random1)
			random1 = Math.round((float)Math.random()*10)%4;
		random = random1;
		String[] nature1 = mate1.getPersonality().getNature().split("-");
		float d = 0;
		switch(random1) {
		case 0:
			d = Float.parseFloat(nature1[0]);
			break;
		case 1:
			d = Float.parseFloat(nature1[1]);
			break;
		case 2:
			d = Float.parseFloat(nature1[2]);
			break;
		case 3:
			d = Float.parseFloat(nature1[3]);
			break;
		}
		return d;
	}
	
	private static void getRemainingPair(Human mate2, float[] sequence) {
		String[] nature2 = mate2.getPersonality().getNature().split("-");
		for(int i=0;i<sequence.length;i++) {
			if(sequence[i]==0) {
				sequence[i] = Float.parseFloat(nature2[i]);
			}
		}
	}
	
	// method for genetic mutation
	private static Nature mutateNature(Nature original) {
		Nature mutated = new Nature(original);
		String[] nature = original.getNature().split("-");
		float[] dimensions = new float[nature.length];
		for(int i=0;i<nature.length;i++)
			dimensions[i] = Float.parseFloat(nature[i]);
		double chance = Math.random();
		if(chance>0.5) {
			int place = Math.round((float)Math.random()*10)%4;
			float d = dimensions[place];
			d = (1-d)*(float)Math.random();
			dimensions[place] = d;
		}
		mutated.setNature(dimensions);
		return mutated;
	}

}

package com.sk.program.action;

import java.util.List;

import com.sk.program.actor.Human;

public class Connect {
	
	public void formConnection(Human actor, List<Human> targets) {
		String an = actor.getPersonality().getNature();
		for(Human target:targets) {
			String tn = target.getPersonality().getNature();
			float sim = getSimilarity(an, tn);
			
			// weight is higher between similar-natured people
			float weight = 1/sim;
			actor.addConnection(target, weight);
		}
	}
	
	// method to evaluate the similarity between people
	private float getSimilarity(String an, String tn) {
		String[] n1 = an.split("-");String[] n2 = tn.split("-");
		float s1 = Float.parseFloat(n1[0])-Float.parseFloat(n2[0]);
		float s2 = Float.parseFloat(n1[1])-Float.parseFloat(n2[1]);
		float s3 = Float.parseFloat(n1[2])-Float.parseFloat(n2[2]);
		float s4 = Float.parseFloat(n1[3])-Float.parseFloat(n2[3]);
		
		//greater weight is assigned to morality, followed by integrity dimensions
		int w1=3, w2=2, w3=1, w4=1;
		return Math.abs(w1*s1+w2*s2+w3*s3+w4*s4+0.01f)/(w1+w2+w3+w4);
	}

}

package com.sk.program.action;

import java.util.List;

import com.sk.program.actor.Human;
import com.sk.program.nature.Nature;

public class Connection {
	
	public static void formConnection(Human actor, List<Human> targets) {
		String an = actor.getPersonality().getNature();
		for(Human target:targets) {
			String tn = target.getPersonality().getNature();
			float sim = getSimilarity(an, tn);
			
			// weight is higher between similar-natured people
			float weight = 1/sim;
			actor.addConnection(target, weight);
			updateNature(actor);
		}
	}
	
	// method to evaluate the similarity between people
	private static float getSimilarity(String an, String tn) {
		String[] n1 = an.split("-");String[] n2 = tn.split("-");
		float s1 = Float.parseFloat(n1[0])-Float.parseFloat(n2[0]);
		float s2 = Float.parseFloat(n1[1])-Float.parseFloat(n2[1]);
		float s3 = Float.parseFloat(n1[2])-Float.parseFloat(n2[2]);
		float s4 = Float.parseFloat(n1[3])-Float.parseFloat(n2[3]);
		
		// greater weights are assigned to morality, followed by integrity dimensions
		// adding 0.01 to address /0 error
		int w1=3, w2=2, w3=1, w4=1;
		return Math.abs(w1*s1+w2*s2+w3*s3+w4*s4+0.01f)/(w1+w2+w3+w4);
	}
	
	public static void updateNature(Human actor) {
		String[] an = actor.getPersonality().getNature().split("-");
		float D1 = Float.parseFloat(an[0]);
		float D2 = Float.parseFloat(an[1]);
		float D3 = Float.parseFloat(an[2]);
		float D4 = Float.parseFloat(an[3]);
		
		float d1=0f, d2=0f, d3=0f, d4=0f, W=0f;
		for(Human contact:actor.getConnection().keySet()) {
			float w = actor.getConnection().get(contact);
			String[] cn = contact.getPersonality().getNature().split("-");
			d1 += w*Float.parseFloat(cn[0]);
			d2 += w*Float.parseFloat(cn[1]);
			d3 += w*Float.parseFloat(cn[2]);
			d4 += w*Float.parseFloat(cn[3]);
			W += w;
		}
		
		Nature newNature = new Nature((float)(D1+d1/W),(float)(D2+d2/W),(float)(D3+d3/W),(float)(D4+d4/W));
		actor.setPersonality(newNature);
	}

}

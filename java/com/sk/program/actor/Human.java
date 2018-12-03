package com.sk.program.actor;

import java.util.LinkedHashMap;
import java.util.List;

import com.sk.program.action.Action;
import com.sk.program.nature.Nature;

public class Human {

	private int id;
	private Group group;
	private String name;
	
	private float anxietyThreshold;
	private Nature personality;
	private LinkedHashMap<Human,Float> connections;
	
	private static int c = 0;
	
	public Human(float d1, float d2, float d3, float d4) {
		id = ++c;
        personality = new Nature(d1, d2, d3, d4);
		connections = new LinkedHashMap<>();
	}
	
	public Human(Nature nature) {
		id = ++c;
        personality = nature;
		connections = new LinkedHashMap<>();
	}
	
	public int getId() {
		return id;
	}
	
	public void setName(String n) {
		this.name = n;
	}
	public String getName() {
		return name;
	}
	
	public void setGroup(Group group) {
		this.group = group;
		influencePersonality();
	}
	public Group getGroup() {
		return group;
	}
	
	// group's overall nature and philosophy influencing the personality
	private void influencePersonality() {
		String[] nd = this.personality.getNature().split("-");
		float d1 = Float.parseFloat(nd[0]);
		float d2 = Float.parseFloat(nd[1]);
		float d3 = Float.parseFloat(nd[2]);
		float d4 = Float.parseFloat(nd[3]);
		
		// the nature and philosophy have slightly different weights of influence
		Nature gn = this.group.getNature();
		String[] gnd = gn.getNature().split("-");
		float gnd1 = Float.parseFloat(gnd[0]);
		float gnd2 = Float.parseFloat(gnd[1]);
		float gnd3 = Float.parseFloat(gnd[2]);
		float gnd4 = Float.parseFloat(gnd[3]);
		float wgn = 1.5f;
		
		Nature ph = this.group.getPhilosophy();
		String[] phd = ph.getNature().split("-");
		float phd1 = Float.parseFloat(phd[0]);
		float phd2 = Float.parseFloat(phd[1]);
		float phd3 = Float.parseFloat(phd[2]);
		float phd4 = Float.parseFloat(phd[3]);
		float wph = 3.0f;
		
		d1 += gnd1/wgn + phd1/wph;
		d2 += gnd2/wgn + phd2/wph;
		d3 += gnd3/wgn + phd3/wph;
		d4 += gnd4/wgn + phd4/wph;
		
		Nature newNature = new Nature(d1,d2,d3,d4);
		this.setPersonality(newNature);
	}

	public void setPersonality(Nature p) {
		this.personality = p;
	}
	public Nature getPersonality() {
		return personality;
	}
	
	public void addConnection(Human arg, float weight) {
		connections.put(arg,weight);
	}
	public LinkedHashMap<Human,Float> getConnection() {
		return connections;
	}
	
	public void resetAnxietyThreshold(float t) {
		this.anxietyThreshold = t;
	}
	public float getAnxietyThreshold() {
		return anxietyThreshold;
	}

	// An action influences both the actor and target(s) and changes the probability of the nature of the action
	public void commitAction(Human actor, List<Human> targets, List<Action> actions) {
		for(int i=0;i<targets.size();i++) {
			Human target = targets.get(i);
			Action action = actions.get(i);
			target.influencePersonality(action,false);
			actor.influencePersonality(action,true);
			action.updateProbability();
		}
	}

	private void influencePersonality(Action action, boolean actor) {
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

		// the influence is less pronounced on the actor than on the recipient
		float act = 1f;
		if(actor)
			act = 0.01f;
		
		// updating the influence by the probability of the type of the human's personality
		String type = this.personality.getNatureType();
		float pr = (float)action.getProbability(type);
		
		// the influence is less pronounced when the human nature is INDIFFERENT
		float inf = 1f;
		if(type.equals(Nature.INDIFFERENT))
			inf = 0.01f;
		
		// effective change in nature
		dA*=pr*act*inf;
		dB*=pr*act*inf;
		dC*=pr*act*inf;
		dD*=pr*act*inf;
		
		// normalizing the altered nature's dimensions
		AH = (AH+dA)/(1+dA); 
		BH = (BH+dB)/(1+dB); 
		CH = (CH+dC)/(1+dC);
		DH = (DH+dD)/(1+dD);
		Nature newNature = new Nature(AH,BH,CH,DH);
		this.setPersonality(newNature);
	}

}

package com.sk.program.actor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.sk.program.action.Action;
import com.sk.program.nature.Nature;

public class Human {

	private Nature personality;
	private float muscle;
	private float political;
	
	private LinkedHashMap<Human,Float> connections;
	
	private int id;
	private int groupId;
	private String name;
	private float anxietyThreshold;
	
	private static int c = 0;
	
	public Human(float d1, float d2, float d3, float d4) {
		id = ++c;
        personality = new Nature(d1, d2, d3, d4);
		connections = new LinkedHashMap<>();
	}
	
	public Human(Nature nature) {
        personality = nature;
		connections = new LinkedHashMap<>();
	}
	
	public int getId() {
		return id;
	}
	
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getGroupId() {
		return groupId;
	}

	public Nature getPersonality() {
		return personality;
	}
	public void setPersonality(Nature p) {
		this.personality = p;
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

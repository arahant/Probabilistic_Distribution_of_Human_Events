package com.sk.program.actor;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import com.sk.program.action.Action;
import com.sk.program.action.Event;
import com.sk.program.nature.Nature;

public class Human {

	private int id;
	private Group group;
	private String name;
	private float latitude, longitude;
	private float anxietyThreshold;
	private Nature personality;
	private LinkedHashMap<Human,Float> connections;
	private static List<Human> population;
	
	private static int c = 0;
	
	static {
		population = new LinkedList<>();
	}
	
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
	
	// connections - weight acts as interpersonal proximity
	public void addConnection(Human arg, float weight) {
		connections.put(arg,weight);
	}
	public LinkedHashMap<Human,Float> getConnection() {
		return connections;
	}
	
	public static void updatePopulation(Human arg) {
		population.add(arg);
	}
	public static List<Human> getPopulation() {
		return population;
	}
	
	// group
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
	
	// anxiety threshold
	public void resetAnxietyThreshold(float t) {
		this.anxietyThreshold = t;
	}
	public float getAnxietyThreshold() {
		return anxietyThreshold;
	}
	
	// spatial location of the individual
	public void setLocation(float lat, float lon) {
		this.latitude = lat;
		this.longitude = lon;
	}
	public void setLocation(float[] loc) {
		this.latitude = loc[0];
		this.longitude = loc[1];
	}
	
	public float getLatitude() {
		return latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public float[] getLocation() {
		return new float[] {latitude,longitude};
	}

	// An action influences both the actor and target(s) and changes the probability of the nature of the action
	public void commitAction(List<Human> targets, List<Action> actions) {
		for(int i=0;i<targets.size();i++) {
			Human target = targets.get(i);
			Action action = actions.get(i);
			target.influencePersonality(action,false);
			this.influencePersonality(action,true);
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
		float pr = (float)Action.getProbability(type);
		
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
	
	// reaction to any event by the actor 
	public void eventReaction(Event event) {
		for(Human link:this.getConnection().keySet()) {
			link.influencePersonality(event,false);
			this.influencePersonality(event,true);
			Action.updateProbability(event);
		}
	}
	
	private void influencePersonality(Event event, boolean actor) {
		String[] human_nature = this.personality.getNature().split("-");
		float AH = Float.parseFloat(human_nature[0]);
		float BH = Float.parseFloat(human_nature[1]);
		float CH = Float.parseFloat(human_nature[2]);
		float DH = Float.parseFloat(human_nature[3]);

		// the change in the nature influenced by the action
		String[] event_nature = event.getNature().getNature().split("-");
		float dA = Float.parseFloat(event_nature[0]);
		float dB = Float.parseFloat(event_nature[1]);
		float dC = Float.parseFloat(event_nature[2]);
		float dD = Float.parseFloat(event_nature[3]);

		// the influence is less pronounced on the actor than on the recipient
		float act = 1f;
		if(actor)
			act = 0.01f;
		
		// updating the influence by the probability of the type of the human's personality
		String type = this.personality.getNatureType();
		float pr = (float)Action.getProbability(type);
		
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

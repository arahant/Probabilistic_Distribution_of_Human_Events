package com.sk.program.actor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.sk.program.action.Event;
import com.sk.program.nature.History;
import com.sk.program.nature.Nature;

public class Group {
	
	private int id;
	private String name;
	
	private Nature nature;
	private Nature philosophy;
	private History history;
	
	private float muscle;
	private float political;
	private float unity;
	private float groupAnxiety;
	
	private Human leader;
	private List<Human> members;
	private HashMap<Integer,Integer> traversed;
	
	private static int count = 0;
	
	public Group(String nm, List<Human> ms) {
		this.id = ++count;
		this.name = nm;
		members.addAll(ms);
		calculateAnxietyThreshold();
		calculateNature();
	}
	
	// basics
	public int getStrength() {
		return members.size();
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	// leader
	public void setLeader(Human member) {
		this.leader = member;
	}
	
	public Human getLeader() {
		return leader;
	}
	
	// members
	public void addMember(Human member) {
		members.add(member);
		updateAnxietyThreshold(member);
		updateNature(member);
	}
	
	public List<Human> getMembers() {
		return members;
	}
	
	// anxiety threshold
	public void calculateAnxietyThreshold() {
		float at = 0;
		for(Human m:members)
			at+=m.getAnxietyThreshold();
		groupAnxiety = at/getStrength();
	}
	
	public void updateAnxietyThreshold(Human member) {
		int n = getStrength();
		float at = groupAnxiety*n;
		at+=member.getAnxietyThreshold();
		groupAnxiety = at/(n+1);
	}
	
	public float getAnxietyThreshold() {
		return groupAnxiety;
	}
	
	// nature
	public void setNature(Nature n) {
		this.nature = n;
	}
	
	public void updateNature(Human member) {
		String[] nature = this.nature.getNature().split("-");
		int n = getStrength();
		float D1 = Float.parseFloat(nature[0])*n;
		float D2 = Float.parseFloat(nature[1])*n;
		float D3 = Float.parseFloat(nature[2])*n;
		float D4 = Float.parseFloat(nature[3])*n;
		
		String[] change = member.getPersonality().getNature().split("-");
		float d1 = Float.parseFloat(change[0]);
		float d2 = Float.parseFloat(change[1]);
		float d3 = Float.parseFloat(change[2]);
		float d4 = Float.parseFloat(change[3]);
		
		Nature nat = new Nature((D1+d1)/(n+1),(D2+d2)/(n+1),(D3+d3)/(n+1),(D4+d4)/(n+1));
		this.setNature(nat);
	}
	
	public void calculateNature() {
		float D1=0,D2=0,D3=0,D4=0;
		for(Human member:members) {
			String[] nature = member.getPersonality().getNature().split("-");
			D1+=Float.parseFloat(nature[0]);
			D2+=Float.parseFloat(nature[1]);
			D3+=Float.parseFloat(nature[2]);
			D4+=Float.parseFloat(nature[3]);
			Nature nat = new Nature(D1,D2,D3,D4);
			this.setNature(nat);
		}
	}
	
	public Nature getNature() {
		return nature;
	}
	
	// philosophy
	public void setPhilosophy(Nature ph) {
		this.philosophy = new Nature(ph);
	}
	
	// the group's history influences its philosophy 
	public void updatePhilosophyHistory() {
		LinkedHashMap<Event,Nature> actions = history.getHistory();
		for(Event event:actions.keySet()) {
			Nature reaction = actions.get(event);
			updatePhilosophy(reaction);
		}
	}
	
	public void updatePhilosophy(Nature ph) {
		String[] pd = philosophy.getNature().split("-");
		float D1 = Float.parseFloat(pd[0]);
		float D2 = Float.parseFloat(pd[1]);
		float D3 = Float.parseFloat(pd[2]);
		float D4 = Float.parseFloat(pd[3]);
		
		String[] ph_change = ph.getNature().split("-");
		float d1 = D1-Float.parseFloat(ph_change[0]);
		float d2 = D2-Float.parseFloat(ph_change[1]);
		float d3 = D3-Float.parseFloat(ph_change[2]);
		float d4 = D4-Float.parseFloat(ph_change[3]);
		
		D1 = (D1+d1)/(1+d1);
		D2 = (D2+d2)/(1+d2);
		D1 = (D3+d3)/(1+d3);
		D1 = (D4+d4)/(1+d4);
		
		Nature newNature = new Nature(D1,D2,D3,D4);
		philosophy = newNature;
	}
	
	public Nature getPhilosophy() {
		return philosophy;
	}
	
	// history
	public void defineHistory(LinkedHashMap<Event,Nature> actions) {
		if(history==null)
			history = new History();
		history.addHistoricalEvents(actions);
	}
	
	public void defineHistory(Event ev, Nature nat) {
		if(history==null)
			history = new History();
		history.addHistoricalEvents(ev,nat);
	}
	
	public History getHistory() {
		return history;
	}
	
	// power
	public void setMusclePower(float p) {
		this.muscle = p;
	}
	public void setPoliticalPower(float p) {
		this.political = p;
	}
	
	public float getMuscle() {
		return muscle;
	}
	public float getPolitical() {
		return political;
	}
	
	// unity of the group
	public void calculateGroupUnity() {
		for(Human member:members) {
			LinkedHashMap<Human, Float> connections = member.getConnection();
			for(Human link:connections.keySet())
				if(members.contains(link) && !isTraversed(member.getId(),link.getId()))
					unity+=connections.get(link);
		}
	}
	
	private boolean isTraversed(int h1, int h2) {
		if(traversed==null)
			traversed = new HashMap<>();
		if(traversed.put(h1,h2)==null || traversed.put(h2,h1)==null)
			return false;
		else
			return true;
	}
	
	public float getGroupUnity() {
		return unity;
	}

}

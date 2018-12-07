package com.sk.program.actor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

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
	private static LinkedList<Group> groupList;

	private static int count = 0;

	public Group(String nm, List<Human> ms) {
		this.id = ++count;
		this.name = nm;
		members.addAll(ms);
		calculateAnxietyThreshold();
		calculateNature();
		updateLocation();
	}
	
	public static Group getEventGroup() {
		Scanner sc = new Scanner(System.in);
		Group id = groupList.get(sc.nextInt());
		sc.close();
		return id;
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
		}
		int n = getStrength();
		Nature nat = new Nature((float)D1/n,(float)D2/n,(float)D3/n,(float)D4/n);
		this.setNature(nat);
	}

	public Nature getNature() {
		return nature;
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

	// unity of the group - depends on interpersonal proximity and 
	public void calculateGroupUnity() {
		int c=0;
		for(Human member:members) {
			LinkedHashMap<Human, Float> connections = member.getConnection();
			for(Human link:connections.keySet())
				if(members.contains(link) && !isTraversed(member.getId(),link.getId())) {
					unity+=connections.get(link);c++;
				}
		}
		unity = (float)unity/c;
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

	/* After forming a group, the members have a natural tendency to converge, 
	 * depending on the proximity of the individual's personality
	 * with the group's nature and philosophy. 
	 * They converge towards the mean (centroid) of the group.
	 */
	private void updateLocation() {
		float[] mean = getMeanLocation();
		// each nature dimension has a different weight
		int w1=3, w2=2, w3=1, w4=1;
		
		// group's nature
		String[] grNat = this.nature.getNature().split("-");
		float g1 = Float.parseFloat(grNat[0]);
		float g2 = Float.parseFloat(grNat[1]);
		float g3 = Float.parseFloat(grNat[2]);
		float g4 = Float.parseFloat(grNat[3]);
		
		// group's philosophy
		String[] grPh = this.philosophy.getNature().split("-");
		float p1 = Float.parseFloat(grPh[0]);
		float p2 = Float.parseFloat(grPh[1]);
		float p3 = Float.parseFloat(grPh[2]);
		float p4 = Float.parseFloat(grPh[3]);
		
		for(Human member:members) {
			/* individual personalities' proximity to the group's nature
			 * and philosophy determining the movement factor
			 */
			String[] nature = member.getPersonality().getNature().split("-");
			float d1 = Float.parseFloat(nature[0]);
			float d2 = Float.parseFloat(nature[1]);
			float d3 = Float.parseFloat(nature[2]);
			float d4 = Float.parseFloat(nature[3]);
			
			float diff1 = w1*Math.abs(1-d1+g1) + w2*Math.abs(1-d2+g2)
					+ w3*Math.abs(1-d3+g3) + w4*Math.abs(1-d4+g4);
			float diff2 = w1*Math.abs(1-d1+p1) + w2*Math.abs(1-d2+p2)
					+ w3*Math.abs(1-d3+p3) + w4*Math.abs(1-d4+p4);
			float factor = unity*diff1*diff2;
			
			float[] loc = member.getLocation();
			loc[0] -= (float)(loc[0]-mean[0])*factor;
			loc[1] -= (float)(loc[1]-mean[1])*factor;
			member.setLocation(loc);
		}
	}
	
	private float[] getMeanLocation() {
		float meanLat = 0;
		float meanLon = 0;
		for(Human member:members) {
			float weight = member.getConnection().get(leader);
			meanLat += member.getLatitude()*weight;
			meanLon += member.getLongitude()*weight;
		}
		meanLat = (float)meanLat/getStrength();
		meanLon = (float)meanLon/getStrength();
		return new float[] {meanLat,meanLon};
	}

}

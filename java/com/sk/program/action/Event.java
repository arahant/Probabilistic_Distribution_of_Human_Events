package com.sk.program.action;

import java.util.Date;

import com.sk.program.actor.Group;
import com.sk.program.nature.Nature;

public class Event {
	
	private String name;
	private Nature nature;
	private Date date;
	private Group group;
	
	// for historical events
	public Event(Nature nat, String nm, Date dt) {
		this.name = nm;
		this.date = dt;
		this.nature = nat;
	}
	
	// for occurring events
	public Event(Nature nat, String nm, Date dt, Group gr) {
		this.name = nm;
		this.date = dt;
		this.nature = nat;
		this.group = gr;
	}
	
	public Event(Nature nat, Group g) {
		this.nature = nat;
		this.group = g;
	}
	
	public Event(Nature nat) {
		this.nature = nat;
	}
	
	public String getName() {
		return name;
	}
	
	public Date getDate() {
		return date;
	}
	
	public Nature getNature() {
		return nature;
	}
	
	public Group getGroup() {
		return group;
	}
	
	public static Event generateEvent(Group g) {
		Nature nat = Nature.getRandomNature();
		Event event = new Event(nat,g);
		return event;
	}

}

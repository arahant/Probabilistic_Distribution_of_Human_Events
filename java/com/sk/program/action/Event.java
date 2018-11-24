package com.sk.program.action;

import java.sql.Date;

import com.sk.program.nature.Nature;

public class Event {
	
	private String name;
	private Nature nature;
	private Date date;
	
	public Event(String nm, Date dt, Nature nat) {
		this.name = nm;
		this.date = dt;
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

}

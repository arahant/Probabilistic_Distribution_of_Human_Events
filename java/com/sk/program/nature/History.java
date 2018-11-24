package com.sk.program.nature;

import java.util.LinkedHashMap;

import com.sk.program.action.Event;

public class History {

	private LinkedHashMap<Event,Nature> actions;
	
	public History() {
		actions = new LinkedHashMap<>();
	}
	
	public void addHistoricalEvents(Event event, Nature reaction) {
		actions.put(event,reaction);
	}
	
	public void addHistoricalEvents(LinkedHashMap<Event,Nature> actions) {
		actions.putAll(actions);
	}
	
	public LinkedHashMap<Event,Nature> getHistory() {
		return actions;
	}
}

package com.sk.program.time;

import java.util.List;

import com.sk.program.action.Event;
import com.sk.program.actor.Group;
import com.sk.program.actor.Human;

public class TimeStamp implements Runnable {
	
	private static List<Human> population;
	private static final int interval = 25;
	
	static {
		population = Human.getPopulation();
	}
	
	public void run() {
		for(long t=0;;t++) {
			try {
				Thread.currentThread().wait(t);
				execute(t);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void execute(long t) {
		if(t%interval==0)
			executeEvent();
	}
	
	public static void executeEvent() {
		Group group = Group.getEventGroup();
		Event ev = Event.generateEvent(group);
		for(Human member:population)
			member.eventReaction(ev);
	}

}

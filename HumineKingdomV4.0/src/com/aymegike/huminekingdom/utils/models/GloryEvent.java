package com.aymegike.huminekingdom.utils.models;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.aymegike.huminekingdom.HumineKingdom;

public abstract class GloryEvent {
	
	private Event event;
	
	public GloryEvent(Event event) {
		this.event = event;
		HumineKingdom.getGloryManager().addEvent(this);
	}
	
	public abstract void onPlayerBeGlorious(Player player);
	
	public Event getPlayerEvent() {
		return event;
	}
	

}

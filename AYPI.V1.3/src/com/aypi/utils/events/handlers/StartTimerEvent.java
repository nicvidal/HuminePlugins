package com.aypi.utils.events.handlers;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.aypi.utils.Timer;

public class StartTimerEvent extends Event {

	private Timer timer;
	

	public StartTimerEvent(Timer timer) {
		this.timer = timer;
	}

	private static final HandlerList handlerList = new HandlerList();
	
	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}

	public static HandlerList getHandlerList() {
		return handlerList;
	}

	public Timer getTimer() {
		return timer;
	}

}

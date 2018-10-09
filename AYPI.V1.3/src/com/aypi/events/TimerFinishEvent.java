package com.aypi.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.aypi.utils.events.handlers.FinishTimerEvent;

public class TimerFinishEvent implements Listener{

	@EventHandler
	public void onFinish(FinishTimerEvent event) {
		if(event.getTimer().getFinishAction() != null) {
			event.getTimer().execute();
		}
	}
}

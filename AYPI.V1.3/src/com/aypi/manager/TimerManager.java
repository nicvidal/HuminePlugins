package com.aypi.manager;

import java.util.ArrayList;

import com.aypi.utils.Timer;

public class TimerManager {

	private ArrayList<Timer> timers;

	public TimerManager() {
		timers = new ArrayList<Timer>();
	}

	public void addTimer(Timer timer) {
		timers.add(timer);
	}

	public void removeTimer(Timer timer) {
		timers.remove(timer);
	}

	public ArrayList<Timer> getTimers() {
		return timers;
	}
}

package com.aypi.manager;

import java.util.ArrayList;

import com.aypi.utils.Zone;

public class ZoneManager {
	
	private ArrayList<Zone> zones;
	
	public ZoneManager() {
		zones = new ArrayList<Zone>();
	}
	
	public void addZone(Zone zone) {
		zones.add(zone);
	}
	
	public void removeZone(Zone zone) {
		zones.remove(zone);
	}
	
	public ArrayList<Zone> getZones() {
		return zones;
	}

}

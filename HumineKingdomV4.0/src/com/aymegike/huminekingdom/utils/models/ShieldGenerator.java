package com.aymegike.huminekingdom.utils.models;

import org.bukkit.Location;

import com.aypi.utils.Zone;

public class ShieldGenerator {
	
	private Kingdom kingdom;
	private Location location;
	private Zone zone;
	
	public ShieldGenerator(Kingdom kingdom, Location location, Zone zone) {
		this.kingdom = kingdom;
		this.location = location;
		this.zone = zone;
	}
	
	public Kingdom getKingdom() {
		return kingdom;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public Zone getZone() {
		return zone;
	}

}

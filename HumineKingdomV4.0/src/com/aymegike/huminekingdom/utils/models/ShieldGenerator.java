package com.aymegike.huminekingdom.utils.models;

import org.bukkit.Location;

import com.aypi.utils.Zone;

public class ShieldGenerator {
	
	private Kingdom kingdom;
	private Location location;
	private Zone zone;
	private boolean isActive;
	
	public ShieldGenerator(Kingdom kingdom, Location location, Zone zone, boolean isActive) {
		this.kingdom = kingdom;
		this.location = location;
		this.zone = zone;
		this.isActive = isActive;
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
	
	public boolean isActive() {
		return isActive;
	}
	
	public void setActive(boolean active) {
		this.isActive = active;
	}

}

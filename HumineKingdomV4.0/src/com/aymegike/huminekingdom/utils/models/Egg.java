package com.aymegike.huminekingdom.utils.models;

import org.bukkit.Location;

public class Egg {
	
	private Location location;
	private Kingdom kingdom;
	
	public Egg(Kingdom kingdom, Location location) {
		this.location = location;
		this.kingdom = kingdom;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public Kingdom getKingdom() {
		return this.kingdom;
	}
	
}

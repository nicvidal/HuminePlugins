package com.aypi.utils;

import java.util.ArrayList;

import org.bukkit.Location;

public class Square {
	
	private ArrayList<Location> locations;
	
	private Location pos1;
	private Location pos2;
	
	public Square(Location pos1, Location pos2) {
		
		int xmin;
		int ymin;
		int zmin;
		
		int xmax;
		int ymax;
		int zmax;
		
		if (pos1.getBlockX() < pos2.getBlockX()) {
			xmin = pos1.getBlockX();
			xmax = pos2.getBlockX();
		} else {
			xmin = pos2.getBlockX();
			xmax = pos1.getBlockX();
		}
		
		if (pos1.getBlockY() < pos2.getBlockY()) {
			ymin = pos1.getBlockY();
			ymax = pos2.getBlockY();
		} else {
			ymin = pos2.getBlockY();
			ymax = pos1.getBlockY();
		}
		
		if (pos1.getBlockZ() < pos2.getBlockZ()) {
			zmin = pos1.getBlockZ();
			zmax = pos2.getBlockZ();
		} else {
			zmin = pos2.getBlockZ();
			zmax = pos1.getBlockZ();
		}
		
		pos1 = new Location(pos1.getWorld(), xmin, ymin, zmin);
		pos2 = new Location(pos2.getWorld(), xmax, ymax, zmax);
		
		this.pos1 = pos1;
		this.pos2 = pos2;
			
		
		locations = new ArrayList<Location>();
		
		for (int x = 0 ; pos1.getBlockX() + x <= pos2.getBlockX() ; x++) {
			for (int y = 0 ; pos1.getBlockY() + y <= pos2.getBlockY() ; y++) {
				for (int z = 0 ; pos1.getBlockZ() + z <= pos2.getBlockZ() ; z++) {
					
					locations.add(new Location(pos1.getWorld(), pos1.getBlockX() + x, pos1.getBlockY() + y, pos1.getBlockZ() + z));
					
				}
			}
		}
		
	}
	
	public Location getPos1() {
		return pos1;
	}
	
	public Location getPos2() {
		return pos2;
	}
	
	public ArrayList<Location> getScareLoc() {
		return locations;
	}

}

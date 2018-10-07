package com.aymegike.huminezone.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerRegister {
	
	private Player player;
	private Location loc;
	
	public PlayerRegister(Player player , Location loc) {
		
		this.loc = loc;
		this.player = player;
		
		
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Location getLocation() {
		return loc;
	}
	

}

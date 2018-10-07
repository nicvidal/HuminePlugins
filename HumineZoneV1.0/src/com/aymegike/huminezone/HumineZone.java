package com.aymegike.huminezone;

import org.bukkit.plugin.java.JavaPlugin;

import com.aymegike.huminezone.events.EventManager;

public class HumineZone extends JavaPlugin {
	
	public void onEnable() {
		super.onEnable();
		EventManager.registerEvent(this);
	}
	
	public void onDisable() {
		super.onDisable();
		
	}

}

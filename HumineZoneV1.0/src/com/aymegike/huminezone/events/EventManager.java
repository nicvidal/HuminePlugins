package com.aymegike.huminezone.events;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import com.aymegike.huminezone.HumineZone;

public class EventManager {
	
	public static void registerEvent(HumineZone pl) {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerClick(), pl);
	}

}

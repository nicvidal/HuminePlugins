package com.aypi.events;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import com.aypi.Aypi;

public class EventManager {
	
	public EventManager(Aypi pl) {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new MenuListener(), pl);
		pm.registerEvents(new PlayerBreakBlock(), pl);
		pm.registerEvents(new PlayerPlaceBlock(), pl);
		if (pl.getConfig().getBoolean("playermove"))
			pm.registerEvents(new PlayerMove(), pl);
	}

}

package com.aypi.events;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import com.aypi.Aypi;

public class EventManager {

	/*
	 * Class gérant les évenements de l'AYPI
	 * 
	 * @param plugin Le plugin AYPI
	 */

	public EventManager(Aypi plugin) {
		PluginManager pluginManager = Bukkit.getPluginManager();

		pluginManager.registerEvents(new MenuListener(), plugin);
		pluginManager.registerEvents(new PlayerBreakBlock(), plugin);
		pluginManager.registerEvents(new PlayerPlaceBlock(), plugin);
		pluginManager.registerEvents(new TimerFinishEvent(), plugin);

		if (plugin.getConfig().getBoolean("playermove"))
			pluginManager.registerEvents(new PlayerMove(), plugin);
	}

}

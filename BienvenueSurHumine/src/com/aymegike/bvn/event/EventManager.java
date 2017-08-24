package com.aymegike.bvn.event;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import com.aymegike.bvn.Main;


public class EventManager {
	
	public static void registerEvents(Main pl){
		PluginManager pm = Bukkit.getPluginManager();
		
		pm.registerEvents(new PlayerJoin(), pl);
		pm.registerEvents(new MenuManager(), pl);
		pm.registerEvents(new PlayerChat(), pl);
		pm.registerEvents(new PlayerFall(), pl);
		pm.registerEvents(new Respawn(), pl);
		pm.registerEvents(new playerLeft(), pl);
		
	}

}

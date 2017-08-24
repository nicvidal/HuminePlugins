package com.aymegike.huminekingdom.event;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import com.aymegike.huminekingdom.HumineKingdom;

public class EventManager {	
	
	public static void registerEvents(HumineKingdom pl){
		PluginManager pm = Bukkit.getPluginManager();
		
		pm.registerEvents(new PlayerJoin(), pl);
		pm.registerEvents(new PlaceBlock(), pl);
		pm.registerEvents(new BreakBlock(), pl);
		pm.registerEvents(new MenuManager(), pl);
		pm.registerEvents(new GetName(), pl);
		pm.registerEvents(new PlayerLeft(), pl);
		pm.registerEvents(new KingdomChat(), pl);
		pm.registerEvents(new Explosion(), pl);
		pm.registerEvents(new PlayerDie(), pl);
		pm.registerEvents(new DropItem(), pl);
	}

}

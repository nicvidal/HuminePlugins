package com.aymegike.huminekingdom.listener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.listener.events.BreakBlock;
import com.aymegike.huminekingdom.listener.events.ExplosionBlock;
import com.aymegike.huminekingdom.listener.events.PlaceBlock;
import com.aymegike.huminekingdom.listener.events.PlayerChatEvent;
import com.aymegike.huminekingdom.listener.events.PlayerClick;

public class EventManager {
	
	public EventManager(HumineKingdom pl) {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerChatEvent(), pl);
		pm.registerEvents(new PlaceBlock(), pl);
		pm.registerEvents(new BreakBlock(), pl);
		pm.registerEvents(new ExplosionBlock(), pl);
		pm.registerEvents(new PlayerClick(), pl);
	}

}

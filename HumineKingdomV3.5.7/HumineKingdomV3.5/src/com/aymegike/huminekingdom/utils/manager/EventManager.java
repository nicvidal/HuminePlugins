package com.aymegike.huminekingdom.utils.manager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.event.BreakBlock;
import com.aymegike.huminekingdom.event.DropItem;
import com.aymegike.huminekingdom.event.Explosion;
import com.aymegike.huminekingdom.event.GetName;
import com.aymegike.huminekingdom.event.KingdomChat;
import com.aymegike.huminekingdom.event.MenuManager;
import com.aymegike.huminekingdom.event.PlaceBlock;
import com.aymegike.huminekingdom.event.PlayerDie;
import com.aymegike.huminekingdom.event.PlayerJoin;
import com.aymegike.huminekingdom.event.PlayerLeft;

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

package com.aymegike.huminepay.event;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import com.aymegike.huminepay.HuminePay;

public class EventManager {
	
	public static void registerEvents(HuminePay pl){
		
		PluginManager pm = Bukkit.getPluginManager();
		
		pm.registerEvents(new MenuManager(), pl);
		
	}	

}

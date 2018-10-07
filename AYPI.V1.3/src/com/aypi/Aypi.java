package com.aypi;

import org.bukkit.plugin.java.JavaPlugin;

import com.aypi.events.EventManager;
import com.aypi.manager.MenuManager;
import com.aypi.manager.ZoneManager;

public class Aypi extends JavaPlugin {
	
	final public static String VERSION = "1.3";
	private static MenuManager mm;
	private static ZoneManager zm;
	
	public void onEnable() {
		System.out.println("Aypi V"+VERSION+" loaded");
		saveDefaultConfig();
		mm = new MenuManager();
		zm = new ZoneManager();
		new EventManager(this);
	}
	
	public void onDisable() {
		System.out.println("Aypi V"+VERSION+" unloaded");
	}
	
	public static MenuManager getMenuManager() {
		return mm;
	}
	
	public static ZoneManager getZoneManager() {
		return zm;
	}
}

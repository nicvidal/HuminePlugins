package com.aypi;

import org.bukkit.plugin.java.JavaPlugin;

import com.aypi.events.EventManager;
import com.aypi.manager.MenuManager;
import com.aypi.manager.TimerManager;
import com.aypi.manager.ZoneManager;

public class Aypi extends JavaPlugin {

	final static String VERSION = "1.3";
	private static MenuManager menuManager;
	private static ZoneManager zoneManager;
	private static TimerManager timerManager;

	/*
	 * Class principal de l'API AYPI
	 */

	public void onEnable() {
		System.out.println("Aypi V" + VERSION + " loaded");
		saveDefaultConfig();
		menuManager = new MenuManager();
		zoneManager = new ZoneManager();
		timerManager = new TimerManager();
		new EventManager(this);
	}

	public void onDisable() {
		System.out.println("Aypi V" + VERSION + " unloaded");
	}

	public static MenuManager getMenuManager() {
		return menuManager;
	}

	public static ZoneManager getZoneManager() {
		return zoneManager;
	}

	public static TimerManager getTimerManager() {
		return timerManager;
	}
}

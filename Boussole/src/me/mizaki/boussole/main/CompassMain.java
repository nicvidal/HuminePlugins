package me.mizaki.boussole.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.mizaki.boussole.events.ClickCompassEvent;

public class CompassMain extends JavaPlugin
{
	
	private List<String> permissions;
	private static CompassMain instance;
	
	private ClickCompassEvent clickCompassEvent;

	@Override
	public void onEnable()
	{
		super.onEnable();
		
		this.permissions = new ArrayList<String>();
		instance = this;
		this.clickCompassEvent = new ClickCompassEvent();
		
		
		// Partie des commandes
		
		
		// Partie des evenements
		this.getServer().getPluginManager().registerEvents(this.clickCompassEvent, this);
		
	}
	
	public static void sendMessage(CommandSender sender, String message)
	{
		sender.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "[Boussole] " + ChatColor.RESET + message);
	}

	public List<String> getPermissions()
	{
		return permissions;
	}

	public void setPermissions(List<String> permissions)
	{
		this.permissions = permissions;
	}

	public static CompassMain getInstance()
	{
		return instance;
	}

	public ClickCompassEvent getClickCompassEvent()
	{
		return clickCompassEvent;
	}
}

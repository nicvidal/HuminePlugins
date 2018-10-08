package me.mizaki.boussole.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.mizaki.boussole.commands.ChangeRegisterLocationCommand;
import me.mizaki.boussole.events.ClickCompassEvent;
import me.mizaki.boussole.events.QuitEvent;
import me.mizaki.boussole.events.ReceiptTargetNameEvent;

public class CompassMain extends JavaPlugin
{
	
	private static CompassMain instance;
	
	private ClickCompassEvent clickCompassEvent;
	private ReceiptTargetNameEvent receiptTargetNameEvent;
	private QuitEvent quitEvent;
	
	private ChangeRegisterLocationCommand changeRegisterLocationCommand;
	private HashMap<String, Location> Positions;
	private Location defaultSpawn;
	
	private List<String> searchDemands;
	private HashMap<String, String> targetDemands;
	
	
	@Override
	public void onEnable()
	{
		super.onEnable();
		
		instance = this;
		this.clickCompassEvent = new ClickCompassEvent();
		this.receiptTargetNameEvent = new ReceiptTargetNameEvent();
		this.quitEvent = new QuitEvent();
		
		Positions = new HashMap<String, Location>();
		changeRegisterLocationCommand = new ChangeRegisterLocationCommand();
		
		this.searchDemands = new ArrayList<String>();
		this.targetDemands = new HashMap<String, String>();
		
		this.saveDefaultConfig();
		this.getDataFolder().setWritable(true);
		
		if(this.getDataFolder().exists()) {
			if(this.getConfig().contains("RegisterLocation")) {
				for(String key : this.getConfig().getConfigurationSection("RegisterLocation").getKeys(false)) {
					this.Positions.put(key, (Location) this.getConfig().get("RegisterLocation." + key));
				}
			}
			
			if(this.getConfig().contains("DefaultSpawn")) {
				this.defaultSpawn = (Location) this.getConfig().get("DefaultSpawn");
			}
		}
		
		// Partie des commandes
		this.getCommand("compass").setExecutor(this.changeRegisterLocationCommand);
		
		// Partie des evenements
		this.getServer().getPluginManager().registerEvents(this.clickCompassEvent, this);
		this.getServer().getPluginManager().registerEvents(this.receiptTargetNameEvent, this);
		this.getServer().getPluginManager().registerEvents(this.quitEvent, this);
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		
		if(this.getDataFolder().exists()) {
			try {
				for(Map.Entry<String, Location> entry : this.Positions.entrySet()) {
					this.getConfig().set("RegisterLocation." + entry.getKey(), entry.getValue());
				}
				
				if(this.defaultSpawn != null) {
					this.getConfig().set("DefaultSpawn", this.defaultSpawn);
				}
				
				this.saveConfig();
				
			} catch (Exception e) {
				sendMessage(this.getServer().getConsoleSender(), ChatColor.RED + "ERROR enregistrement des positions");
				sendMessage(this.getServer().getConsoleSender(), ChatColor.RED + e.getMessage());
			}
		}
		else
			sendMessage(this.getServer().getConsoleSender(), ChatColor.RED + "Fichier config introuvable");
		
		this.searchDemands.clear();
		this.targetDemands.clear();
		this.Positions.clear();
	}
	
	public static void sendMessage(CommandSender sender, String message)
	{
		sender.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "[Boussole] " + ChatColor.RESET + message);
	}

	public static CompassMain getInstance()
	{
		return instance;
	}

	public ClickCompassEvent getClickCompassEvent()
	{
		return clickCompassEvent;
	}

	public HashMap<String, Location> getPositions() {
		return Positions;
	}

	public void setPositions(HashMap<String, Location> positions) {
		Positions = positions;
	}

	public Location getDefaultSpawn() {
		return defaultSpawn;
	}

	public void setDefaultSpawn(Location defaultSpawn) {
		this.defaultSpawn = defaultSpawn;
	}

	public List<String> getSearchDemands()
	{
		return searchDemands;
	}

	public HashMap<String, String> getTargetDemands()
	{
		return targetDemands;
	}
}

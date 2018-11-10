package com.humine.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.humine.events.ArmorDeadEvent;
import com.humine.events.ConnectPlayerEvent;
import com.humine.events.DeconnectPlayerEvent;
import com.humine.util.ArmorStand;

public class BattleMain extends JavaPlugin {

	
	private static BattleMain instance;
	
	private List<ArmorStand> armors;
	
	@Override
	public void onEnable() {
		super.onEnable();
		
		// Initiation
		instance = this;
		this.armors = new ArrayList<ArmorStand>();
		
		// Déclaration des évenements
		this.getServer().getPluginManager().registerEvents(new ArmorDeadEvent(), this);
		this.getServer().getPluginManager().registerEvents(new ConnectPlayerEvent(), this);
		this.getServer().getPluginManager().registerEvents(new DeconnectPlayerEvent(), this);
		
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		
		for(ArmorStand as : this.armors) {
			as.getArmorStand().remove();
		}
		
		this.armors.clear();
	}
	
	public static void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.AQUA + "[HumineBattle] " + ChatColor.RESET + message);
	}
	

	public static BattleMain getInstance() {
		return instance;
	}

	public List<ArmorStand> getArmors() {
		return armors;
	}

	public void setArmors(List<ArmorStand> armors) {
		this.armors = armors;
	}

	
}

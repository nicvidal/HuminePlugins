package com.aymegike.huminekingdom;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import com.aymegike.huminekingdom.listener.CommandManager;
import com.aymegike.huminekingdom.utils.MenuList;
import com.aymegike.huminekingdom.utils.Permissions;
import com.aymegike.huminekingdom.utils.managers.FileManager;
import com.aymegike.huminekingdom.utils.managers.KingdomManager;
import com.aymegike.huminekingdom.utils.models.Grade;
import com.aymegike.huminekingdom.utils.models.Kingdom;
import com.aypi.utils.inter.ZoneListener;

public class HumineKingdom extends JavaPlugin {
	
	private static FileManager fileManager;
	private static KingdomManager kManager;
	
	public void onEnable() {
		System.out.println("-------------------------------------------");
		System.out.println("[HumineKingdom] Aymegike & coo production !");
		System.out.println("-------------------------------------------");
		new com.aymegike.huminekingdom.listener.EventManager(this);
		new FileManager();
		kManager = new KingdomManager();
		new CommandManager(this);
	}
	
	public void onDisable() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			MenuList.closePlayerMenu(player);
			player.closeInventory();
		}
	}
	
	public static FileManager getFileManager() {
		return fileManager;
	}
	
	public static Kingdom getPlayerKingdom(OfflinePlayer player) {
		for (Kingdom kingdom : kManager.getKingdomList()) {
			for (OfflinePlayer op : kingdom.getMembers()) {
				if (op.getUniqueId().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
					return kingdom;
				}
			}
		}
		
		return null;
	}
	
	public static Grade getPlayerGrade(OfflinePlayer player) {
		
		for (Grade grade : HumineKingdom.getPlayerKingdom(player).getGrades()) {
			if (grade.getMembers().contains(player)) {
				return grade;
			}
		}
		
		return null;
	}
	
	public static Kingdom getKingdom(String name) {
		
		for (Kingdom kingdom : kManager.getKingdomList()) {
			if (kingdom.getName().equalsIgnoreCase(name)) {
				return kingdom;
			}
		}
		
		return null;
	}
	
	public static KingdomManager getKingdomManager() {
		return kManager;
	}
	
	public static boolean playerAsKingdomPermission(OfflinePlayer player, String permission) {
		return HumineKingdom.getPlayerGrade(player).getPermissions().contains(permission);
	}
	
	//ZONE PLAYER GESTION
	
	public static ZoneListener getZoneListener(Kingdom kingdom) {

		return new ZoneListener() {

			
			
			private void cancel(Player player, Event e) {
				player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 5, 1);
			}
			
			private void accept(Player player, Event e) {}

			@Override
			public void onPlayerEnter(Player player, PlayerMoveEvent e) {}

			@Override
			public void onPlayerTryToPlaceBlock(Player player, Block block, BlockPlaceEvent e) {
				if (player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
					
					if (HumineKingdom.getPlayerKingdom(player) != null && HumineKingdom.getPlayerKingdom(player) == kingdom) {
						if (HumineKingdom.getPlayerGrade(player) != null && HumineKingdom.getPlayerGrade(player).containPermission(Permissions.BUILD.getPermission())) {
							accept(player, e);
						} else {
							e.setCancelled(true);
							cancel(player, e);
						}
					} else {
						e.setCancelled(true);
						cancel(player, e);
					}
					
				}
				
			}

			@Override
			public void onPlayerTryToRemoveBlock(Player player, Block block, BlockBreakEvent e) {
				if (player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
					
					if (HumineKingdom.getPlayerKingdom(player) != null && HumineKingdom.getPlayerKingdom(player) == kingdom) {
						if (HumineKingdom.getPlayerGrade(player) != null && HumineKingdom.getPlayerGrade(player).containPermission(Permissions.BREAK.getPermission())) {
							accept(player, e);
						} else {
							e.setCancelled(true);
							cancel(player, e);
						}
					} else {
						e.setCancelled(true);
						cancel(player, e);
					}
					
				}
			}

		};
		
	}

}

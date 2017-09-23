package com.aymegike.huminekingdom.event;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.aymegike.huminekingdom.HumineKingdom;

import net.md_5.bungee.api.ChatColor;

public class PlayerDie implements Listener {
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){
		
		/********************************************
		 *      Gain/Perte de gloire                *
		 ********************************************/
		Player p = e.getEntity();
		Player killer = e.getEntity().getKiller();
		if(p.getInventory().contains(new ItemStack(Material.DRAGON_EGG))){
			for(ItemStack it : e.getDrops()){
				if(it.getType() == Material.DRAGON_EGG){
					it.setType(Material.AIR);
					for(Player op : Bukkit.getOnlinePlayers()){
						op.sendMessage(p.getName()+ChatColor.DARK_PURPLE+" est mort et a perdu l'oeuf.");
						op.playSound(op.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
						
					}
				}
			}
			
			PlaceBlock.placeDragon(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()), p, false);
		}
		
		if(!(killer instanceof Player)){
			if(HumineKingdom.getPlayerkingdom(p) != null){
				HumineKingdom.getPlayerkingdom(p).decreaseGlory(p, 25, true);
			}else
				HumineKingdom.setPlayerGlory(p, HumineKingdom.getPlayerGlory(p) - 25);
		}else{
			
			if(HumineKingdom.getPlayerkingdom(p) != null){
				
				HumineKingdom.getPlayerkingdom(p).decreaseGlory(p, 35, true);
				
			}else{
				
				HumineKingdom.setPlayerGlory(p, HumineKingdom.getPlayerGlory(p) - 35);
					
			}
			
			if(HumineKingdom.getPlayerkingdom(killer) != null){
				
				HumineKingdom.getPlayerkingdom(killer).increaseGlory(killer, 35, true);
				
			}else{
				
				HumineKingdom.setPlayerGlory(killer, HumineKingdom.getPlayerGlory(killer)+35);
				
			}
			
		}
		
//		if (!(killer instanceof Player)) { //if it's PVE
//			
//		}else if(HumineKingdom.getPlayerkingdom(p) != null && HumineKingdom.getPlayerkingdom(killer) != null){
//			
//			if (HumineKingdom.getPlayerkingdom(killer).getName().equalsIgnoreCase(HumineKingdom.getPlayerkingdom(p).getName())) {
//				HumineKingdom.getPlayerkingdom(p).decreaseGlory(p, 35, true);		
//			}
//			else {
//				HumineKingdom.getPlayerkingdom(p).decreaseGlory(p, 35, true);	
//				HumineKingdom.getPlayerkingdom(killer).increaseGlory(killer, 35, true);
//			}
//		}else if(HumineKingdom.getPlayerkingdom(killer) != null && HumineKingdom.getPlayerkingdom(p) == null){
//			
//			HumineKingdom.setPlayerGlory(p, HumineKingdom.getPlayerGlory(p) - 35);
//			HumineKingdom.getPlayerkingdom(killer).increaseGlory(p, 35, true);
//			
//		}else if(HumineKingdom.getPlayerkingdom(killer) == null && HumineKingdom.getPlayerkingdom(p) != null){
//			
//			HumineKingdom.setPlayerGlory(killer, HumineKingdom.getPlayerGlory(killer) - 35);
//			HumineKingdom.getPlayerkingdom(p).increaseGlory(p, 35, true);
//			
//		}
	}
}

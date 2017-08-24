package com.aymegike.bvn.event;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.aymegike.bvn.Main;
import com.aymegike.bvn.utils.PlayerJoinManager;
import com.aymegike.bvn.utils.Title;

import net.md_5.bungee.api.ChatColor;

public class PlayerFall implements Listener{
	
	static ArrayList<Player> falling = new ArrayList<>(); 
	
	@EventHandler
	public void onPlayerFall(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player player = (Player) e.getEntity();
			if(falling.contains(player)){
				if(e.getCause() == DamageCause.FALL){
					e.setCancelled(true);
					player.getLocation().getWorld().createExplosion(player.getLocation(), 5);
					falling.remove(player);
					for(Player pls : Bukkit.getOnlinePlayers()){			
						pls.playSound(pls.getLocation(), Sound.AMBIENT_CAVE, 5, 5);
					}
					
					player.playSound(player.getLocation(), Sound.RECORD_13, 500, 1);
	            	Title.sendTitle(player, ChatColor.DARK_PURPLE+"Humine"+ChatColor.WHITE+"Craft", ChatColor.DARK_PURPLE+"Bienvenue "+player.getName());
        			Bukkit.broadcastMessage(ChatColor.DARK_PURPLE+"Bienvenue "+ChatColor.WHITE+player.getName()+ChatColor.DARK_PURPLE+" sur "+ChatColor.WHITE+"HumineCraft "+ChatColor.DARK_PURPLE+"!");
        			player.getInventory().addItem(Main.getBook());
        			removeFall(player);
	            	PlayerJoinManager.finalisePlayerConnection(player);
				}
			}
		}
	}
	
	public static void addFalling(Player player){
		falling.add(player);
	}
	
	public static boolean containeFall(Player player){
		if(falling.contains(player)){
			return true;
		}else{
			return false;
		}
		
	}

	public static void removeFall(Player player) {
		falling.remove(player);
		
	}

}

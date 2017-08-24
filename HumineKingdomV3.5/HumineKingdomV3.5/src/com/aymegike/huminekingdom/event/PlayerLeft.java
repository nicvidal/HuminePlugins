package com.aymegike.huminekingdom.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.GradeManager;

import net.md_5.bungee.api.ChatColor;

public class PlayerLeft implements Listener {
	
	@EventHandler
	public void onPlayerLeft(PlayerQuitEvent e){
		
		Player p = e.getPlayer();
		GradeManager.removePlayer(p);
		if(p.getInventory().contains(new ItemStack(Material.DRAGON_EGG))){
			
			p.getInventory().remove(Material.DRAGON_EGG);
			PlaceBlock.placeDragon(new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ()), p);
			
			if(!HumineKingdom.getPlayerkingdom(p).haveDragonEgg()){
				HumineKingdom.getPlayerkingdom(p).setDragonEgg(true);
				Bukkit.broadcastMessage(ChatColor.DARK_PURPLE+"L'oeuf de dragon a trouvé de nouveaux maitres ! Gloire a "+ChatColor.WHITE+HumineKingdom.getPlayerkingdom(p).getName()+ChatColor.DARK_PURPLE+" !");
				for(Player pls : Bukkit.getOnlinePlayers()) pls.playSound(pls.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 5, 5);
			}else{
				for(OfflinePlayer op : HumineKingdom.getPlayerkingdom(p).getAllMember()){
					if(op.isOnline()){
						op.getPlayer().sendMessage(ChatColor.WHITE+p.getName()+ChatColor.DARK_PURPLE+" vien de replacer l'oeuf");
						op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.ENTITY_ENDERDRAGON_HURT, 5, 1);
					}
				}
			}
			
		}
		
		
	}

}

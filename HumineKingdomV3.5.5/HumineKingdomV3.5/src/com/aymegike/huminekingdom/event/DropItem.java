package com.aymegike.huminekingdom.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import com.aymegike.huminekingdom.HumineKingdom;

import net.md_5.bungee.api.ChatColor;

public class DropItem implements Listener{
	
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent e){
		Player p = e.getPlayer();
		
		if(HumineKingdom.getPlayerkingdom(p) != null){
			if(e.getItemDrop().getItemStack().getType() == Material.DRAGON_EGG){
				
				e.getItemDrop().setPickupDelay(100);
				e.getItemDrop().setGlowing(true);
				e.getItemDrop().setInvulnerable(true);
				e.getItemDrop().setPortalCooldown(100);
            	HumineKingdom.getPlayerkingdom(p).increaseGlory(null, 500, false);
				
				if(!HumineKingdom.getPlayerkingdom(p).haveDragonEgg()){
					HumineKingdom.getPlayerkingdom(p).setDragonEgg(true);
					Bukkit.broadcastMessage(ChatColor.DARK_PURPLE+"L'oeuf de dragon a trouvé de nouveaux maitres ! Gloire a "+ChatColor.WHITE+HumineKingdom.getPlayerkingdom(p).getName()+ChatColor.DARK_PURPLE+" !");
					for(Player pls : Bukkit.getOnlinePlayers()) pls.playSound(pls.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 5, 5);
				}else{
					for(OfflinePlayer op : HumineKingdom.getPlayerkingdom(p).getAllMember()){
						if(op.isOnline()){
							op.getPlayer().sendMessage(ChatColor.WHITE+p.getName()+ChatColor.DARK_PURPLE+" vient de replacer l'oeuf");
							op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.ENTITY_ENDERDRAGON_HURT, 5, 1);
						}
					}
				}
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("HumineKingdom"), new Runnable(){
		            @Override
					public void run(){
		            	PlaceBlock.placeDragon(new Location(e.getItemDrop().getWorld(), e.getItemDrop().getLocation().getBlockX(), e.getItemDrop().getLocation().getBlockY(), e.getItemDrop().getLocation().getBlockZ()), p, false);
		            	e.getItemDrop().setInvulnerable(false);
		            	e.getItemDrop().setItemStack(new ItemStack(Material.AIR));
		            }
		        }, 20);
			
			
			}
		}
	}

}

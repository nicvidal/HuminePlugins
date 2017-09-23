package com.aymegike.bvn.utils.objets;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.aymegike.bvn.Main;
import com.aymegike.bvn.event.PlayerFall;
import com.aymegike.bvn.utils.PlayerJoinManager;
import com.aymegike.bvn.utils.Title;

public class Fall {
	
	Player player;
	int task;
	
	public Fall(Player player){
		this.player = player;
		clock();
	}
	
	private void clock(){

        player.getLocation().getWorld().strikeLightning(player.getLocation().add(0, 10, 0));	
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("WelcomeOnHumineCraft"), new Runnable(){

			@Override
			public void run() {
				if(PlayerFall.containeFall(player) && player.isOnline()){
					if(player.getLocation().getBlock().getType().name().equalsIgnoreCase("STATIONARY_WATER")){
						
						player.getLocation().getWorld().createExplosion(player.getLocation(), 5);
						PlayerFall.removeFall(player);
						for(Player pls : Bukkit.getOnlinePlayers()){			
							pls.playSound(pls.getLocation(), Sound.AMBIENT_CAVE, 5, 5);
						}
						
						player.playSound(player.getLocation(), Sound.RECORD_13, 500, 1);
		            	Title.sendTitle(player, ChatColor.DARK_PURPLE+"Humine"+ChatColor.WHITE+"Craft", ChatColor.DARK_PURPLE+"Bienvenue "+player.getName());
	        			Bukkit.broadcastMessage(ChatColor.DARK_PURPLE+"Bienvenue "+ChatColor.WHITE+player.getName()+ChatColor.DARK_PURPLE+" sur "+ChatColor.WHITE+"HumineCraft "+ChatColor.DARK_PURPLE+"!");
	        			player.getInventory().addItem(Main.getBook());
		            	PlayerJoinManager.finalisePlayerConnection(player);
					}
					
				}else{
					Bukkit.getScheduler().cancelTask(task);
				}
			}
			
		}, 20, 1);	
		
	}

}

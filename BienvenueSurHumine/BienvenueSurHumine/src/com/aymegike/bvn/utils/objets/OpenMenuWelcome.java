package com.aymegike.bvn.utils.objets;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.aymegike.bvn.utils.MenuPlayer;
import com.aymegike.bvn.utils.MenuUtils;



public class OpenMenuWelcome {
	
	private int task;
	
	public OpenMenuWelcome(Player player){
		
		MenuPlayer.playerOpenWelcomeMenu(player);
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("WelcomeOnHumineCraft"), new Runnable(){
			
			@Override
			public void run() {
				if(player.isOnline()){
					if(MenuPlayer.welcomeMenuIsOpen(player))
						MenuUtils.Welcome(player);
					else
						Bukkit.getScheduler().cancelTask(task);
				}else{
					Bukkit.getScheduler().cancelTask(task);
				}
				
			}
			
		}, 0, 5);	
	}

}

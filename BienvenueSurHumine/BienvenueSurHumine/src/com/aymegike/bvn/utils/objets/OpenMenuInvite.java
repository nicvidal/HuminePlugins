package com.aymegike.bvn.utils.objets;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.aymegike.bvn.utils.MenuPlayer;
import com.aymegike.bvn.utils.MenuUtils;

public class OpenMenuInvite {
	
	private static int task;
	private String name;
	
	public OpenMenuInvite(Player player, Player sender){
		
		name = player.getName();
		
		MenuPlayer.playerOpenInviteMenu(player);
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("WelcomeOnHumineCraft"), new Runnable(){
			
			@Override
			public void run() {
				if(player.isOnline()){
					if(MenuPlayer.inviteMenuIsOpen(player) && sender.isOnline()){
						MenuUtils.openMenuJoin(sender, player).openForPlayer(player);
					}else{
						player.closeInventory();
						MenuPlayer.playerCloseInviteMenu(player);
						Bukkit.getScheduler().cancelTask(task);
					}
				}else{
					Bukkit.getScheduler().cancelTask(task);
					sender.sendMessage(ChatColor.WHITE+name+ChatColor.RED+" c'est déconecté(e).");
					new OpenMenuWelcome(sender);
				}
			}
			
		}, 0, 5);	
		
	}

}

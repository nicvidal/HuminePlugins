package com.aymegike.huminekingdom.event;

import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import com.aymegike.huminekingdom.HumineKingdom;

import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("deprecation")
public class KingdomChat implements Listener{
	
	@EventHandler
	public void onPLayerChat(PlayerChatEvent e){
		Player p = e.getPlayer();
		if(HumineKingdom.getPlayerkingdom(p) != null){
			if(p.hasPermission("kingdom.chat")){
				String msg = e.getMessage();
				String[] args = msg.split("");
				if(args[0].equalsIgnoreCase("*")){
					for(OfflinePlayer op : HumineKingdom.getPlayerkingdom(p).getAllMember()){
						if(op.isOnline()){
							if(op.getPlayer().hasPermission("kingdom.chat")){
								e.setCancelled(true);
								op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
								op.getPlayer().sendMessage(ChatColor.BLACK+"["+ChatColor.DARK_PURPLE+HumineKingdom.getPlayerkingdom(p).getName()+ChatColor.BLACK+"-"+ChatColor.DARK_PURPLE+HumineKingdom.getPlayerGrade(p).getName()+ChatColor.BLACK+"] "+ChatColor.WHITE+p.getName()+ChatColor.WHITE+": "+ChatColor.GRAY+msg.replace("*", ""));								
							}
						}
					}
					System.out.println("["+HumineKingdom.getPlayerkingdom(p).getName()+"-"+HumineKingdom.getPlayerGrade(p).getName()+"] "+p.getName()+": "+msg.replace("*", ""));
					
				}
			}
		}
		
	}

}
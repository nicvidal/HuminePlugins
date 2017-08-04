package com.aymegike.bvn.event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.aymegike.bvn.utils.MenuPlayer;
import com.aymegike.bvn.utils.objets.OpenMenuWelcome;


public class MenuManager implements Listener{
	
	@EventHandler
	public void onPlayerUseMenu(InventoryClickEvent e){
		
		if(e.getWhoClicked() instanceof Player){
		
			Player p = (Player) e.getWhoClicked();
			
			if(e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta()){             
			  return;
			}
			  
			//NO KINGDOM
			if(e.getInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_PURPLE+"Bienvenue "+ChatColor.WHITE+p.getName()+ChatColor.DARK_PURPLE+" sur "+ChatColor.WHITE+" HumineCraft")){
				e.setCancelled(true);
				if(e.getCurrentItem().getType() == Material.IRON_SWORD){
					p.sendMessage(ChatColor.DARK_PURPLE+"Vous avez choisi de commencer seul...");
					MenuPlayer.playerCloseWelcomeMenu(p);
					p.closeInventory();
				}
				if(e.getCurrentItem().getType() == Material.TOTEM){
					p.sendMessage(ChatColor.DARK_PURPLE+"Indiquer le pseudo du joueur que vous souhaitez rejoindre.");
					MenuPlayer.playerCloseWelcomeMenu(p);
					p.closeInventory();
					PlayerChat.addSendInvite(p);
				}
			}
			
			if(e.getInventory().getTitle().contains(ChatColor.DARK_PURPLE+" souhaite ce joindre a toi !")){
				e.setCancelled(true);
				if(e.getCurrentItem().getType() == Material.EMERALD_BLOCK){
					Player sender = Bukkit.getPlayer(e.getInventory().getTitle().replace(ChatColor.DARK_PURPLE+" souhaite ce joindre a toi !", ""));
					sender.sendMessage(ChatColor.GREEN+"Votre demande a était accepté !");
					p.sendMessage(ChatColor.GREEN+"Vous avez accépté la demande de "+ChatColor.WHITE+sender.getName()+ChatColor.DARK_PURPLE+".");
					MenuPlayer.playerCloseInviteMenu(p);
					Bukkit.dispatchCommand(sender, "next 3");
				}
				
				if(e.getCurrentItem().getType() == Material.REDSTONE_BLOCK){
					Player sender = Bukkit.getPlayer(e.getInventory().getTitle().replace(ChatColor.DARK_PURPLE+" souhaite ce joindre a toi !", ""));
					sender.sendMessage(ChatColor.RED+"Votre demande a était refusé !");
					Bukkit.dispatchCommand(sender, "next 1");
					p.sendMessage(ChatColor.RED+"Vous avez refusé la demande de "+ChatColor.WHITE+sender.getName()+ChatColor.GREEN+".");			
					MenuPlayer.playerCloseInviteMenu(p);
					new OpenMenuWelcome(sender);
					
				}
			}
			
		}
		
	}

}

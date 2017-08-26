package com.aymegike.bvn.event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.aymegike.bvn.utils.MenuPlayer;
import com.aymegike.bvn.utils.PlayerJoinManager;
import com.aymegike.bvn.utils.objets.Preview;

import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;


public class MenuManager implements Listener{
	
	@EventHandler
	public void onPlayerUseMenu(InventoryClickEvent e){
		
		if(e.getWhoClicked() instanceof Player){
		
			Player p = (Player) e.getWhoClicked();
			
			if(e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta()){             
			  return;
			}
			
			if(e.getInventory().getTitle().contains(ChatColor.DARK_PURPLE+" souhaite se joindre a toi !")){
				e.setCancelled(true);
				if(e.getCurrentItem().getType() == Material.EMERALD_BLOCK){
					Player sender = Bukkit.getPlayer(e.getInventory().getTitle().replace(ChatColor.DARK_PURPLE+" souhaite se joindre a toi !", ""));
					sender.sendMessage(ChatColor.GREEN+"Votre demande a été acceptée !");
					p.sendMessage(ChatColor.GREEN+"Vous avez accepté la demande de "+ChatColor.WHITE+sender.getName()+ChatColor.DARK_PURPLE+".");
					p.sendMessage(ChatColor.DARK_PURPLE+"Vous feriez mieux de vous diriger à un endroit hors de danger pour pouvoir accueillir votre nouvel ami !");
					MenuPlayer.playerCloseInviteMenu(p);
					Preview pv = new Preview(sender, p);
					PlayerJoinManager.pvs.add(pv);
					Bukkit.dispatchCommand(sender, "next 7");
				}
				
				if(e.getCurrentItem().getType() == Material.REDSTONE_BLOCK){
					Player sender = Bukkit.getPlayer(e.getInventory().getTitle().replace(ChatColor.DARK_PURPLE+" souhaite se joindre a toi !", ""));
					sender.sendMessage(ChatColor.RED+"Votre demande a été refusée !");
					p.sendMessage(ChatColor.RED+"Vous avez refusé la demande de "+ChatColor.WHITE+sender.getName()+ChatColor.GREEN+".");			
					MenuPlayer.playerCloseInviteMenu(p);
					PlayerJoinManager.stap3.remove(sender);
					PlayerJoinManager.stap2.add(sender);
					sender.playSound(sender.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
					sender.sendMessage(ChatColor.BLACK+"-------------------------------------");
					PacketPlayOutChat chat = new PacketPlayOutChat(ChatSerializer.a("[\"\",{\"text\":\"Souhaitez-vous directement rejoindre une personne dans son aventure ?\",\"color\":\"yellow\",\"bold\":true,\"underlined\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Vous pouvez soit commencer seul, \",\"color\":\"light_purple\"},{\"text\":\"ou bien rejoindre un(e) ami(e) qui \",\"color\":\"light_purple\"},{\"text\":\"est connecté(e).\",\"color\":\"light_purple\"}]}}},{\"text\":\" \\n[\",\"color\":\"gray\",\"bold\":false,\"underlined\":false},{\"text\":\"oui\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 2\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"J'ai bien un(e) ami(e) que je pourais rejoindre !\",\"color\":\"light_purple\"}]}}},{\"text\":\"] [\",\"color\":\"gray\"},{\"text\":\"non\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 3\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Non les ami(e)s c'est pour les faibles !\",\"color\":\"light_purple\"}]}}},{\"text\":\"]\",\"color\":\"gray\"}]"));
					((CraftPlayer) sender).getHandle().playerConnection.sendPacket(chat);
					
				}
			}
			
		}
		
	}

}

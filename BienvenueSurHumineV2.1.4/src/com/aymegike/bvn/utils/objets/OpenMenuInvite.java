package com.aymegike.bvn.utils.objets;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.aymegike.bvn.utils.MenuPlayer;
import com.aymegike.bvn.utils.MenuUtils;
import com.aymegike.bvn.utils.PlayerJoinManager;

import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;

public class OpenMenuInvite {
	
	private static int task;
	private String name;
	
	public OpenMenuInvite(Player player, Player sender){
		
		name = player.getName();
		
		MenuPlayer.playerOpenInviteMenu(player);
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("WelcomeOnHumineCraft"), new Runnable(){
			int chrono = 0;
			@Override
			public void run() {
				chrono++;
				if(player.isOnline()){
					if(MenuPlayer.inviteMenuIsOpen(player)){
						if(sender.isOnline()){
							MenuUtils.openMenuJoin(sender, player, chrono).openForPlayer(player);
						}else{
							player.closeInventory();
							MenuPlayer.playerCloseInviteMenu(player);
							player.sendMessage(ChatColor.WHITE+sender.getName()+ChatColor.RED+" s'est déconnecté(e).");
							Bukkit.getScheduler().cancelTask(task);
						}
					}else{
						player.closeInventory();
						MenuPlayer.playerCloseInviteMenu(player);
						Bukkit.getScheduler().cancelTask(task);
					}
				}else{
					MenuPlayer.playerCloseInviteMenu(player);
					PlayerJoinManager.stap3.remove(sender);
					PlayerJoinManager.stap2.add(sender);
					sender.playSound(sender.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
					sender.sendMessage(ChatColor.WHITE+name+ChatColor.RED+" s'est déconnecté(e).");
					sender.sendMessage(ChatColor.BLACK+"-------------------------------------");
					PacketPlayOutChat chat = new PacketPlayOutChat(ChatSerializer.a("[\"\",{\"text\":\"Souhaitez-vous directement rejoindre une personne dans son aventure ?\",\"color\":\"yellow\",\"bold\":true,\"underlined\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Vous pouvez soit commencer seul, \",\"color\":\"light_purple\"},{\"text\":\"ou bien rejoindre un(e) ami(e) qui \",\"color\":\"light_purple\"},{\"text\":\"est connecté(e).\",\"color\":\"light_purple\"}]}}},{\"text\":\" \\n[\",\"color\":\"gray\",\"bold\":false,\"underlined\":false},{\"text\":\"oui\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 2\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"J'ai bien un(e) ami(e) que je pourait rejoindre !\",\"color\":\"light_purple\"}]}}},{\"text\":\"] [\",\"color\":\"gray\"},{\"text\":\"non\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 3\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Non les ami(e)s c'est pour les faibles !\",\"color\":\"light_purple\"}]}}},{\"text\":\"]\",\"color\":\"gray\"}]"));
					((CraftPlayer) sender).getHandle().playerConnection.sendPacket(chat);
					Bukkit.getScheduler().cancelTask(task);
				}
				if(chrono >= 60){
					player.closeInventory();
					MenuPlayer.playerCloseInviteMenu(player);
					Bukkit.getScheduler().cancelTask(task);
					sender.sendMessage(ChatColor.WHITE+name+ChatColor.RED+" est AFK.");
				}
			}
			
		}, 20, 20);	
		
	}

}

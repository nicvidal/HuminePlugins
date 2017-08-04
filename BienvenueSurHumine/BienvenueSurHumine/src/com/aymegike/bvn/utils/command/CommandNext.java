package com.aymegike.bvn.utils.command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.aymegike.bvn.event.PlayerChat;

import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;

public class CommandNext implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] arg) {
		
		if(sender instanceof Player){
			Player p = (Player) sender;
			if(lbl.equalsIgnoreCase("next")){
				
				if(arg[0].equalsIgnoreCase("1")){
					p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
					p.sendMessage(ChatColor.BLACK+"-------------------------------------");
					PacketPlayOutChat chat = new PacketPlayOutChat(ChatSerializer.a("[\"\",{\"text\":\"Souhaitez-vous directement rejoindre une personne dans son aventure ?\",\"color\":\"yellow\",\"bold\":true,\"underlined\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Vous pouvez soit commencer seul, \",\"color\":\"light_purple\"},{\"text\":\"ou bien rejoindre un(e) ami(e) qui \",\"color\":\"light_purple\"},{\"text\":\"est connecté(e).\",\"color\":\"light_purple\"}]}}},{\"text\":\" \\n[\",\"color\":\"gray\",\"bold\":false,\"underlined\":false},{\"text\":\"oui\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 2\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"J'ai bien un(e) ami(e) que je pourait rejoindre !\",\"color\":\"light_purple\"}]}}},{\"text\":\"] [\",\"color\":\"gray\"},{\"text\":\"non\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 3\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Non les ami(e)s c'est pour les faibles !\",\"color\":\"light_purple\"}]}}},{\"text\":\"]\",\"color\":\"gray\"}]"));
					((CraftPlayer) p).getHandle().playerConnection.sendPacket(chat);
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("2")){
					//JOIN FRIENDS
					p.sendMessage(ChatColor.BLACK+"-------------------------------------");
					p.sendMessage(ChatColor.DARK_PURPLE+"Vous pouvez écrire le nom de votre "+ChatColor.WHITE+" ami(e) "+ChatColor.DARK_PURPLE+"ici.");
					PlayerChat.addSendInvite(p);
					return true;
				}
					
				if(arg[0].equalsIgnoreCase("3")){
					//SOLO
					p.sendMessage(ChatColor.BLACK+"-------------------------------------");
					p.sendMessage(ChatColor.DARK_PURPLE+"Bien !");
					new Cinematique(p);
					PlayerChat.removeSendInvite(p);
					return true;					
				}
				
			}
		}else{
			System.out.println("Vous ne pouvez pas executer cette commande depuis la console !");
		}
		
		
		return true;
	}

}

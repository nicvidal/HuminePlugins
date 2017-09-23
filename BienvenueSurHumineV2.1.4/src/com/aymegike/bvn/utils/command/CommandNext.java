package com.aymegike.bvn.utils.command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.aymegike.bvn.event.PlayerChat;
import com.aymegike.bvn.utils.PlayerJoinManager;
import com.aymegike.bvn.utils.objets.Cinematique;
import com.aymegike.bvn.utils.objets.PositionSpawn;
import com.aymegike.bvn.utils.objets.Preview;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;

public class CommandNext implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] arg) {
		
		if(sender instanceof Player){
			Player p = (Player) sender;
			if(lbl.equalsIgnoreCase("next")){
				
				if(arg[0].equalsIgnoreCase("1")){
					if(PlayerJoinManager.stap1.contains(p)){
						PlayerJoinManager.stap1.remove(p);
						PlayerJoinManager.stap2.add(p);
						p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
						p.sendMessage(ChatColor.BLACK+"-------------------------------------");
						PacketPlayOutChat chat = new PacketPlayOutChat(ChatSerializer.a("[\"\",{\"text\":\"Souhaitez-vous directement rejoindre une personne dans son aventure ?\",\"color\":\"yellow\",\"bold\":true,\"underlined\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Vous pouvez soit commencer seul, \",\"color\":\"light_purple\"},{\"text\":\"ou bien rejoindre un(e) ami(e) qui \",\"color\":\"light_purple\"},{\"text\":\"est connecté(e).\",\"color\":\"light_purple\"}]}}},{\"text\":\" \\n[\",\"color\":\"gray\",\"bold\":false,\"underlined\":false},{\"text\":\"oui\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 2\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"J'ai bien un(e) ami(e) que je pourrais rejoindre.\",\"color\":\"light_purple\"}]}}},{\"text\":\"] [\",\"color\":\"gray\"},{\"text\":\"non\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 3\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Non les ami(e)s c'est pour les faibles !\",\"color\":\"light_purple\"}]}}},{\"text\":\"]\",\"color\":\"gray\"}]"));
						((CraftPlayer) p).getHandle().playerConnection.sendPacket(chat);
						return true;
					}
				}
				
				if(arg[0].equalsIgnoreCase("2")){
					//JOIN FRIENDS
					if(PlayerJoinManager.stap2.contains(p)){
						PlayerJoinManager.stap2.remove(p);
						PlayerJoinManager.stap3.add(p);
						p.sendMessage(ChatColor.BLACK+"-------------------------------------");
						p.sendMessage(ChatColor.DARK_PURPLE+"Vous pouvez écrire le nom de votre "+ChatColor.WHITE+"ami(e) "+ChatColor.DARK_PURPLE+"ici.");
						PlayerChat.addSendInvite(p);
						return true;
					}
				}
					
				if(arg[0].equalsIgnoreCase("3")){
					//SOLO
					if(PlayerJoinManager.stap2.contains(p)){
						PlayerJoinManager.stap2.remove(p);
						PlayerJoinManager.stap3.add(p);
						p.sendMessage(ChatColor.BLACK+"-------------------------------------");
						p.sendMessage(ChatColor.DARK_PURPLE+"Bien !");
						PositionSpawn position = null;
						for(PositionSpawn ps : PlayerJoinManager.ps){
							if(ps.getPlayer() == p){
								ps.stop(false);
								position = ps;
							}
						}
						PlayerJoinManager.ps.remove(position);
						PlayerChat.removeSendInvite(p);
						return true;
					}
				}
				
				if(arg[0].equalsIgnoreCase("4")){
					for(Preview pv : PlayerJoinManager.pvs){
						if(pv.getPlayer() == p){
							pv.stop();
						}
					}
					p.getInventory().setHelmet(null);
					PositionSpawn ps = new PositionSpawn(p);
					PlayerJoinManager.ps.add(ps);
					for(int i = 0 ; i< 150 ; i++)
						p.sendMessage("");
					PacketPlayOutChat chat = new PacketPlayOutChat(ChatSerializer.a("[\"\",{\"text\":\"Maintenant que tout est prêt, cliquez sur ce texte lorsque vous serez prêt à démarrer.\",\"color\":\"dark_purple\",\"bold\":true,\"underlined\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 6\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Pensez à bien activer le son du jeu avant de commencer.\",\"color\":\"light_purple\"}]}}}]"));
					((CraftPlayer) p).getHandle().playerConnection.sendPacket(chat);
					return true;
				}
					
				if(arg[0].equalsIgnoreCase("5")){
					for(Preview pv : PlayerJoinManager.pvs){
						if(pv.getPlayer() == p){
							pv.nextView();
							p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
						}
					}
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("6")){
					PositionSpawn position = null;
					for(PositionSpawn ps : PlayerJoinManager.ps){
						if(ps.getPlayer() == p){
							ps.stop(true);
							position = ps;
						}
					}
					PlayerJoinManager.ps.remove(position);
					new Cinematique(p);
					return true;
				}
				
				if(arg[0].equalsIgnoreCase("7")){
					p.getInventory().setHelmet(null);
					PositionSpawn ps = new PositionSpawn(p);
					PlayerJoinManager.ps.add(ps);
					for(int i = 0 ; i< 150 ; i++)
						p.sendMessage("");
					PacketPlayOutChat chat = new PacketPlayOutChat(ChatSerializer.a("[\"\",{\"text\":\"Maintenant que tout est prêt, cliquez sur ce texte lorsque vous serez prêt à démarrer.\",\"color\":\"dark_purple\",\"bold\":true,\"underlined\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 6\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Pensez à bien activer le son du jeu avant de commencer.\",\"color\":\"light_purple\"}]}}}]"));
					((CraftPlayer) p).getHandle().playerConnection.sendPacket(chat);
					return true;
				}
				
			}
		}else{
			System.out.println("Vous ne pouvez pas executer cette commande depuis la console !");
		}
		
		
		return true;
	}

}

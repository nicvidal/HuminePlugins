package com.aymegike.bvn.event;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import com.aymegike.bvn.utils.MenuPlayer;
import com.aymegike.bvn.utils.PlayerJoinManager;
import com.aymegike.bvn.utils.objets.OpenMenuInvite;
import com.aymegike.huminekingdom.HumineKingdom;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;

@SuppressWarnings("deprecation")
public class PlayerChat implements Listener{
	
	private static ArrayList<Player> stopchat = new ArrayList<>();
	private static ArrayList<Player> sendInvite = new ArrayList<>();
	
	@EventHandler
	public void onPlayerChatEvents(PlayerChatEvent e){
		
		Player player = e.getPlayer();
		String msg = e.getMessage();
		if(stopchat.contains(player)){
			e.setCancelled(true);
		}
		
		if(sendInvite.contains(player)){
			
			Player target = Bukkit.getPlayer(msg);
			
			if(target == null){
				player.sendMessage(ChatColor.WHITE+msg+ChatColor.DARK_PURPLE+" n'est pas connecté(e).");
				player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
				PlayerJoinManager.stap3.remove(player);
				PlayerJoinManager.stap2.add(player);
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
				player.sendMessage(ChatColor.BLACK+"-------------------------------------");
				PacketPlayOutChat chat = new PacketPlayOutChat(ChatSerializer.a("[\"\",{\"text\":\"Souhaitez-vous directement rejoindre une personne dans son aventure ?\",\"color\":\"yellow\",\"bold\":true,\"underlined\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Vous pouvez soit commencer seul, \",\"color\":\"light_purple\"},{\"text\":\"ou bien rejoindre un(e) ami(e) qui \",\"color\":\"light_purple\"},{\"text\":\"est connecté(e).\",\"color\":\"light_purple\"}]}}},{\"text\":\" \\n[\",\"color\":\"gray\",\"bold\":false,\"underlined\":false},{\"text\":\"oui\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 2\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"J'ai bien un(e) ami(e) que je pourrais rejoindre.\",\"color\":\"light_purple\"}]}}},{\"text\":\"] [\",\"color\":\"gray\"},{\"text\":\"non\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 3\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Non les ami(e)s c'est pour les faibles !\",\"color\":\"light_purple\"}]}}},{\"text\":\"]\",\"color\":\"gray\"}]"));
				((CraftPlayer) player).getHandle().playerConnection.sendPacket(chat);
				removeSendInvite(player);
				return;
			}
			
			if(target.isOnline()){
				if(stopchat.contains(target)){
					player.sendMessage(ChatColor.WHITE+msg+ChatColor.DARK_PURPLE+" n'a pas encore commencé de partie sur ce serveur.");
					player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					PlayerJoinManager.stap3.remove(player);
					PlayerJoinManager.stap2.add(player);
					player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
					player.sendMessage(ChatColor.BLACK+"-------------------------------------");
					PacketPlayOutChat chat = new PacketPlayOutChat(ChatSerializer.a("[\"\",{\"text\":\"Souhaitez-vous directement rejoindre une personne dans son aventure ?\",\"color\":\"yellow\",\"bold\":true,\"underlined\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Vous pouvez soit commencer seul, \",\"color\":\"light_purple\"},{\"text\":\"ou bien rejoindre un(e) ami(e) qui \",\"color\":\"light_purple\"},{\"text\":\"est connecté(e).\",\"color\":\"light_purple\"}]}}},{\"text\":\" \\n[\",\"color\":\"gray\",\"bold\":false,\"underlined\":false},{\"text\":\"oui\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 2\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"J'ai bien un(e) ami(e) que je pourrais rejoindre.\",\"color\":\"light_purple\"}]}}},{\"text\":\"] [\",\"color\":\"gray\"},{\"text\":\"non\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 3\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Non les ami(e)s c'est pour les faibles !\",\"color\":\"light_purple\"}]}}},{\"text\":\"]\",\"color\":\"gray\"}]"));
					((CraftPlayer) player).getHandle().playerConnection.sendPacket(chat);
					removeSendInvite(player);
					return;
				}
				if(MenuPlayer.inviteMenuIsOpen(target)){
					player.sendMessage(ChatColor.WHITE+msg+ChatColor.DARK_PURPLE+" est occupé(e) pour l'instant.");
					player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					PlayerJoinManager.stap3.remove(player);
					PlayerJoinManager.stap2.add(player);
					player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
					player.sendMessage(ChatColor.BLACK+"-------------------------------------");
					PacketPlayOutChat chat = new PacketPlayOutChat(ChatSerializer.a("[\"\",{\"text\":\"Souhaitez-vous directement rejoindre une personne dans son aventure ?\",\"color\":\"yellow\",\"bold\":true,\"underlined\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Vous pouvez soit commencer seul, \",\"color\":\"light_purple\"},{\"text\":\"ou bien rejoindre un(e) ami(e) qui \",\"color\":\"light_purple\"},{\"text\":\"est connecté(e).\",\"color\":\"light_purple\"}]}}},{\"text\":\" \\n[\",\"color\":\"gray\",\"bold\":false,\"underlined\":false},{\"text\":\"oui\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 2\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"J'ai bien un(e) ami(e) que je pourrais rejoindre.\",\"color\":\"light_purple\"}]}}},{\"text\":\"] [\",\"color\":\"gray\"},{\"text\":\"non\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 3\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Non les ami(e)s c'est pour les faibles !\",\"color\":\"light_purple\"}]}}},{\"text\":\"]\",\"color\":\"gray\"}]"));
					((CraftPlayer) player).getHandle().playerConnection.sendPacket(chat);
					removeSendInvite(player);
					return;
				}
				if(Bukkit.getServer().getWorlds().get(0) != target.getLocation().getWorld()){
					player.sendMessage(ChatColor.WHITE+msg+ChatColor.DARK_PURPLE+" est impossible à géolocaliser.");
					player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					PlayerJoinManager.stap3.remove(player);
					PlayerJoinManager.stap2.add(player);
					player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
					player.sendMessage(ChatColor.BLACK+"-------------------------------------");
					PacketPlayOutChat chat = new PacketPlayOutChat(ChatSerializer.a("[\"\",{\"text\":\"Souhaitez-vous directement rejoindre une personne dans son aventure ?\",\"color\":\"yellow\",\"bold\":true,\"underlined\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Vous pouvez soit commencer seul, \",\"color\":\"light_purple\"},{\"text\":\"ou bien rejoindre un(e) ami(e) qui \",\"color\":\"light_purple\"},{\"text\":\"est connecté(e).\",\"color\":\"light_purple\"}]}}},{\"text\":\" \\n[\",\"color\":\"gray\",\"bold\":false,\"underlined\":false},{\"text\":\"oui\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 2\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"J'ai bien un(e) ami(e) que je pourrais rejoindre.\",\"color\":\"light_purple\"}]}}},{\"text\":\"] [\",\"color\":\"gray\"},{\"text\":\"non\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 3\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Non les ami(e)s c'est pour les faibles !\",\"color\":\"light_purple\"}]}}},{\"text\":\"]\",\"color\":\"gray\"}]"));
					((CraftPlayer) player).getHandle().playerConnection.sendPacket(chat);
					removeSendInvite(player);
					return;
				}
				if(HumineKingdom.getPlayerkingdom(target) != null){
					if(!target.hasPermission("kingdom.gestion.invite.newplayer")){
						player.sendMessage(ChatColor.WHITE+msg+ChatColor.DARK_PURPLE+" n'a pas les droit pour vous accepter.");
						player.sendMessage(ChatColor.YELLOW+"Voici la liste des joueurs connectés de son royaume qui peuvent vous accepter:");
						int i = 0;
						for(OfflinePlayer pls : HumineKingdom.getPlayerkingdom(target).getAllMember()){
							if(pls.isOnline() && pls.getPlayer().hasPermission("kingdom.gestion.invite.newplayer")){
								player.sendMessage(ChatColor.WHITE+"- "+pls.getName());
								i++;
							}
						}
						if(i == 0)
							player.sendMessage(ChatColor.DARK_PURPLE+"Il semblerait que personne ne soit en mesure de vous accepter en ce momment :/.");
						player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
						PlayerJoinManager.stap3.remove(player);
						PlayerJoinManager.stap2.add(player);
						player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
						player.sendMessage(ChatColor.BLACK+"-------------------------------------");
						PacketPlayOutChat chat = new PacketPlayOutChat(ChatSerializer.a("[\"\",{\"text\":\"Souhaitez-vous directement rejoindre une personne dans son aventure ?\",\"color\":\"yellow\",\"bold\":true,\"underlined\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Vous pouvez soit commencer seul, \",\"color\":\"light_purple\"},{\"text\":\"ou bien rejoindre un(e) ami(e) qui \",\"color\":\"light_purple\"},{\"text\":\"est connecté(e).\",\"color\":\"light_purple\"}]}}},{\"text\":\" \\n[\",\"color\":\"gray\",\"bold\":false,\"underlined\":false},{\"text\":\"oui\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 2\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"J'ai bien un(e) ami(e) que je pourrais rejoindre.\",\"color\":\"light_purple\"}]}}},{\"text\":\"] [\",\"color\":\"gray\"},{\"text\":\"non\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 3\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Non les ami(e)s c'est pour les faibles !\",\"color\":\"light_purple\"}]}}},{\"text\":\"]\",\"color\":\"gray\"}]"));
						((CraftPlayer) player).getHandle().playerConnection.sendPacket(chat);
						removeSendInvite(player);
						return;
					}
				}
				player.sendMessage(ChatColor.GREEN+"La demande d'invitation a été envoyée à "+ChatColor.WHITE+msg+ChatColor.GREEN+" !");
				new OpenMenuInvite(target, player);
				removeSendInvite(player);
			}else{
				player.sendMessage(ChatColor.WHITE+msg+ChatColor.DARK_PURPLE+" n'est pas connecté(e).");
				player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
				PlayerJoinManager.stap3.remove(player);
				PlayerJoinManager.stap2.add(player);
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
				player.sendMessage(ChatColor.BLACK+"-------------------------------------");
				PacketPlayOutChat chat = new PacketPlayOutChat(ChatSerializer.a("[\"\",{\"text\":\"Souhaitez-vous directement rejoindre une personne dans son aventure ?\",\"color\":\"yellow\",\"bold\":true,\"underlined\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Vous pouvez soit commencer seul, \",\"color\":\"light_purple\"},{\"text\":\"ou bien rejoindre un(e) ami(e) qui \",\"color\":\"light_purple\"},{\"text\":\"est connecté(e).\",\"color\":\"light_purple\"}]}}},{\"text\":\" \\n[\",\"color\":\"gray\",\"bold\":false,\"underlined\":false},{\"text\":\"oui\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 2\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"J'ai bien un(e) ami(e) que je pourrais rejoindre.\",\"color\":\"light_purple\"}]}}},{\"text\":\"] [\",\"color\":\"gray\"},{\"text\":\"non\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 3\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Non les ami(e)s c'est pour les faibles !\",\"color\":\"light_purple\"}]}}},{\"text\":\"]\",\"color\":\"gray\"}]"));
				((CraftPlayer) player).getHandle().playerConnection.sendPacket(chat);
				removeSendInvite(player);
			}
			
		}
		
	}
	
	public static void addPlayerStopChat(Player player){
		stopchat.add(player);
	}
	
	public static void removePlayerStopChat(Player player){
		stopchat.remove(player);
	}
	
	public static void addSendInvite(Player player){
		sendInvite.add(player);
	}
	
	public static void removeSendInvite(Player player){
		sendInvite.remove(player);
	}

}

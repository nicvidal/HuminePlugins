package com.aymegike.bvn.event;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import com.aymegike.bvn.utils.objets.OpenMenuInvite;

import net.md_5.bungee.api.ChatColor;

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
				return;
			}
			
			if(target.isOnline()){
				if(stopchat.contains(target)){
					player.sendMessage(ChatColor.WHITE+msg+ChatColor.DARK_PURPLE+" n'a pas encore commencer de partie sur ce serveur.");
					player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					return;
				}
				player.sendMessage(ChatColor.GREEN+"Une invitation a était envoyer a "+ChatColor.WHITE+msg+ChatColor.GREEN+" !");
				new OpenMenuInvite(target, player);
			}else{
				player.sendMessage(ChatColor.WHITE+msg+ChatColor.DARK_PURPLE+" n'est pas connecté(e).");
				player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
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

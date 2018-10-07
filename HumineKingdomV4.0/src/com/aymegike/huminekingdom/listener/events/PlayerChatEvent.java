package com.aymegike.huminekingdom.listener.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.MenuList;
import com.aymegike.huminekingdom.utils.Permissions;
import com.aymegike.huminekingdom.utils.models.Grade;
import com.aymegike.huminekingdom.utils.models.Kingdom;
import com.aymegike.huminekingdom.utils.models.Shematic;

public class PlayerChatEvent implements Listener {
	
	public static ArrayList<OfflinePlayer> getNameOfKingdom = new ArrayList<OfflinePlayer>();
	public static ArrayList<OfflinePlayer> getNameOfPlayer = new ArrayList<OfflinePlayer>();
	public static ArrayList<OfflinePlayer> getNameOfGrade = new ArrayList<OfflinePlayer>();
	public static ArrayList<OfflinePlayer> getNameOfShematic = new ArrayList<OfflinePlayer>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerUseChat(org.bukkit.event.player.PlayerChatEvent e) {
		
		Player player = e.getPlayer();
		if (getNameOfKingdom.contains(player)) {
			e.setCancelled(true);
			String msg = e.getMessage();
			String[] getSpace = msg.split(" ");
			if(getSpace.length == 1){
				if(HumineKingdom.getKingdom(msg) != null){
					player.sendMessage(ChatColor.RED+msg+" est un nom super cool ! Du coup il est déjà utilisé...");
					player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					getNameOfKingdom.remove(player);
					return;
				}
				player.sendMessage(ChatColor.DARK_PURPLE+"Construction du futur empire en cours...");
				player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 5, 1);
				new Kingdom(msg, player, "Roi");
				MenuList.mainKingdomMenu(player).openMenu();
				
			}else{				
				player.sendMessage(ChatColor.RED+"Tu ne peut pas avoir d'espace dans le nom de ton royaume. Un peut de respect pour lui quoi...");
				player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
			
			}
			
			getNameOfKingdom.remove(player);	
		}
		else if(getNameOfPlayer.contains(player)) {
			e.setCancelled(true);
			String msg = e.getMessage();
			if(HumineKingdom.getPlayerKingdom(player) != null){
				if(Bukkit.getPlayer(msg) != null){
					if(HumineKingdom.getPlayerKingdom(Bukkit.getOfflinePlayer(msg)) == null){
						player.sendMessage(ChatColor.DARK_PURPLE+msg+" a reçu l'invitation !");
						player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);
						MenuList.invitationMenu(player, Bukkit.getPlayer(msg)).openMenu();;
					}else{
						player.sendMessage(ChatColor.DARK_PURPLE+"C'est bête mais "+msg+" fait déja partie d'un royaume !");
						player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					}
					
				}else{
					
					player.sendMessage(ChatColor.RED+msg+" n'est pas connecté... Tu est sur d'avoir bien écrit le nom de ton ami ?");
					player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
				
				}
			}
			
			MenuList.membersMenu(player).openMenu();
			getNameOfPlayer.remove(player);
		}
		else if (getNameOfGrade.contains(player)) {
			e.setCancelled(true);
			String msg = e.getMessage();
			String[] getSpace = msg.split(" ");
			if(getSpace.length == 1){
				if(HumineKingdom.getPlayerKingdom(player).getGrade(msg) != null){
					player.sendMessage(ChatColor.RED+msg+" est un nom super cool ! Du coup il est déjà utilisé...");
					player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					getNameOfGrade.remove(player);
					return;
				}
				player.sendMessage(ChatColor.DARK_PURPLE+"Construction du grade en cours...");
				player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 5, 1);
				HumineKingdom.getPlayerKingdom(player).addGrade(new Grade(HumineKingdom.getPlayerKingdom(player), msg));
				MenuList.gradeListMenu(player).openMenu();
				
			}else{				
				player.sendMessage(ChatColor.RED+"Tu ne peut pas avoir d'espace dans le nom de ton grade. Un peut de respect pour lui quoi...");
				player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
			
			}
			
			getNameOfGrade.remove(player);	
			
		} else if(getNameOfShematic.contains(player)) {
			e.setCancelled(true);
			
			String msg = e.getMessage();
			String[] getSpace = msg.split(" ");
			
			if(getSpace.length == 1){
				if(HumineKingdom.getPlayerKingdom(player).getShematic(msg) != null){
					player.sendMessage(ChatColor.RED+msg+" est un nom super cool ! Du coup il est déjà utilisé...");
					player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					getNameOfGrade.remove(player);
					return;
				}
				player.sendMessage(ChatColor.DARK_PURPLE+"Construction du plans en cours...");
				player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 5, 1);
				HumineKingdom.getPlayerKingdom(player).addShematic(new Shematic(HumineKingdom.getPlayerKingdom(player), msg, PlayerClick.getShieldGenerator.get(player)));
				MenuList.shematicMenu(player).openMenu();
				PlayerClick.getShieldGenerator.remove(player);
				
			} else{				
				player.sendMessage(ChatColor.RED+"Tu ne peut pas avoir d'espace dans le nom de ton plans. Un peut de respect pour lui quoi...");
				player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
			
			}
			
			getNameOfShematic.remove(player);
			
		} else {
			
			if(HumineKingdom.getPlayerGrade(player) != null){
				if(HumineKingdom.getPlayerGrade(player).containPermission(Permissions.CHAT.getPermission())){
					String msg = e.getMessage();
					String[] args = msg.split("");
					if(args[0].equalsIgnoreCase("*")){
						for(OfflinePlayer op : HumineKingdom.getPlayerKingdom(player).getMembers()){
							if(op.isOnline()){
								if(HumineKingdom.getPlayerGrade(player).containPermission(Permissions.CHAT.getPermission())){
									e.setCancelled(true);
									op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
									op.getPlayer().sendMessage(ChatColor.BLACK+"["+ChatColor.DARK_PURPLE+HumineKingdom.getPlayerKingdom(player).getName()+ChatColor.BLACK+"-"+ChatColor.DARK_PURPLE+HumineKingdom.getPlayerGrade(player).getName()+ChatColor.BLACK+"] "+ChatColor.WHITE+player.getName()+ChatColor.WHITE+": "+ChatColor.GRAY+msg.replace("*", ""));								
								}
							}
						}
						System.out.println("["+HumineKingdom.getPlayerKingdom(player).getName()+"-"+HumineKingdom.getPlayerGrade(player).getName()+"] "+player.getName()+": "+msg.replace("*", ""));
						
					}
				}
			}
		}
		
		
		
	}

}

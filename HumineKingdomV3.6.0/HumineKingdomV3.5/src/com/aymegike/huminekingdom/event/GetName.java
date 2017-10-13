package com.aymegike.huminekingdom.event;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.MenuList;
import com.aymegike.huminekingdom.utils.manager.GradeManager;
import com.aymegike.huminekingdom.utils.manager.KingdomManager;
import com.aymegike.huminekingdom.utils.objets.Grade;
import com.aymegike.huminekingdom.utils.objets.Kingdom;

@SuppressWarnings("deprecation")
public class GetName implements Listener{
	
	public static ArrayList<Player> getNameOfKingdom = new ArrayList<Player>();
	public static ArrayList<Player> getNameOfPlayer = new ArrayList<Player>();
	public static ArrayList<Player> getNameOfGrade = new ArrayList<Player>();
	
	
	@EventHandler
	public void onPLayerChat(PlayerChatEvent e){
		
		Player p = e.getPlayer();
		if(getNameOfKingdom.contains(p)){
		
			e.setCancelled(true);
			String msg = e.getMessage();
			String[] getSpace = msg.split(" ");
			if(getSpace.length == 1){
				if(HumineKingdom.getKingdom(msg) != null){
					p.sendMessage(ChatColor.RED+msg+" est un nom super cool ! Du coup il est déjà utilisé :/...");
					p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					getNameOfKingdom.remove(p);
					return;
				}
				p.sendMessage(ChatColor.DARK_PURPLE+"Construction du futur empire en cours...");
				p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 5, 1);
				Kingdom k = new Kingdom(msg, 0);
				k.initFileGlory();
				k.addMember(p, true, true);
				KingdomManager.setKingdom(k);
				Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("HumineKingdom"), new Runnable(){
		            @Override
					public void run(){
		            	Grade g = new Grade("King", k, true);
						g.addNewPerms("kingdom.build");
						g.addNewPerms("kingdom.break");
						g.addNewPerms("kingdom.gestion.invite");
						g.addNewPerms("kingdom.gestion.grade");
						g.addNewPerms("kingdom.gestion.perm");
						g.addNewPerms("kingdom.gestion.create");
						g.addNewPerms("kingdom.chat");
						g.addNewPerms("kingdom.gestion.invite.newplayer");
						g.addNewPlayer(Bukkit.getOfflinePlayer(p.getName()), true);
						GradeManager.addGrade(g);
						g = new Grade("Aucun", k, true);
						GradeManager.addGrade(g);
						p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
						MenuList.MainKingdomMenu(k, p).openForPlayer(p);
						p.sendMessage(ChatColor.GREEN+"[HumineKingdom] Felicitation ! "+msg+" rejoint la course vers la gloire !");
		            }
		        }, 100);
				
				
			}else{				
				p.sendMessage(ChatColor.RED+"[HumineKingdom] Vous ne pouvez pas avoir d'espace dans votre nom !");
				p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
			
			}
			
			getNameOfKingdom.remove(p);		
		}
		else if(getNameOfPlayer.contains(p)){
			e.setCancelled(true);
			String msg = e.getMessage();
			if(HumineKingdom.getPlayerkingdom(p) != null){
				if(Bukkit.getPlayer(msg) != null){
					if(HumineKingdom.getPlayerkingdom(Bukkit.getOfflinePlayer(msg)) == null){
						p.sendMessage(ChatColor.DARK_PURPLE+msg+" a reçu l'invitation !");
						p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
						MenuList.InvitationMenu(HumineKingdom.getPlayerkingdom(Bukkit.getOfflinePlayer(p.getName())), p).openForPlayer(Bukkit.getPlayer(msg));
					}else{
						p.sendMessage(ChatColor.DARK_PURPLE+msg+" fait déja partie d'un royaume !");
						p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					}
					
				}else{
					
					p.sendMessage(ChatColor.RED+msg+" n'est pas connecté !");
					p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
				
				}
			}else{
				getNameOfPlayer.remove(p);
				return;
			}
			
			MenuList.MemberMenu(HumineKingdom.getPlayerkingdom(p)).openForPlayer(p);
			getNameOfPlayer.remove(p);
		}
		else if(getNameOfGrade.contains(p)){
			e.setCancelled(true);		
			String msg = e.getMessage().replace("/", "").replace("\\", "").replace(":", "").replace("*", "").replace("?", "").replace("*", "").replace("|", "").replace(">", "").replace("<", "");
			if(HumineKingdom.getPlayerkingdom(p) != null){
				File file = new File(HumineKingdom.getPlayerkingdom(p).getKingdomFile()+"/grade/"+msg);
				if(file.exists()){
					p.sendMessage(ChatColor.RED+msg+" existe deja !");
					p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					getNameOfGrade.remove(p);
				}else{
					Grade g = new Grade(msg, HumineKingdom.getPlayerkingdom(p), true);
					GradeManager.addGrade(g);
					p.sendMessage(ChatColor.BLUE+"Super ! vous venez de créer le grade: "+ChatColor.WHITE+msg+ChatColor.BLUE+" !");
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
					getNameOfGrade.remove(p);
					
				}
			}else{
				getNameOfGrade.remove(p);
				return;
			}
			
			MenuList.gradeListMenu(HumineKingdom.getPlayerkingdom(p)).openForPlayer(p);
		}
	}
}

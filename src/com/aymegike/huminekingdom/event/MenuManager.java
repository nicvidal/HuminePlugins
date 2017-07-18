package com.aymegike.huminekingdom.event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.MenuList;
import com.aymegike.huminekingdom.utils.objets.Grade;

import net.md_5.bungee.api.ChatColor;

public class MenuManager implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerUseMenu(InventoryClickEvent e){
		
		if(e.getWhoClicked() instanceof Player){
		
			Player p = (Player) e.getWhoClicked();
			
			  if(e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta()){             
				  return;
			  }
			  
			  //NO KINGDOM
			  if(e.getInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_PURPLE+"Vous n'avez pas de kingdom !")){
				  e.setCancelled(true);
				  if(e.getCurrentItem().getType() == Material.REDSTONE_BLOCK){
					  p.sendMessage(ChatColor.DARK_PURPLE+"Dommage comme vous voulez...");
					  p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					  p.closeInventory();
				  }
				  if(e.getCurrentItem().getType() == Material.EMERALD_BLOCK){
					  p.sendMessage(ChatColor.GREEN+"Chouette ! Tout d'abord il faut donner un nom à ton royaume ! "+ChatColor.DARK_PURPLE+"(Tu ne pourras pas le modifier)");
					  p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
					  if(!GetName.getNameOfKingdom.contains(p))
						  GetName.getNameOfKingdom.add(p);		
					  p.closeInventory();
				  }
			  }
			 	  
			  //INVITE
			  else if(e.getInventory().getTitle().equalsIgnoreCase(ChatColor.GOLD+"Vous avez reçu une invitation !")){
				 e.setCancelled(true);
				 if(e.getCurrentItem().getType() == Material.EMERALD_BLOCK){
	
					 String name = e.getInventory().getItem(13).getItemMeta().getDisplayName();
					 String[] args = name.split(" ");
					 String kingdom = args[5].replace("§f", "");
					 System.out.println(kingdom);
					 HumineKingdom.getKingdom(kingdom).addMember(p, true, false);
					 HumineKingdom.getGrade("Aucun", HumineKingdom.getKingdom(kingdom)).addNewPlayer(p, true);
					 for(OfflinePlayer pls : HumineKingdom.getKingdom(kingdom).getAllMember()){
						 if(Bukkit.getPlayer(pls.getName()) != null){
							 Bukkit.getPlayer(pls.getName()).sendMessage(ChatColor.DARK_PURPLE+p.getName()+" rejoint le royaume !");
							 Bukkit.getPlayer(pls.getName()).playSound(Bukkit.getPlayer(pls.getName()).getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
						 }
					 }
					 
					 p.closeInventory();
				 }
				 if(e.getCurrentItem().getType() == Material.REDSTONE_BLOCK){
					 String name = e.getInventory().getItem(13).getItemMeta().getDisplayName();
					 String[] args = name.split(" ");
					 String player = args[0].replace("§f", "");
					 if(Bukkit.getPlayer(player) != null){
						 Bukkit.getPlayer(player).sendMessage(ChatColor.DARK_PURPLE+p.getName()+" a refusé l'invitation !");
						 Bukkit.getPlayer(player).playSound(Bukkit.getPlayer(player).getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					 }
					 p.closeInventory();
					 p.sendMessage(ChatColor.DARK_PURPLE+"Dommage comme vous voulez...");
					 p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
				 }
			  }
			  
			  else if(HumineKingdom.getPlayerkingdom(p) == null){
				  return;
			  }
			  
			  //BALISE MENU
			  
			  else if(e.getInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA+"Liste des Balises")){
				  e.setCancelled(true);
				  if(e.getCurrentItem().getType() == Material.BARRIER){
					  p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
					  MenuList.gestionMenu(HumineKingdom.getPlayerkingdom(p), p).openForPlayer(p);
				  }
			  }
			  
			  //GESTION MENU
			  else if(e.getInventory().getTitle().equalsIgnoreCase(ChatColor.GRAY+"Gestion "+HumineKingdom.getPlayerkingdom(p).getName())){
				  e.setCancelled(true);
				  if(e.getCurrentItem().getType() == Material.SKULL_ITEM){
					  if(HumineKingdom.getPlayerGrade(p).getName().equalsIgnoreCase("King")){
						  for(OfflinePlayer op : HumineKingdom.getPlayerkingdom(p).getAllMember()){
							  if(op.isOnline()){
								  op.getPlayer().sendMessage(ChatColor.DARK_PURPLE+"Votre roi "+ChatColor.WHITE+p.getName()+ChatColor.DARK_PURPLE+" décide d'abandonner "+ChatColor.WHITE+HumineKingdom.getPlayerkingdom(p).getName()+ChatColor.DARK_PURPLE+" pour de bon.");
								  op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
								  op.getPlayer().closeInventory();
							  }
						  }
						  HumineKingdom.getPlayerkingdom(p).delet();
						  return;
					  }else{
						  for(OfflinePlayer op : HumineKingdom.getPlayerkingdom(p).getAllMember()){
							  if(op.isOnline()){
								  op.getPlayer().sendMessage(ChatColor.WHITE+p.getName()+ChatColor.DARK_PURPLE+" quitte "+ChatColor.WHITE+HumineKingdom.getPlayerkingdom(p).getName()+ChatColor.DARK_PURPLE+" !");
								  op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
							  }
						  }
						  HumineKingdom.getPlayerGrade(p).removePlayer(p);
						  HumineKingdom.getPlayerkingdom(p).removeMember(p);
						  p.closeInventory();
					  }
				  }
				  if(e.getCurrentItem().getType() == Material.BARRIER){
					  p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
					  MenuList.MainKingdomMenu(HumineKingdom.getPlayerkingdom(p), p).openForPlayer(p);
				  }
				  if(e.getCurrentItem().getType() == Material.BEACON){
					  p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
					  MenuList.BeaconList(HumineKingdom.getPlayerkingdom(p)).openForPlayer(p);
				  }
			  }
			  
			  //MAIN KINGDOM
			  else if(e.getInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_PURPLE+"Votre Royaume ! "+ChatColor.WHITE+HumineKingdom.getPlayerkingdom(p).getName())){
				  e.setCancelled(true);
				  if(e.getCurrentItem().getType() == Material.COMPASS){
					  p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
					  MenuList.gestionMenu(HumineKingdom.getPlayerkingdom(p), p).openForPlayer(p);
				  }
				  if(e.getCurrentItem().getType() == Material.SKULL_ITEM){
					  MenuList.MemberMenu(HumineKingdom.getPlayerkingdom(p)).openForPlayer(p);
					  p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
				  }
				  if(e.getCurrentItem().getType() == Material.BANNER){
					  MenuList.gradeListMenu(HumineKingdom.getPlayerkingdom(p)).openForPlayer(p);
					  p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
				  }
				  if(e.getCurrentItem().getType() == Material.DRAGON_EGG){
					  p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
				  }
				  if(e.getCurrentItem().getType() == Material.BARRIER){
					  p.closeInventory();
					  p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);				  
				  }
			  }
			  //MEMBER
			  else if(e.getInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_PURPLE+"Membres "+ChatColor.WHITE+HumineKingdom.getPlayerkingdom(p).getName())){
				  e.setCancelled(true);
				  if(e.getCurrentItem().getType() == Material.BARRIER){
					  p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
					  MenuList.MainKingdomMenu(HumineKingdom.getPlayerkingdom(p), p).openForPlayer(p);;
					  p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);	
				  }
				  if(e.getCurrentItem().getType() == Material.BOOK_AND_QUILL){
					  if(p.hasPermission("kingdom.gestion.invite")){
						  p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
						  GetName.getNameOfPlayer.add(p);
						  p.sendMessage(ChatColor.GREEN+"Besoin d'une nouvelle recrue ! Ecrivez le nom du nouveau \njoueur pour lui envoyer une invitation.");
						  p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 5, 5);
						  p.closeInventory();
					  }else{
						  p.sendMessage(ChatColor.RED+"Désolé l'ami ! mais tu n'a pas les droits pour inviter du monde ici :/");
						  p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					  }
				  }
				  if(e.getCurrentItem().getType() == Material.SKULL_ITEM){
					  p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
					  MenuList.PlayerMenu(HumineKingdom.getPlayerkingdom(p), Bukkit.getOfflinePlayer(e.getCurrentItem().getItemMeta().getDisplayName().replace("§a", ""))).openForPlayer(p);
				  }
			  }
			  //PLAYER
			  else if(e.getInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_PURPLE+"Joueur")){
				  e.setCancelled(true);
				  if(e.getCurrentItem().getType() == Material.SKULL_ITEM){
					  p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
					  MenuList.MemberMenu(HumineKingdom.getPlayerkingdom(p)).openForPlayer(p);
				  }
				  if(e.getCurrentItem().getType() == Material.REDSTONE_BLOCK){
					  
					  if(p.hasPermission("kingdom.gestion.invite")){
						  String player = e.getInventory().getItem(13).getItemMeta().getDisplayName().replace("§a", "");
						  if(!player.equalsIgnoreCase(p.getName())){
							  if(!HumineKingdom.getPlayerGrade(Bukkit.getOfflinePlayer(player)).getName().equalsIgnoreCase("King")){
								  HumineKingdom.getPlayerkingdom(p).removeMember(Bukkit.getOfflinePlayer(player));
								  HumineKingdom.getPlayerGrade(Bukkit.getOfflinePlayer(player)).removePlayer(Bukkit.getOfflinePlayer(player));
								  MenuList.MemberMenu(HumineKingdom.getPlayerkingdom(p)).openForPlayer(p);
								  if(Bukkit.getPlayer(player) != null){
									  Bukkit.getPlayer(player).closeInventory();
									  Bukkit.getPlayer(player).sendMessage(ChatColor.WHITE+p.getName()+ChatColor.DARK_PURPLE+" vous exclu de "+ChatColor.WHITE+HumineKingdom.getPlayerkingdom(p).getName()+ChatColor.DARK_PURPLE+" !");
									  p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5); 
								  }
								  for(OfflinePlayer opls : HumineKingdom.getPlayerkingdom(p).getAllMember()){
									  if(Bukkit.getPlayer(opls.getName()) != null){
										  Bukkit.getPlayer(opls.getName()).sendMessage(ChatColor.WHITE+p.getName()+ChatColor.DARK_PURPLE+" a exclu "+ChatColor.WHITE+Bukkit.getOfflinePlayer(player).getName()+ChatColor.DARK_PURPLE+" de "+ChatColor.WHITE+HumineKingdom.getPlayerkingdom(p).getName()+ChatColor.DARK_PURPLE+" !");
										  Bukkit.getPlayer(opls.getName()).playSound(Bukkit.getPlayer(opls.getName()).getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5); 
									  }
								  }
							  }else{
								  p.sendMessage(ChatColor.RED+"Vous ne pouvez pas exlure le roi !");
								  p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);  
							  }
						  }else{
							  p.sendMessage(ChatColor.RED+"Vous ne pouvez pas vous exclure vous-même !");
							  p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5); 
						  }
					  }else{
						  p.sendMessage(ChatColor.RED+"Vous n'avez pas la permission de faire sa !");
						  p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					  }
				  }
				  if(e.getCurrentItem().getType() == Material.NAME_TAG){
					  if(p.hasPermission("kingdom.gestion.grade")){
						  String player = e.getInventory().getItem(13).getItemMeta().getDisplayName().replace("§a", "");
						  if(!p.getName().equalsIgnoreCase(player)){
							  if(!HumineKingdom.getPlayerGrade(Bukkit.getOfflinePlayer(player)).getName().equalsIgnoreCase("King")){
								  p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
							  	  MenuList.choseGrade(HumineKingdom.getPlayerkingdom(p), Bukkit.getOfflinePlayer(e.getInventory().getItem(13).getItemMeta().getDisplayName().replace("§a",""))).openForPlayer(p);
							  }else{
								  p.sendMessage(ChatColor.RED+"Vous ne pouvez pas changer le grade du roi !");
								  p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5); 
							  }
						  }else{
							  p.sendMessage(ChatColor.RED+"Vous ne pouvez pas changer votre propre grade !");
							  p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
						  }
					  }else{
						  p.sendMessage(ChatColor.RED+"Vous n'avez pas la permission de faire sa !");
						  p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					  }
				  }
				  
			  }
			  
			  //GRADE LIST MENU
			  else if(e.getInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_PURPLE+"Liste des grades")){
				  e.setCancelled(true);
				  if(e.getCurrentItem().getType() == Material.BARRIER){
					  p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
					  MenuList.MainKingdomMenu(HumineKingdom.getPlayerkingdom(p), p).openForPlayer(p);
				  }
				  if(e.getCurrentItem().getType() == Material.BOOK_AND_QUILL){
					  if(p.hasPermission("kingdom.gestion.create")){
						  
						  p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 5, 5);
						  p.closeInventory();
						  
						  p.sendMessage(ChatColor.DARK_PURPLE+"Pour créer un nouveau grade, ecrivez son nom !");
						  GetName.getNameOfGrade.add(p);
						  
						  p.closeInventory();
					  
					  }else{
						  
						  p.sendMessage(ChatColor.RED+"Vous n'avez pas la permission !");
						  p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
						  
					  }
				  }
				  if(e.getCurrentItem().getType() == Material.NAME_TAG){
					  if(p.hasPermission("kingdom.gestion.grade") || p.hasPermission("kingdom.gestion.create") || p.hasPermission("kingdom.gestion.perm")){
						  p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
						  MenuList.gradeMenu(HumineKingdom.getGrade(e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§9", ""), HumineKingdom.getPlayerkingdom(p))).openForPlayer(p);; 
					  }else{
						  p.sendMessage(ChatColor.RED+"Vous n'avez pas la permission !");
						  p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5); 
					  }
				  }
			  }
			  
			  //GRADE MENU
			  else if(e.getInventory().getTitle().equalsIgnoreCase(ChatColor.BLUE+"Grade")){
				  e.setCancelled(true);
				  if(e.getCurrentItem().getType() == Material.NAME_TAG){
					  p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
					  MenuList.gradeListMenu(HumineKingdom.getPlayerkingdom(p)).openForPlayer(p);
				  }
				  if(e.getCurrentItem().getType() == Material.REDSTONE_BLOCK){
					  if(p.hasPermission("kingdom.gestion.create")){
						  new Grade(e.getInventory().getItem(13).getItemMeta().getDisplayName().replace("§9", ""), HumineKingdom.getPlayerkingdom(p), false).delete();
						  p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5); 
						  MenuList.gradeListMenu(HumineKingdom.getPlayerkingdom(p)).openForPlayer(p);	
					  }else{
						  p.sendMessage(ChatColor.RED+"Vous n'avez pas la permission !");
						  p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5); 
					  }
				  }
				  if(e.getCurrentItem().getType() == Material.PAPER){
					  if(p.hasPermission("kingdom.gestion.perm")){
						  p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
						  MenuList.permsListMenu(HumineKingdom.getPlayerkingdom(p), HumineKingdom.getGrade(e.getInventory().getItem(13).getItemMeta().getDisplayName().replace("§9", ""), HumineKingdom.getPlayerkingdom(p))).openForPlayer(p);
					  }else{
						  p.sendMessage(ChatColor.RED+"Vous n'avez pas la permission !");
						  p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5); 
					  }
			      }
				  
			  }
			  //ADDPERMMENU
			  else if(e.getInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_PURPLE+"Ajouter une permission")){
				  e.setCancelled(true);
				  if(e.getCurrentItem().getType() == Material.PAPER){				 
					  String perm = e.getCurrentItem().getItemMeta().getDisplayName().replace("§e", "");
					  String grade = e.getInventory().getItem(e.getInventory().getSize() - 5).getItemMeta().getDisplayName().replace("§9", "");
					  Grade g = HumineKingdom.getGrade(grade, HumineKingdom.getPlayerkingdom(p));
					  if(!g.hasPerm(perm)){
						  p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
						  g.addNewPerms(perm);
						  MenuList.permsListMenu(HumineKingdom.getPlayerkingdom(p), HumineKingdom.getGrade(e.getInventory().getItem(e.getInventory().getSize() - 5).getItemMeta().getDisplayName().replace("§9", ""), HumineKingdom.getPlayerkingdom(p))).openForPlayer(p);
					  }else{
						  p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5); 
					  }
				  }
				  if(e.getCurrentItem().getType() == Material.BARRIER){
					  p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
					  MenuList.permsListMenu(HumineKingdom.getPlayerkingdom(p), HumineKingdom.getGrade(e.getInventory().getItem(e.getInventory().getSize() - 5).getItemMeta().getDisplayName().replace("§9", ""), HumineKingdom.getPlayerkingdom(p))).openForPlayer(p);
				  }
			  }
			  
			  //PERM MENU
			  else if(e.getInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_PURPLE+"Liste des permissions")){
				  e.setCancelled(true);
				  if(e.getCurrentItem().getType() == Material.PAPER){
					  String perm = e.getCurrentItem().getItemMeta().getDisplayName().replace("§e", "");
					  String grade = e.getInventory().getItem(e.getInventory().getSize() - 5).getItemMeta().getDisplayName().replace("§9", "");
					  Grade g = HumineKingdom.getGrade(grade, HumineKingdom.getPlayerkingdom(p));
					  g.removePerm(perm);
					  e.getCurrentItem().setType(Material.AIR);
					  p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					  MenuList.permsListMenu(HumineKingdom.getPlayerkingdom(p), g).openForPlayer(p);
				  }
				  if(e.getCurrentItem().getType() == Material.BOOK_AND_QUILL){
					  p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
					  String grade = e.getInventory().getItem(e.getInventory().getSize() - 5).getItemMeta().getDisplayName().replace("§9", "");
					  MenuList.addPermsListMenu(HumineKingdom.getGrade(grade, HumineKingdom.getPlayerkingdom(p))).openForPlayer(p);
				  }
				  if(e.getCurrentItem().getType() == Material.BARRIER){
					  p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
					  MenuList.gradeMenu(HumineKingdom.getGrade(e.getInventory().getItem(e.getInventory().getSize() - 5).getItemMeta().getDisplayName().replace("§9", ""), HumineKingdom.getPlayerkingdom(p))).openForPlayer(p);
				  }
			  }
			  
			  //CHOSE GRADE
			  else if(e.getInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_PURPLE+"Choisire un grade")){
				  e.setCancelled(true);
				  if(e.getCurrentItem().getType() == Material.BARRIER){
					  p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
					  MenuList.PlayerMenu(HumineKingdom.getPlayerkingdom(p), Bukkit.getOfflinePlayer(e.getInventory().getItem(e.getInventory().getSize()-5).getItemMeta().getDisplayName().replace("§a", ""))).openForPlayer(p);
				  }
				  if(e.getCurrentItem().getType() == Material.NAME_TAG){
					  String grade = e.getCurrentItem().getItemMeta().getDisplayName().replace("§9", "");
					  OfflinePlayer op = Bukkit.getOfflinePlayer(e.getInventory().getItem(e.getInventory().getSize()-5).getItemMeta().getDisplayName().replace("§a", ""));
					  if(HumineKingdom.getPlayerGrade(op) != null){
						  HumineKingdom.getPlayerGrade(op).removePlayer(op); 
					  }					  
					  p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 5, 5);
					  p.sendMessage(ChatColor.DARK_PURPLE+op.getName()+" devient "+ChatColor.WHITE+grade+ChatColor.DARK_PURPLE+" !");
					  MenuList.PlayerMenu(HumineKingdom.getPlayerkingdom(p), op).openForPlayer(p);
					  HumineKingdom.getGrade(grade, HumineKingdom.getPlayerkingdom(p)).addNewPlayer(op, true);
					  if(op.isOnline()){
						  Bukkit.getPlayer(op.getName()).sendMessage(ChatColor.DARK_PURPLE+"Vous venez de changer de grade: "+ChatColor.WHITE+grade+ChatColor.DARK_PURPLE+" !");
						  Bukkit.getPlayer(op.getName()).playSound(Bukkit.getPlayer(op.getName()).getLocation(), Sound.ENTITY_FIREWORK_BLAST, 5, 5);
					  }
				  }
			  }
			  
			
		}
	}

}

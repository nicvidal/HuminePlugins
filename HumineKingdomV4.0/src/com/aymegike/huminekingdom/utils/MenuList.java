package com.aymegike.huminekingdom.utils;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.listener.events.PlayerChatEvent;
import com.aymegike.huminekingdom.listener.events.PlayerClick;
import com.aymegike.huminekingdom.utils.models.Grade;
import com.aymegike.huminekingdom.utils.models.Shematic;
import com.aypi.utils.Button;
import com.aypi.utils.Menu;
import com.aypi.utils.inter.MenuItemListener;

import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("deprecation")
public class MenuList {
	
	public static ArrayList<Menu> user = new ArrayList<Menu>();
	
	
	//MENU CREATION D'UN ROYAUME
	public static Menu noKingdomMenu(Player player) {
		closePlayerMenu(player);

		Menu menu = new Menu(player, ChatColor.DARK_PURPLE+"Tu n'as encore de royaume !", 3*9, true);
		
		ArrayList<String> lore = new ArrayList<String>();
		
		ItemStack emerald = new ItemStack(Material.EMERALD);
		ItemMeta emeraldM = emerald.getItemMeta();
		emeraldM.setDisplayName(ChatColor.GREEN+"INFORMATION !");
		lore.add(ChatColor.DARK_PURPLE+"Afin de devenir plus puissant, tu");
		lore.add(ChatColor.DARK_PURPLE+"peut créer ton royaume et recruter");
		lore.add(ChatColor.DARK_PURPLE+"des amis pour dominer le monde de "+ChatColor.WHITE+"HumineCraft"+ChatColor.DARK_PURPLE+" !");
		emeraldM.setLore(lore);
		emerald.setItemMeta(emeraldM);
		
		lore.clear();
		
		lore.add(ChatColor.DARK_PURPLE+"Cliquez pour valider");
		
		ItemStack accept = new ItemStack(Material.EMERALD_BLOCK);
		ItemMeta acceptm = accept.getItemMeta();
		acceptm.setDisplayName(ChatColor.YELLOW+"Créer un nouveau Royaume !"+ChatColor.GREEN+" ✔");
		acceptm.setLore(lore);
		accept.setItemMeta(acceptm);
		
		ItemStack decline = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta declineM = decline.getItemMeta();
		declineM.setDisplayName(ChatColor.YELLOW+"Non une autre fois peut-être..."+ChatColor.RED+" ✗");
		declineM.setLore(lore);
		decline.setItemMeta(declineM);
		
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 1);
		menu.fillLine(glass, 3);
		
		glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 2);
		
		menu.setButton(10, new Button(accept, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				 player.sendMessage(ChatColor.GREEN+"Chouette ! Tout d'abord il faut donner un nom à ton royaume ! "+ChatColor.DARK_PURPLE+"(Tu ne pourras pas le modifier)");
				 player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
				 if(!PlayerChatEvent.getNameOfKingdom.contains(player))
					 PlayerChatEvent.getNameOfKingdom.add(player);		
				 menu.closePlayerMenu();
				 MenuList.user.remove(menu);
			}
			
		}));
		
		menu.setButton(16, new Button(decline, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
				player.sendMessage(ChatColor.DARK_PURPLE+"Dommage comme vous voulez...");
				MenuList.user.remove(menu);
			}
			
		}));
		
		menu.setItem(13, emerald);
		
		MenuList.user.add(menu);
		return menu;
	}
	
	//MENU PRINCIPALE
	public static Menu mainKingdomMenu(Player player) {
		closePlayerMenu(player);

		Menu menu = new Menu(player, ChatColor.DARK_PURPLE+"-Votre royaume-", 6*9, false);
		
		ItemStack quit = new ItemStack(Material.BARRIER);
		ItemMeta quitm = quit.getItemMeta();
		quitm.setDisplayName(ChatColor.RED+"Quitter");
		quit.setItemMeta(quitm);
		
		ItemStack gestion = new ItemStack(Material.BEACON);
		ItemMeta gestionm = gestion.getItemMeta();
		gestionm.setDisplayName(ChatColor.AQUA+"Gestion des Générateurs de boucliés");
		gestion.setItemMeta(gestionm);
		
		ItemStack membre = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
		SkullMeta membrem = (SkullMeta) membre.getItemMeta();
		membrem.setDisplayName(ChatColor.GREEN+"Liste des membres");
		membrem.setOwner(player.getName());
		membre.setItemMeta(membrem);
		
		
		ItemStack grade = new ItemStack(Material.NAME_TAG);
		ItemMeta gradem = grade.getItemMeta();
		gradem.setDisplayName(ChatColor.BLUE+"Gestion des grades");
		grade.setItemMeta(gradem);
		
		ItemStack classement = new ItemStack(Material.DRAGON_EGG);
		ItemMeta classementm = classement.getItemMeta();
		classementm.setDisplayName(ChatColor.DARK_PURPLE+"Classement");
		classement.setItemMeta(classementm);
		
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 1);
		menu.fillLine(glass, 3);
		menu.fillLine(glass, 4);
		menu.fillLine(glass, 6);
		
		glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 2);
		menu.fillLine(glass, 5);
		
		menu.setButton(10, new Button(gestion, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.GestionMenu(player).openMenu();
				MenuList.user.remove(menu);
			}
			
		}));
		
		menu.setButton(13, new Button(membre, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.membersMenu(player).openMenu();
				MenuList.user.remove(menu);
			}
			
		}));
		
		menu.setButton(16, new Button(grade, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.gradeListMenu(player).openMenu();
				MenuList.user.remove(menu);
			}
			
		}));
		
		menu.setButton(40, new Button(classement, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				
			}
			
		}));
		
		menu.setButton(44, new Button(quit, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.user.remove(menu);
			}
			
		}));
		
		MenuList.user.add(menu);
		return menu;
	}
	
	//MENU DES MEMBRES
	public static Menu membersMenu(Player player) {
		closePlayerMenu(player);
		int size = HumineKingdom.getPlayerKingdom(player).getMembers().size();
		int c = 0;
		int multi = 1;
		for(int i = 1 ; i<size-2 ; i++){
			c++;
			if(c>=9){				
				c = 0;
				multi++;
			}
		}
		
		Menu menu = new Menu(player, ChatColor.DARK_GREEN+"- Membres -", (multi+1)*9, false);
		
		ArrayList<String> lore = new ArrayList<String>();
		
		ItemStack quit = new ItemStack(Material.BARRIER);
		ItemMeta quitm = quit.getItemMeta();
		quitm.setDisplayName(ChatColor.RED+"Quitter");
		quit.setItemMeta(quitm);
		
		lore.clear();
		
		ItemStack ice = new ItemStack(Material.ICE);
		ItemMeta icem = ice.getItemMeta();
		icem.setDisplayName(ChatColor.AQUA+"rafraîchir la page");
		ice.setItemMeta(icem);
		
		ItemStack add = new ItemStack(Material.LEGACY_BOOK_AND_QUILL);
		ItemMeta addm = add.getItemMeta();
		addm.setDisplayName(ChatColor.GREEN+"Ajouter un joueur");
		add.setItemMeta(addm);
		
		ItemStack glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, menu.getSize()/9);
		
		int slot = 0;
		for (OfflinePlayer op : HumineKingdom.getPlayerKingdom(player).getMembers()) {
			
			ItemStack membre = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
			SkullMeta membrem = (SkullMeta) membre.getItemMeta();
			membrem.setDisplayName(ChatColor.GREEN+op.getName());
			membrem.setOwningPlayer(op);
			if (HumineKingdom.getPlayerGrade(op) != null) {
				lore.add(ChatColor.DARK_PURPLE+"Grade: "+ChatColor.WHITE+HumineKingdom.getPlayerGrade(op).getName());
			} else {
				lore.add(ChatColor.DARK_PURPLE+"Grade: "+ChatColor.WHITE+"Aucun");
			}
			membrem.setLore(lore);
			lore.clear();
			membre.setItemMeta(membrem);
			menu.setButton(slot, new Button(membre, new MenuItemListener() {
				
				@Override
				public void onItemClick() {
						menu.closePlayerMenu();
					 	player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
					 	if (HumineKingdom.getPlayerKingdom(op) == HumineKingdom.getPlayerKingdom(player))
					 		MenuList.playerProfilMenu(player, op).openMenu();
					 	else
					 		MenuList.membersMenu(player).openMenu();
				}
				
			}));
			
			slot++;
		}
		
		menu.setButton(menu.getSize()-1, new Button(quit, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.mainKingdomMenu(player).openMenu();
				MenuList.user.remove(menu);
			}
			
		}));
		
		menu.setButton(menu.getSize()-9, new Button(add, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();			
				
				if (HumineKingdom.getPlayerGrade(player) != null && HumineKingdom.getPlayerGrade(player).containPermission(Permissions.INVITE.getPermission())) {
	
					player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
					PlayerChatEvent.getNameOfPlayer.add(player);
					player.sendMessage(ChatColor.GREEN+"Besoin d'une nouvelle recrue ? Ecris le nom du nouveau \njoueur pour lui envoyer une invitation.");
				}else{
					  player.sendMessage(ChatColor.RED+"Désolé l'ami ! Mais tu n'as pas les droits pour inviter du monde ici...\n"+ChatColor.ITALIC+"(demande de l'aide a un adulte)");
					  player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
				}
					
				MenuList.user.remove(menu);
				
			}
			
		}));
		
		menu.setButton(menu.getSize()-5, new Button(ice, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.BLOCK_SNOW_FALL, 5, 1);
				MenuList.membersMenu(player).openMenu();
			}
			
		}));
		
		MenuList.user.add(menu);
		return menu;
	}
	
	//MENU D'INVITATION 
	public static Menu invitationMenu(Player sender, Player receiver) {
		closePlayerMenu(receiver);
		Menu menu = new Menu(receiver, ChatColor.BOLD+sender.getName()+ChatColor.DARK_PURPLE+ "t'invite dans "+ChatColor.WHITE+HumineKingdom.getPlayerKingdom(sender).getName()+ChatColor.DARK_GREEN+" !", 3*9, true);
		
		ArrayList<String> lore = new ArrayList<String>();
		
		ItemStack emerald = new ItemStack(Material.EMERALD);
		ItemMeta emeraldM = emerald.getItemMeta();
		emeraldM.setDisplayName(ChatColor.WHITE+sender.getName()+ChatColor.GREEN+" t'invite pour rejoindre "+ChatColor.WHITE+HumineKingdom.getPlayerKingdom(sender).getName());
		lore.add("");
		lore.add(ChatColor.GOLD+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		lore.add(ChatColor.DARK_PURPLE+"Afin de devenir plus puissant, tu");
		lore.add(ChatColor.DARK_PURPLE+"peux rejoindre "+ChatColor.WHITE+HumineKingdom.getPlayerKingdom(sender).getName()+ChatColor.DARK_PURPLE+" afin de ");
		lore.add(ChatColor.DARK_PURPLE+"dominer le monde de "+ChatColor.WHITE+"HumineCraft"+ChatColor.DARK_PURPLE+" !");
		lore.add(ChatColor.GOLD+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		emeraldM.setLore(lore);
		emerald.setItemMeta(emeraldM);
		
		lore.clear();
		
		lore.add(ChatColor.DARK_PURPLE+"Clique pour valider");
		
		ItemStack accept = new ItemStack(Material.EMERALD_BLOCK);
		ItemMeta acceptm = accept.getItemMeta();
		acceptm.setDisplayName(ChatColor.YELLOW+"Accepter l'invitation"+ChatColor.GREEN+" ✔");
		acceptm.setLore(lore);
		accept.setItemMeta(acceptm);
		
		ItemStack decline = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta declineM = decline.getItemMeta();
		declineM.setDisplayName(ChatColor.YELLOW+"Non une autre fois peut-être..."+ChatColor.RED+" ✗");
		declineM.setLore(lore);
		decline.setItemMeta(declineM);
		
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 1);
		menu.fillLine(glass, 3);
		
		glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 2);
		
		menu.setButton(13, new Button(emerald, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				receiver.playSound(sender.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 50, 1);				
			}
			
		}));
		
		menu.setButton(10, new Button(accept, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				receiver.playSound(sender.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 50, 1);
				menu.closePlayerMenu();
				MenuList.user.remove(menu);
				HumineKingdom.getPlayerKingdom(sender).addMember(receiver);
				for(OfflinePlayer op : HumineKingdom.getPlayerKingdom(sender).getMembers()) {
					if (op.isOnline()) {
						op.getPlayer().sendMessage(ChatColor.GREEN+"Bienvenue dans le royaume "+ChatColor.WHITE+HumineKingdom.getPlayerKingdom(sender).getName()+ChatColor.GREEN+" "+receiver.getName()+" !");
						
					}
				}
			}
			
		}));
		
		menu.setButton(16, new Button(decline, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				receiver.playSound(sender.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 50, 1);
				receiver.sendMessage(ChatColor.RED+"Vraiment ? Bon... tant pis pour eux !");
				sender.sendMessage(ChatColor.RED+"Finalement "+receiver.getName()+" n'as pas voulu nous rejoindre... zut alors ! ");
				sender.playSound(sender.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
				receiver.closeInventory();
				MenuList.user.remove(menu);
			}
			
		}));
		
		MenuList.user.add(menu);
		return menu;
				
	}
	
	//PLAYER MENU
	public static Menu playerProfilMenu(Player player, OfflinePlayer target) {
		closePlayerMenu(player);
		Menu menu = new Menu(player, ChatColor.DARK_GREEN+"- "+ChatColor.WHITE+target.getName()+ChatColor.DARK_GREEN+" -", 3*9, false);
		
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 1);
		menu.fillLine(glass, 3);
		
		glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 2);
		
		ItemStack back = new ItemStack(Material.BARRIER);
		ItemMeta backm = back.getItemMeta();
		backm.setDisplayName(ChatColor.RED+"retour");
		back.setItemMeta(backm);
		
		ArrayList<String> lore = new ArrayList<String>();
		
		if (HumineKingdom.getPlayerGrade(target) != null) {
			lore.add(ChatColor.DARK_PURPLE+"Grade: "+ChatColor.WHITE+HumineKingdom.getPlayerGrade(target).getName());
		} else {
			lore.add(ChatColor.DARK_PURPLE+"Grade: "+ChatColor.WHITE+"Aucun");
		}
		
		ItemStack membre = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
		SkullMeta membrem = (SkullMeta) membre.getItemMeta();
		membrem.setDisplayName(ChatColor.GREEN+target.getName());
		membrem.setOwner(target.getName());
		membrem.setLore(lore);
		membre.setItemMeta(membrem);
		
		lore.clear();
		
		ItemStack grade = new ItemStack(Material.NAME_TAG);
		ItemMeta gradem = grade.getItemMeta();
		gradem.setDisplayName(ChatColor.GREEN+"Changer le grade de "+ChatColor.WHITE+target.getName());
		grade.setItemMeta(gradem);
		
		ItemStack quitter = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta quitterm = quitter.getItemMeta();
		
		if (player.getName().equalsIgnoreCase(target.getName())) {
			quitterm.setDisplayName(ChatColor.RED+"Quitter le royaume");
		} else {
			quitterm.setDisplayName(ChatColor.RED+"Exclure du royaume");
		}
		
		if (HumineKingdom.getPlayerGrade(target) != null && HumineKingdom.getPlayerGrade(target).getName().equalsIgnoreCase(HumineKingdom.getPlayerKingdom(target).getKingGradeName()) && player.getName().equalsIgnoreCase(target.getName())) {
			lore.add("");
			lore.add(ChatColor.RED+""+ChatColor.ITALIC+"Ton royaume sera supprimé et toute");
			lore.add(ChatColor.RED+"la gloire durement aquise avec sera perdue.");
			quitterm.setLore(lore);
		}
		
		quitter.setItemMeta(quitterm);
		
		menu.setButton(10, new Button(grade, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				if (HumineKingdom.getPlayerKingdom(player) == HumineKingdom.getPlayerKingdom(target)) {
					if (HumineKingdom.getPlayerGrade(player) != null && HumineKingdom.getPlayerGrade(player).containPermission(Permissions.GRADE.getPermission()) && !player.getName().equalsIgnoreCase(target.getName())) {
						menu.closePlayerMenu();
						player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
						MenuList.addPlayerGrade(player, target).openMenu();
					} else if (player.getName().equalsIgnoreCase(target.getName())){
						player.sendMessage(ChatColor.RED+"Tu ne peux pas modifier ton propre grade.");
						player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
					} else {
						player.sendMessage(ChatColor.RED+"Désolé l'ami mais tu n'en as pas la permission. \n(Demande le l'aide a un adulte)");
						player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
					}
				} else {
					MenuList.membersMenu(player).openMenu();
				}
			}
			
		}));
		
		menu.setItem(13, membre);
		
		menu.setButton(16, new Button(quitter, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				if (HumineKingdom.getPlayerKingdom(player) == HumineKingdom.getPlayerKingdom(target)) {
					
					if (HumineKingdom.getPlayerGrade(target) != null && HumineKingdom.getPlayerGrade(target).getName().equalsIgnoreCase(HumineKingdom.getPlayerKingdom(player).getKingGradeName()) && player.getName().equalsIgnoreCase(target.getName())) {
						for (OfflinePlayer op : HumineKingdom.getPlayerKingdom(target).getMembers()) {
							if (op.isOnline()) {
								op.getPlayer().sendMessage(HumineKingdom.getPlayerKingdom(player).getName()+ChatColor.RED+" n'est plus. Le "+HumineKingdom.getPlayerGrade(player).getName()+" "+ChatColor.WHITE+player.getName()+ChatColor.RED+" y a mis fin.");
							}
						}
	
						HumineKingdom.getPlayerKingdom(player).delet();
						menu.closePlayerMenu();
					} else if (HumineKingdom.getPlayerGrade(target) != null && HumineKingdom.getPlayerGrade(target).getName().equalsIgnoreCase(HumineKingdom.getPlayerKingdom(target).getKingGradeName())) { 
						for(OfflinePlayer op : HumineKingdom.getPlayerKingdom(player).getMembers()) {
							if (op.isOnline()) {
								op.getPlayer().sendMessage(ChatColor.RED+"Tentative de mutinerie de la part de "+ChatColor.WHITE+player.getName()+ChatColor.RED+" dejouer !");
								op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 5, 1);
							}
						}
						player.sendMessage(ChatColor.ITALIC+"(je crois que tu t'es grillé)");
					} else if (HumineKingdom.getPlayerGrade(player) != null && HumineKingdom.getPlayerGrade(player).containPermission(Permissions.INVITE.getPermission()) || player.getName().equalsIgnoreCase(target.getName())) {
						for (OfflinePlayer op : HumineKingdom.getPlayerKingdom(player).getMembers()) {
							if (op.isOnline()) {
								op.getPlayer().sendMessage(ChatColor.RED+target.getName()+" a quitter "+ChatColor.WHITE+HumineKingdom.getPlayerKingdom(player).getName()+ChatColor.RED+" pour de bon.");
								op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.ENTITY_FIREWORK_ROCKET_SHOOT, 5, 1);
							}
						}
						
						menu.closePlayerMenu();
						HumineKingdom.getPlayerKingdom(target).removeMember(target);
						if (!player.getName().equalsIgnoreCase(target.getName()))
							MenuList.membersMenu(player).openMenu();
						
					} else {
						player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
						player.sendMessage(ChatColor.RED+"Désolé l'ami ! Mais tu n'as pas les droits pour virer du monde d'ici...\n"+ChatColor.ITALIC+"(demande de l'aide a un adulte)");
					}
					
				}
			}
			
		}));
		
		menu.setButton(26, new Button(back, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				menu.closePlayerMenu();
				MenuList.membersMenu(player).openMenu();
			}
			
		}));
		
		MenuList.user.add(menu);
		return menu;		
	}
	
	//ADD GRADE FOR A PLAYER
	public static Menu addPlayerGrade(Player player, OfflinePlayer target) {
		closePlayerMenu(player);
		
		int size = HumineKingdom.getPlayerKingdom(player).getGrades().size();
		int c = 0;
		int multi = 1;
		for(int i = 1 ; i<size-2 ; i++){
			c++;
			if(c>=9){				
				c = 0;
				multi++;
			}
		}
		
		Menu menu = new Menu(player, ChatColor.BLUE+"- "+ChatColor.WHITE+"Grades a ajouter"+ChatColor.BLUE+" -", (multi+1)*9, false);
		
		ItemStack membre = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
		SkullMeta membrem = (SkullMeta) membre.getItemMeta();
		membrem.setDisplayName(ChatColor.GREEN+target.getName());
		membrem.setOwner(target.getName());
		membre.setItemMeta(membrem);
		
		ItemStack retour = new ItemStack(Material.BARRIER);
		ItemMeta retourm = retour.getItemMeta();
		retourm.setDisplayName(ChatColor.RED+"retour");
		retour.setItemMeta(retourm);
		
		
		ItemStack glass = new ItemStack(Material.MAGENTA_STAINED_GLASS);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, menu.getSize()/9);
		
		
		ArrayList<Grade> list = HumineKingdom.getPlayerKingdom(target).getGrades();
		ArrayList<String> lore;
		
		int slot = 0;
		for(int i = 0 ; i<list.size(); i++){
			if(!list.get(i).getName().equalsIgnoreCase(HumineKingdom.getPlayerKingdom(target).getKingGradeName())){
				
				lore = list.get(i).getPermissions();
				
				ItemStack nametag = new ItemStack(Material.NAME_TAG);
				ItemMeta nametagm = nametag.getItemMeta();
				nametagm.setDisplayName(ChatColor.BLUE+list.get(i).getName());
				nametagm.setLore(lore);
				nametag.setItemMeta(nametagm);
				
				final Grade g = list.get(i);
				
				menu.setButton(slot, new Button(nametag, new MenuItemListener() {
					
					@Override
					public void onItemClick() {
						if (HumineKingdom.getPlayerKingdom(player).getGrade(g.getName()) != null && HumineKingdom.getPlayerKingdom(target) == HumineKingdom.getPlayerKingdom(player)) {
							g.addMember(target);
							menu.closePlayerMenu();
							MenuList.playerProfilMenu(player, target).openMenu();
							player.sendMessage(target.getName()+ChatColor.GREEN+" a était promus "+g.getName());
							player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
							if (target.isOnline()) {
								target.getPlayer().sendMessage(ChatColor.GREEN+"Felicitation tu a était promus "+g.getName()+" !");
								target.getPlayer().playSound(target.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 1);
							}
						} else {
							MenuList.membersMenu(player).openMenu();
							player.sendMessage(ChatColor.RED+"Il y a comme un problème");
						}
					}
					
				}));
				slot++;
			}
		}
		
		menu.setButton(menu.getSize()-1, new Button(retour, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.playerProfilMenu(player, target).openMenu();
			}
			
		}));
		
		menu.setItem(menu.getSize()-9, membre);
		
		MenuList.user.add(menu);
		return menu;
	}
	
	//GRADE LIST MENU
	public static Menu gradeListMenu(Player player) {
		closePlayerMenu(player);
		
		int size = HumineKingdom.getPlayerKingdom(player).getGrades().size();
		int c = 0;
		int multi = 1;
		for(int i = 1 ; i<size-2 ; i++){
			c++;
			if(c>=9){				
				c = 0;
				multi++;
			}
		}
		
		Menu menu = new Menu(player, ChatColor.BLUE+ "- "+ChatColor.WHITE+"grades "+ChatColor.BLUE+"-", (multi+1)*9, false);
		int slot = 0;
		for (Grade grade : HumineKingdom.getPlayerKingdom(player).getGrades()) {
			if (!grade.getName().equalsIgnoreCase(HumineKingdom.getPlayerKingdom(player).getKingGradeName())) {
				
				ItemStack nametag = new ItemStack(Material.NAME_TAG);
				ItemMeta nametagm = nametag.getItemMeta();
				nametagm.setDisplayName(ChatColor.BLUE+grade.getName());
				nametag.setItemMeta(nametagm);
				
				menu.setButton(slot, new Button(nametag, new MenuItemListener() {
					
					@Override
					public void onItemClick() {
						menu.closePlayerMenu();
						
						player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
						if (HumineKingdom.getPlayerKingdom(player).getGrade(grade.getName()) != null) {
							MenuList.gradeMenu(player, grade).openMenu();							
						} else {
							player.sendMessage(ChatColor.RED+"Ce grade a été supprimé.");
							MenuList.gradeListMenu(player).openMenu();
						}
					}
					
				}));
				
				slot++;
				
			}
		}
		
		ItemStack glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		ItemStack ice = new ItemStack(Material.ICE);
		ItemMeta icem = ice.getItemMeta();
		icem.setDisplayName(ChatColor.AQUA+"rafraîchir la page");
		ice.setItemMeta(icem);
		
		menu.fillLine(glass, menu.getSize()/9);
		
		ItemStack quit = new ItemStack(Material.BARRIER);
		ItemMeta quitm = quit.getItemMeta();
		quitm.setDisplayName(ChatColor.RED+"Quitter");
		quit.setItemMeta(quitm);
		
		ItemStack add = new ItemStack(Material.LEGACY_BOOK_AND_QUILL);
		ItemMeta addm = add.getItemMeta();
		addm.setDisplayName(ChatColor.GREEN+"Ajouter un grade");
		add.setItemMeta(addm);
		
		menu.setButton(menu.getSize()-9, new Button(add, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				if (HumineKingdom.getPlayerGrade(player) != null && HumineKingdom.getPlayerGrade(player).containPermission(Permissions.GRADE.getPermission())) {
					player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
					menu.closePlayerMenu();
					player.sendMessage(ChatColor.DARK_PURPLE+"Tout d'abords il faut donner un nom a ce nouveau grade.");
					PlayerChatEvent.getNameOfGrade.add(player);
				} else {
					player.sendMessage(ChatColor.RED+"Désolé l'ami mais tu n'en as pas la permission. \n(Demande le l'aide a un adulte)");
					player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
				}
			}
			
		}));
		
		menu.setButton(menu.getSize()-1, new Button(quit, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				menu.closePlayerMenu();
				MenuList.mainKingdomMenu(player).openMenu();
			}
			
		}));
		
		menu.setButton(menu.getSize()-5, new Button(ice, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.BLOCK_SNOW_FALL, 5, 1);
				MenuList.gradeListMenu(player).openMenu();
			}
			
		}));
		
		MenuList.user.add(menu);
		return menu;
				
	}
	
	//GRADE MENU
	public static Menu gradeMenu(Player player, Grade grade) {
		closePlayerMenu(player);
		Menu menu = new Menu(player, ChatColor.BLUE+" - "+ChatColor.WHITE+grade.getName()+ChatColor.BLUE+" -", 3*9, false);
		
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 1);
		menu.fillLine(glass, 3);
		
		glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 2);
		
		ItemStack back = new ItemStack(Material.BARRIER);
		ItemMeta backm = back.getItemMeta();
		backm.setDisplayName(ChatColor.RED+"retour");
		back.setItemMeta(backm);
		
		ArrayList<String> lore = new ArrayList<String>();
		
		lore.add(ChatColor.GOLD+"~~~~~~~~~~~~~~~~~~~");
		
		for (String str : grade.getPermissions()) {
			lore.add("- "+str);
		}
		
		lore.add(ChatColor.GOLD+"~~~~~~~~~~~~~~~~~~~");
		
		ItemStack membre = new ItemStack(Material.NAME_TAG);
		ItemMeta membrem = membre.getItemMeta();
		membrem.setDisplayName(ChatColor.BLUE+grade.getName());
		membrem.setLore(lore);
		membre.setItemMeta(membrem);
		
		lore.clear();
		
		ItemStack paper = new ItemStack(Material.PAPER);
		ItemMeta paperm = paper.getItemMeta();
		paperm.setDisplayName(ChatColor.GREEN+"Modifier les permissions "+ChatColor.WHITE+grade.getName());
		paper.setItemMeta(paperm);
		
		ItemStack quitter = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta quitterm = quitter.getItemMeta();
		quitterm.setDisplayName(ChatColor.RED+"Supprimer le grade: "+ChatColor.WHITE+grade.getName());	
		quitter.setItemMeta(quitterm);
		
		menu.setButton(10, new Button(paper, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				if (HumineKingdom.getPlayerKingdom(player).getGrade(grade.getName()) != null) {
					if (HumineKingdom.getPlayerGrade(player) != null && HumineKingdom.getPlayerGrade(player).containPermission(Permissions.GRADE.getPermission()) && HumineKingdom.getPlayerGrade(player) != grade) {
						
						player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
						menu.closePlayerMenu();
						MenuList.permissionsMenu(player, grade).openMenu();
						
					}  else if (HumineKingdom.getPlayerGrade(player) == grade) {
						
						player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
						player.sendMessage(ChatColor.RED+"Désolé l'ami mais tu n'en as pas la permission de modifier ton propre grade.");
						
					} else {
						player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
						player.sendMessage(ChatColor.RED+"Désolé l'ami mais tu n'en as pas la permission. \n(Demande le l'aide a un adulte)");
					}
				} else {
					MenuList.gradeListMenu(player).openMenu();
				}
			}
			
		}));
		
		menu.setItem(13, membre);
		
		menu.setButton(16, new Button(quitter, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				if (HumineKingdom.getPlayerKingdom(player).getGrade(grade.getName()) != null) {
					if (HumineKingdom.getPlayerGrade(player) != null && HumineKingdom.getPlayerGrade(player).containPermission(Permissions.GRADE.getPermission()) && HumineKingdom.getPlayerGrade(player) != grade) {
						player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
						menu.closePlayerMenu();
						player.sendMessage(ChatColor.RED+"Le grade "+ChatColor.WHITE+grade.getName()+ChatColor.RED+" a était supprimé");
						grade.getKingdom().removeGrade(grade);
						grade.delet();
						MenuList.gradeListMenu(player).openMenu();
						
					}  else if (HumineKingdom.getPlayerGrade(player) == grade) {
						
						player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
						player.sendMessage(ChatColor.RED+"Désolé l'ami mais tu n'en as pas la permission de modifier ton propre grade.");
						
					} else {
						player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
						player.sendMessage(ChatColor.RED+"Désolé l'ami mais tu n'en as pas la permission. \n(Demande le l'aide a un adulte)");
					}
				} else {
					MenuList.gradeListMenu(player).openMenu();
				}
			}
			
		}));
		
		menu.setButton(26, new Button(back, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				menu.closePlayerMenu();
				MenuList.gradeListMenu(player).openMenu();
			}
			
		}));
		
		MenuList.user.add(menu);
		
		return menu;
	}
	
	//PERMISSION MENU
	public static Menu permissionsMenu(Player player, Grade grade) {
		closePlayerMenu(player);
		
		int size = HumineKingdom.getPlayerKingdom(player).getGrades().size();
		int c = 0;
		int multi = 1;
		for(int i = 1 ; i<size-2 ; i++){
			c++;
			if(c>=9){				
				c = 0;
				multi++;
			}
		}
		
		Menu menu = new Menu(player, ChatColor.GREEN+"- "+ChatColor.WHITE+"Liste des permissions"+ChatColor.GREEN+" -", (multi+1)*9, false);		
		
		ItemStack igrade = new ItemStack(Material.NAME_TAG);
		ItemMeta igradem = igrade.getItemMeta();
		igradem.setDisplayName(ChatColor.BLUE+grade.getName());
		igrade.setItemMeta(igradem);
		
		ItemStack retour = new ItemStack(Material.BARRIER);
		ItemMeta retourm = retour.getItemMeta();
		retourm.setDisplayName(ChatColor.RED+"retour");
		retour.setItemMeta(retourm);
		
		
		ItemStack glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, menu.getSize()/9);
		
		for (int i = 0 ; i<Permissions.getAllPermissions().size() ; i++) {
			
			ArrayList<String> lore = new ArrayList<String>();
			
			for (String str : Permissions.getAllPermissions().get(i).getLore())
				lore.add(str);
			
			lore.add("   ");
			
			if (grade.getPermissions().contains(Permissions.getAllPermissions().get(i).getPermission())) {
				lore.add(ChatColor.RED+"Retirer la permission");		
			} else {
				lore.add(ChatColor.GREEN+"Ajouter la permission");
			}
			
			ItemStack paper = new ItemStack(Material.PAPER);
			ItemMeta paperm = paper.getItemMeta();
			paperm.setDisplayName(ChatColor.BLUE+"- "+Permissions.getAllPermissions().get(i).getName()+" -");
			paperm.setLore(lore);
			paper.setItemMeta(paperm);
			
			final int rank = i;
			
			menu.setButton(rank, new Button(paper, new MenuItemListener() {
				
				@Override
				public void onItemClick() {
					if (HumineKingdom.getPlayerKingdom(player).getGrade(grade.getName()) != null) {
					
						if (HumineKingdom.getPlayerGrade(player).containPermission(Permissions.GRADE.getPermission())) {
							
							boolean contain = false;
							
							for (String str : grade.getPermissions()) {
								if (str.equalsIgnoreCase(Permissions.getAllPermissions().get(rank).getPermission())) {
									contain = true;
								}
							}
							
							if (!contain) {
								grade.addPermission(Permissions.getAllPermissions().get(rank).getPermission());
							} else {
								grade.removePermission(Permissions.getAllPermissions().get(rank).getPermission());
							}
							
							player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
							menu.closePlayerMenu();
							MenuList.gradeMenu(player, grade).openMenu();
						}else {
							player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
							player.sendMessage(ChatColor.RED+"Désolé l'ami mais tu n'en as pas la permission. \n(Demande le l'aide a un adulte)");
						}
					
					} else {
						MenuList.gradeListMenu(player).openMenu();
					}
					
				}
				
			}));
			
		}
		
		menu.setItem(menu.getSize()-9, igrade);
		menu.setButton(menu.getSize()-1, new Button(retour, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				menu.closePlayerMenu();
				MenuList.gradeMenu(player, grade).openMenu();
			}
			
		}));
		
		MenuList.user.add(menu);
		return menu;
	}
	
	//SHIELD MENU
	public static Menu GestionMenu(Player player) {
		
		closePlayerMenu(player);
		
		Menu menu = new Menu(player, ChatColor.BLUE+ "- "+ChatColor.WHITE+"mes generateurs "+ChatColor.BLUE+"-", 3*9, false);
		
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 1);
		menu.fillLine(glass, 3);
		
		glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, 2);
		
		ItemStack back = new ItemStack(Material.BARRIER);
		ItemMeta backm = back.getItemMeta();
		backm.setDisplayName(ChatColor.RED+"retour");
		back.setItemMeta(backm);
		
		ArrayList<String> lore = new ArrayList<String>();
		
		ItemStack beacon = new ItemStack(Material.BEACON);
		ItemMeta beaconm = beacon.getItemMeta();
		beaconm.setDisplayName(ChatColor.AQUA+"Info Generateurs");
		lore.add(ChatColor.GOLD+"---------------------------");
		lore.add(ChatColor.LIGHT_PURPLE+"nombre de generateur(s): "+ChatColor.WHITE+HumineKingdom.getPlayerKingdom(player).getShieldGenerators().size());
		lore.add(ChatColor.GOLD+"---------------------------");
		beaconm.setLore(lore);
		beacon.setItemMeta(beaconm);
		
		lore.clear();
		
		ItemStack saveCons = new ItemStack(Material.EMERALD);
		ItemMeta saveConsm = saveCons.getItemMeta();
		saveConsm.setDisplayName(ChatColor.GREEN+"liste des plans de construction");
		lore.add("");
		saveConsm.setLore(lore);
		saveCons.setItemMeta(saveConsm);
		
		lore.clear();
		
		menu.setItem(13, beacon);
		menu.setButton(10, new Button(saveCons, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				menu.closePlayerMenu();
				MenuList.shematicMenu(player).openMenu();
			}
			
		}));
		
		
		menu.setButton(26, new Button(back, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				menu.closePlayerMenu();
				MenuList.mainKingdomMenu(player).openMenu();
			}
			
		}));
		
		MenuList.user.add(menu);
		return menu;
	}
	
	//SHEMATIC MENU
	public static Menu shematicMenu(Player player) {
		closePlayerMenu(player);
		int size = HumineKingdom.getPlayerKingdom(player).getMembers().size();
		int c = 0;
		int multi = 1;
		for(int i = 1 ; i<size-2 ; i++){
			c++;
			if(c>=9){				
				c = 0;
				multi++;
			}
		}
		
		Menu menu = new Menu(player, ChatColor.DARK_GREEN+"- Mes plans -", (multi+1)*9, false);
		
		ArrayList<String> lore = new ArrayList<String>();
		
		ItemStack quit = new ItemStack(Material.BARRIER);
		ItemMeta quitm = quit.getItemMeta();
		quitm.setDisplayName(ChatColor.RED+"Quitter");
		quit.setItemMeta(quitm);
		
		lore.clear();
		
		ItemStack ice = new ItemStack(Material.ICE);
		ItemMeta icem = ice.getItemMeta();
		icem.setDisplayName(ChatColor.AQUA+"rafraîchir la page");
		ice.setItemMeta(icem);
		
		ItemStack add = new ItemStack(Material.LEGACY_BOOK_AND_QUILL);
		ItemMeta addm = add.getItemMeta();
		addm.setDisplayName(ChatColor.GREEN+"Ajouter un plans");
		add.setItemMeta(addm);
		
		ItemStack glass = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE, 1);
		ItemMeta glassM = glass.getItemMeta();
		glassM.setDisplayName(" ");
		glass.setItemMeta(glassM);
		
		menu.fillLine(glass, menu.getSize()/9);
		
		int slot = 0;
		for (Shematic s : HumineKingdom.getPlayerKingdom(player).getShematics()) {
			
			ItemStack item = new ItemStack(Material.PAPER, 1);
			ItemMeta itemm = item.getItemMeta();
			itemm.setDisplayName(ChatColor.GREEN+s.getName());
			itemm.setLore(lore);
			lore.clear();
			item.setItemMeta(itemm);
			menu.setButton(slot, new Button(item, new MenuItemListener() {
				
				@Override
				public void onItemClick() {
						menu.closePlayerMenu();
					 	s.rebuild();
					 	player.sendMessage(ChatColor.GREEN+"Reconstruction");
				}
				
			}));
			
			slot++;
		}
		
		menu.setButton(menu.getSize()-1, new Button(quit, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
				MenuList.mainKingdomMenu(player).openMenu();
				MenuList.user.remove(menu);
			}
			
		}));
		
		menu.setButton(menu.getSize()-9, new Button(add, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();			
				
				if (HumineKingdom.getPlayerGrade(player) != null && HumineKingdom.getPlayerGrade(player).containPermission(Permissions.INVITE.getPermission())) {
	
					player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
					PlayerClick.getBeacon.add(player);
					player.sendMessage(ChatColor.GREEN+"Besoin d'un nouveau plans de construction ? Clique sur un de tes generateurs de boucliers !");
				}else{
					  player.sendMessage(ChatColor.RED+"Désolé l'ami ! Mais tu n'as pas les droits pour inviter du creer des plans ici...\n"+ChatColor.ITALIC+"(demande de l'aide a un adulte)");
					  player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
				}
					
				MenuList.user.remove(menu);
				
			}
			
		}));
		
		menu.setButton(menu.getSize()-5, new Button(ice, new MenuItemListener() {
			
			@Override
			public void onItemClick() {
				menu.closePlayerMenu();
				player.playSound(player.getLocation(), Sound.BLOCK_SNOW_FALL, 5, 1);
				MenuList.membersMenu(player).openMenu();
			}
			
		}));
		
		MenuList.user.add(menu);
		return menu;
	}
	
	
	
	public static void closePlayerMenu(Player player) {
		for(int i = 0 ; i<user.size() ; i++) {
			if (user.get(i).getPlayer().getName().equalsIgnoreCase(player.getName())) {
				user.get(i).closePlayerMenu();
				MenuList.user.remove(user.get(i));
				i--;
			}
		}
	}

	
}
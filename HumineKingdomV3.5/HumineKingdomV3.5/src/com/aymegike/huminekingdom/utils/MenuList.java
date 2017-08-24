package com.aymegike.huminekingdom.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.objets.Grade;
import com.aymegike.huminekingdom.utils.objets.Kingdom;
import com.aymegike.huminekingdom.utils.objets.Menu;
import com.aymegike.huminekingdom.utils.objets.Zone;

import net.md_5.bungee.api.ChatColor;

public class MenuList {
	
	//NO KINGDOM MENU
	public static Menu noKingdomMenu(){
		Menu menu = new Menu(ChatColor.DARK_PURPLE+"Vous n'avez pas de kingdom !", 3*9, null);
		
		ArrayList<String> lore = new ArrayList<String>();
		
		ItemStack emerald = new ItemStack(Material.EMERALD);
		ItemMeta emeraldM = emerald.getItemMeta();
		emeraldM.setDisplayName(ChatColor.GREEN+"INFORMATION !");
		lore.add(ChatColor.DARK_PURPLE+"Afin de devenir plus puissant, vous");
		lore.add(ChatColor.DARK_PURPLE+"pouvez créer votre royaume et recruter");
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
		
		for(int i = 0 ; i<menu.getSize() ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
		
		for(int i = 9 ; i<18 ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 10);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
		
		menu.addItem(emerald, 13);
		menu.addItem(accept, 10);
		menu.addItem(decline, 16);
		
		
		return menu;
	}
	
	//Member list
	@SuppressWarnings("deprecation")
	public static Menu MemberMenu(Kingdom k){
		
		int size = k.getAllMember().size();
		int c = 0;
		int multi = 1;
		for(int i = 1 ; i<size-2 ; i++){
			c++;
			if(c>=9){				
				c = 0;
				multi++;
			}
		}
		Menu menu = new Menu(ChatColor.DARK_PURPLE+"Membres "+ChatColor.WHITE+k.getName(), (multi+1)*9, null);
		ArrayList<String> lore = new ArrayList<String>();
		ItemStack quit = new ItemStack(Material.BARRIER);
		ItemMeta quitm = quit.getItemMeta();
		quitm.setDisplayName(ChatColor.RED+"Quitter");
		quit.setItemMeta(quitm);
		
		ItemStack add = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta addm = add.getItemMeta();
		addm.setDisplayName(ChatColor.GREEN+"Ajouter un joueur");
		add.setItemMeta(addm);
		
		File member = new File("./plugins/HumineKingdom/Kingdom/"+k.getName()+"/member.yml");
		int slot = 0;
		try(FileInputStream fis = new FileInputStream(member)){
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(fis);
			while(sc.hasNext()){
				String name = sc.nextLine();
				ItemStack membre = new ItemStack(Material.SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
				SkullMeta membrem = (SkullMeta) membre.getItemMeta();
				if(Bukkit.getPlayer(name) == null){
					membrem.setDisplayName(ChatColor.GREEN+name);
					membrem.setOwner(Bukkit.getOfflinePlayer(name).getName());
				}else{
					membrem.setDisplayName(ChatColor.GREEN+Bukkit.getPlayer(name).getName());
					membrem.setOwner(Bukkit.getPlayer(name).getName());
				}
				if(HumineKingdom.getPlayerGrade(Bukkit.getOfflinePlayer(name)) != null){
					lore.add(ChatColor.DARK_PURPLE+"Grade: "+ChatColor.WHITE+HumineKingdom.getPlayerGrade(Bukkit.getOfflinePlayer(name)).getName());
				}
				membrem.setLore(lore);
				lore.clear();
				membre.setItemMeta(membrem);
				menu.addItem(membre, slot);
				slot++;
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
		for(int i = menu.getSize()-9 ; i<menu.getSize() ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 10);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
		
		menu.addItem(quit, menu.getSize()-1);
		menu.addItem(add, menu.getSize()-9);
		return menu;
	}
	
	//MAIN KINGDOM 
	public static Menu MainKingdomMenu(Kingdom k, Player p){
		Menu menu = new Menu(ChatColor.DARK_PURPLE+"Votre Royaume ! "+ChatColor.WHITE+k.getName(), 6*9, null);
		
		ItemStack quit = new ItemStack(Material.BARRIER);
		ItemMeta quitm = quit.getItemMeta();
		quitm.setDisplayName(ChatColor.RED+"Quitter");
		quit.setItemMeta(quitm);
		
		ItemStack gestion = new ItemStack(Material.COMPASS);
		ItemMeta gestionm = gestion.getItemMeta();
		gestionm.setDisplayName(ChatColor.DARK_GRAY+"Gestion de kingdom");
		gestion.setItemMeta(gestionm);
		
		ItemStack membre = new ItemStack(Material.SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
		SkullMeta membrem = (SkullMeta) membre.getItemMeta();
		membrem.setDisplayName(ChatColor.GREEN+p.getName());
		membrem.setOwner(p.getName());
		membre.setItemMeta(membrem);
		
		
		ItemStack grade = new ItemStack(Material.BANNER);
		ItemMeta gradem = grade.getItemMeta();
		gradem.setDisplayName(ChatColor.BLUE+"Gestion des grades");
		grade.setItemMeta(gradem);
		
		ItemStack classement = new ItemStack(Material.DRAGON_EGG);
		ItemMeta classementm = classement.getItemMeta();
		classementm.setDisplayName(ChatColor.DARK_PURPLE+"Classement");
		classement.setItemMeta(classementm);
		
		
		for(int i = 0 ; i<menu.getSize() ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
		
		for(int i = 9 ; i<18 ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 10);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
		
		for(int i = 36 ; i<45 ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 10);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
		
		menu.addItem(gestion, 10);
		menu.addItem(membre, 13);
		menu.addItem(grade, 16);
		menu.addItem(classement, 40);
		menu.addItem(quit, 44);
		
		
		
		return menu;
	}
	
	//MENU INVITE
	public static Menu InvitationMenu(Kingdom k, Player p){
Menu menu = new Menu(ChatColor.GOLD+"Vous avez reçu une invitation !", 3*9, null);
		
		ArrayList<String> lore = new ArrayList<String>();
		
		ItemStack emerald = new ItemStack(Material.EMERALD);
		ItemMeta emeraldM = emerald.getItemMeta();
		emeraldM.setDisplayName(ChatColor.WHITE+p.getName()+ChatColor.GREEN+" vous invite pour rejoindre "+ChatColor.WHITE+k.getName());
		lore.add("");
		lore.add(ChatColor.GOLD+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		lore.add(ChatColor.DARK_PURPLE+"Afin de devenir plus puissant, vous");
		lore.add(ChatColor.DARK_PURPLE+"pouvez rejoindre "+ChatColor.WHITE+k.getName()+ChatColor.DARK_PURPLE+" afin de ");
		lore.add(ChatColor.DARK_PURPLE+"dominer le monde de "+ChatColor.WHITE+"HumineCraft"+ChatColor.DARK_PURPLE+" !");
		lore.add(ChatColor.GOLD+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		emeraldM.setLore(lore);
		emerald.setItemMeta(emeraldM);
		
		lore.clear();
		
		lore.add(ChatColor.DARK_PURPLE+"Cliquez pour valider");
		
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
		
		for(int i = 0 ; i<menu.getSize() ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
		
		for(int i = 9 ; i<18 ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 10);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
		
		menu.addItem(emerald, 13);
		menu.addItem(accept, 10);
		menu.addItem(decline, 16);
		
		return menu;
	}
	
	//PLAYER MENU
	public static Menu PlayerMenu(Kingdom k, OfflinePlayer p){
		Menu menu = new Menu(ChatColor.DARK_PURPLE+"Joueur", 3*9, k);
		
		ArrayList<String> lore = new ArrayList<String>(); 
		
		ItemStack quite = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta quitem = quite.getItemMeta();
		quitem.setDisplayName(ChatColor.RED+"Exclure");
		quite.setItemMeta(quitem);
		
		ItemStack grade = new ItemStack(Material.NAME_TAG);
		ItemMeta gradem = grade.getItemMeta();
		gradem.setDisplayName(ChatColor.BLUE+"Modifier son grade");
		grade.setItemMeta(gradem);
		
		ItemStack membre = new ItemStack(Material.SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
		SkullMeta membrem = (SkullMeta) membre.getItemMeta();
		membrem.setDisplayName(ChatColor.GREEN+p.getName());
		membrem.setOwner(p.getName());
		
		if(HumineKingdom.getPlayerGrade(p) != null){
			lore.add(ChatColor.DARK_PURPLE+"Grade: "+ChatColor.WHITE+HumineKingdom.getPlayerGrade(p).getName());
		}
		lore.add("");
		lore.add(ChatColor.RED+"retour");
		membrem.setLore(lore);
		lore.clear();
		membre.setItemMeta(membrem);
		
		for(int i = 0 ; i<menu.getSize() ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
		
		for(int i = 9 ; i<18 ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 10);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
		
		menu.addItem(membre, 13);
		menu.addItem(quite, 16);
		menu.addItem(grade, 10);
		
		return menu;
		
	}
	//GRADE FOCUS
	public static Menu gradeMenu(Grade g){
		Menu menu = new Menu(ChatColor.BLUE+"Grade", 3*9, null);
		
		ItemStack quite = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta quitem = quite.getItemMeta();
		quitem.setDisplayName(ChatColor.RED+"Supprimer");
		quite.setItemMeta(quitem);
		
		ItemStack perms = new ItemStack(Material.PAPER);
		ItemMeta permsm = perms.getItemMeta();
		permsm.setDisplayName(ChatColor.BLUE+"Definir les permissions");
		perms.setItemMeta(permsm); 
		
		ArrayList<String> lore = new ArrayList<String>();
		if(g.getAllPerms().size() > 0){
			
			lore.add("");
			lore.add(ChatColor.GOLD+"~~~~~~~~~~~~~~~~~~~");
			for(String str : g.getAllPerms()){
				lore.add(ChatColor.DARK_PURPLE+str);
			}
			lore.add(ChatColor.GOLD+"~~~~~~~~~~~~~~~~~~~");
		
		}
		
		lore.add("");
		lore.add(ChatColor.RED+"retour");
		
		ItemStack grade = new ItemStack(Material.NAME_TAG);
		ItemMeta gradem = grade.getItemMeta();
		gradem.setDisplayName(ChatColor.BLUE+g.getName());
		gradem.setLore(lore);
		grade.setItemMeta(gradem); 
		
		for(int i = 0 ; i<menu.getSize() ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
		
		for(int i = 9 ; i<18 ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 10);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
		
		menu.addItem(perms, 10);
		menu.addItem(grade, 13);
		menu.addItem(quite, 16);
		
		return menu;
	}
	
	//PERMS LIST
	public static Menu addPermsListMenu(Grade grade){
		Menu menu = new Menu(ChatColor.DARK_PURPLE+"Ajouter une permission", 2*9, grade.getKingdom());
		
		ArrayList<String> lore = new ArrayList<String>();
		
		ItemStack build = new ItemStack(Material.PAPER);
		ItemMeta buildm = build.getItemMeta();
		buildm.setDisplayName(ChatColor.YELLOW+"kingdom.build");
		lore.add("");
		lore.add(ChatColor.BLUE+"description:");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"Donne l'autorisation à vos joueurs");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"de construire dans les zones protegées");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"par vos balises.");
		lore.add("");
		lore.add(ChatColor.GOLD+"Cliquez pour choisir.");
		buildm.setLore(lore);
		build.setItemMeta(buildm);
		
		lore.clear();
		
		ItemStack destruct = new ItemStack(Material.PAPER);
		ItemMeta destructm = destruct.getItemMeta();
		destructm.setDisplayName(ChatColor.YELLOW+"kingdom.break");
		lore.add("");
		lore.add(ChatColor.BLUE+"description:");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"Donne l'autorisation a vos joueurs");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"de détruire dans les zones protegées");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"par vos balises.");
		lore.add("");
		lore.add(ChatColor.GOLD+"Cliquez pour choisir.");
		destructm.setLore(lore);
		destruct.setItemMeta(destructm);
		
		lore.clear();
		
		ItemStack gestion = new ItemStack(Material.PAPER);
		ItemMeta gestionm = gestion.getItemMeta();
		gestionm.setDisplayName(ChatColor.YELLOW+"kingdom.gestion.invite");
		lore.add("");
		lore.add(ChatColor.BLUE+"description:");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"Donne l'autorisation a vos joueurs");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"d'inviter d'autres joueurs à rejoindre");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"votre royaume.");
		lore.add("");
		lore.add(ChatColor.GOLD+"Cliquez pour choisir.");
		gestionm.setLore(lore);
		gestion.setItemMeta(gestionm);
		
		lore.clear();
		
		ItemStack invite = new ItemStack(Material.PAPER);
		ItemMeta invitem = invite.getItemMeta();
		invitem.setDisplayName(ChatColor.YELLOW+"kingdom.gestion.invite.newplayer");
		lore.add("");
		lore.add(ChatColor.BLUE+"description:");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"Donne l'autorisation a vos joueurs");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"d'inviter les nouveau joueurs à");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"atterrire sur vos terres.");
		lore.add("");
		lore.add(ChatColor.GOLD+"Cliquez pour choisir.");
		invitem.setLore(lore);
		invite.setItemMeta(invitem);
		
		lore.clear();
		
		ItemStack sgrade = new ItemStack(Material.PAPER);
		ItemMeta sgradem = sgrade.getItemMeta();
		sgradem.setDisplayName(ChatColor.YELLOW+"kingdom.gestion.grade");
		lore.add("");
		lore.add(ChatColor.BLUE+"description:");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"Donne l'autorisation a vos joueurs");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"de modifier le grade des autres");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"joueurs de votre royaume.");
		lore.add("");
		lore.add(ChatColor.GOLD+"Cliquez pour choisir.");
		sgradem.setLore(lore);
		sgrade.setItemMeta(sgradem);
		
		lore.clear();
		
		ItemStack perm = new ItemStack(Material.PAPER);
		ItemMeta permm = perm.getItemMeta();
		permm.setDisplayName(ChatColor.YELLOW+"kingdom.gestion.perm");
		lore.add("");
		lore.add(ChatColor.BLUE+"description:");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"Donne l'autorisation a vos joueurs");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"de modifier les permissions des");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"différents grades de votre royaume.");
		lore.add("");
		lore.add(ChatColor.GOLD+"Cliquez pour choisir.");
		permm.setLore(lore);
		perm.setItemMeta(permm);
		
		lore.clear();
		
		ItemStack create = new ItemStack(Material.PAPER);
		ItemMeta createm = create.getItemMeta();
		createm.setDisplayName(ChatColor.YELLOW+"kingdom.gestion.create");
		lore.add("");
		lore.add(ChatColor.BLUE+"description:");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"Donne l'autorisation a vos joueurs");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"de créer et de supprimer des grades.");
		lore.add("");
		lore.add(ChatColor.GOLD+"Cliquez pour choisir.");
		createm.setLore(lore);
		create.setItemMeta(createm);
		
		lore.clear();
		
		ItemStack chat = new ItemStack(Material.PAPER);
		ItemMeta chatm = chat.getItemMeta();
		chatm.setDisplayName(ChatColor.YELLOW+"kingdom.chat");
		lore.add("");
		lore.add(ChatColor.BLUE+"description:");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"Donne l'autorisation a vos joueurs");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"de lire et de publier des messages");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"lisible uniquement par les membres");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"de votre Royaume. Pour ce faire il");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"doit placer "+ChatColor.WHITE+"*"+ChatColor.DARK_PURPLE+" devant le message");
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"en question.");
		lore.add("");
		lore.add(ChatColor.GOLD+"Cliquez pour choisir.");
		chatm.setLore(lore);
		chat.setItemMeta(chatm);
		
		for(int i = menu.getSize()-9 ; i<menu.getSize()-1 ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 10);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
		
		ItemStack emerald = new ItemStack(Material.EMERALD);
		ItemMeta emeraldm = emerald.getItemMeta();
		emeraldm.setDisplayName(ChatColor.BLUE+grade.getName());
		emerald.setItemMeta(emeraldm);
		
		ItemStack quit = new ItemStack(Material.BARRIER);
		ItemMeta quitm = quit.getItemMeta();
		quitm.setDisplayName(ChatColor.RED+"Quitter");
		quit.setItemMeta(quitm);
		
		menu.addItem(emerald, 13);
		
		menu.addItem(build, 0);
		menu.addItem(destruct, 1);
		menu.addItem(gestion, 2);
		menu.addItem(sgrade, 3);
		menu.addItem(perm, 4);
		menu.addItem(create, 5);
		menu.addItem(chat, 6);
		menu.addItem(invite, 7);
		
		menu.addItem(quit, menu.getSize()-1);
		
		return menu;
	}
	
	//GRADE LIST
	public static Menu gradeListMenu(Kingdom k){
		File file = new File(k.getKingdomFile()+"/grade");
		File[] chil = file.listFiles();
		int size = chil.length;
		int c = 0;
		int multi = 1;
		for(int i = 1 ; i<size-2 ; i++){
			c++;
			if(c>=9){				
				c = 0;
				multi++;
			}
		}
		Menu menu = new Menu(ChatColor.DARK_PURPLE+"Liste des grades", (multi+1)*9, k);
		
		int slot = 0;
		for(int i = 0 ; i<chil.length ; i++){
			if(!chil[i].getName().equalsIgnoreCase("King") && !chil[i].getName().equalsIgnoreCase("Aucun")){
				
				ItemStack nametag = new ItemStack(Material.NAME_TAG);
				ItemMeta nametagm = nametag.getItemMeta();
				nametagm.setDisplayName(ChatColor.BLUE+chil[i].getName());
				nametag.setItemMeta(nametagm);
				
				menu.addItem(nametag, slot);
				slot++;
			}
		}
		
		for(int i = menu.getSize()-9 ; i<menu.getSize()-1 ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 10);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
		
		ItemStack quit = new ItemStack(Material.BARRIER);
		ItemMeta quitm = quit.getItemMeta();
		quitm.setDisplayName(ChatColor.RED+"Quitter");
		quit.setItemMeta(quitm);
		
		ItemStack add = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta addm = add.getItemMeta();
		addm.setDisplayName(ChatColor.GREEN+"Ajouter un grade");
		add.setItemMeta(addm);
		
		menu.addItem(add, menu.getSize()-9);
		menu.addItem(quit, menu.getSize()-1);
		
		
		
		return menu;
		
	}
	
	public static Menu choseGrade(Kingdom k, OfflinePlayer p){
		
		File file = new File(k.getKingdomFile()+"/grade");
		File[] chil = file.listFiles();
		int size = chil.length;
		int c = 0;
		int multi = 1;
		for(int i = 1 ; i<size-2 ; i++){
			c++;
			if(c>=9){				
				c = 0;
				multi++;
			}
		}
		Menu menu = new Menu(ChatColor.DARK_PURPLE+"Choisir un grade", (multi+1)*9, k);
		
		ItemStack membre = new ItemStack(Material.SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
		SkullMeta membrem = (SkullMeta) membre.getItemMeta();
		membrem.setDisplayName(ChatColor.GREEN+p.getName());
		membrem.setOwner(p.getName());
		membre.setItemMeta(membrem);
		
		ItemStack retour = new ItemStack(Material.BARRIER);
		ItemMeta retourm = retour.getItemMeta();
		retourm.setDisplayName(ChatColor.RED+"retour");
		retour.setItemMeta(retourm);
		
		int slot = 0;
		for(int i = 0 ; i<chil.length ; i++){
			System.out.println(chil[i].getName());
			if(!chil[i].getName().equalsIgnoreCase("King") && !chil[i].getName().equalsIgnoreCase("Aucun")){
				
				ItemStack nametag = new ItemStack(Material.NAME_TAG);
				ItemMeta nametagm = nametag.getItemMeta();
				nametagm.setDisplayName(ChatColor.BLUE+chil[i].getName());
				nametag.setItemMeta(nametagm);
				
				menu.addItem(nametag, slot);
				slot++;
			}
		}
		
		for(int i = menu.getSize()-9 ; i<menu.getSize()-1 ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 10);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
		
		menu.addItem(retour, menu.getSize()-1);
		menu.addItem(membre, menu.getSize()-5);
		
		return menu;
		
	}
	
	public static Menu permsListMenu(Kingdom k, Grade g){
		int size = 0;
		
		ArrayList<String> perms = new ArrayList<String>();
		
		try(FileInputStream fis = new FileInputStream(g.getPermFile())){
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(fis);
			while(sc.hasNext()){
				size++;
				perms.add(sc.nextLine());
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		int c = 0;
		int multi = 1;
		for(int i = 1 ; i<size-2 ; i++){
			c++;
			if(c>=9){				
				c = 0;
				multi++;
			}
		}
		
		Menu menu = new Menu(ChatColor.DARK_PURPLE+"Liste des permissions", (multi+1)*9, k);
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.RED+"cliquez pour supprimer");
		int slot = 0;
		for(String name : perms){
			ItemStack it = new ItemStack(Material.PAPER);
			ItemMeta itm = it.getItemMeta();
			itm.setDisplayName(ChatColor.YELLOW+name);
			itm.setLore(lore);
			it.setItemMeta(itm);
			menu.addItem(it, slot);
			slot++;
		}
		
		ItemStack emerald = new ItemStack(Material.EMERALD);
		ItemMeta emeraldm = emerald.getItemMeta();
		emeraldm.setDisplayName(ChatColor.BLUE+g.getName());
		emerald.setItemMeta(emeraldm);
		
		ItemStack retour = new ItemStack(Material.BARRIER);
		ItemMeta retourm = retour.getItemMeta();
		retourm.setDisplayName(ChatColor.RED+"retour");
		retour.setItemMeta(retourm);
		
		ItemStack add = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta addm = add.getItemMeta();
		addm.setDisplayName(ChatColor.BLUE+"Ajouter une permission");
		add.setItemMeta(addm);
		
		for(int i = menu.getSize()-9 ; i<menu.getSize()-1 ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 10);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
		
		menu.addItem(add, menu.getSize()-9);
		menu.addItem(retour, menu.getSize()-1);
		menu.addItem(emerald, menu.getSize()-5);
		
		return menu;
		
	}
	
	public static Menu gestionMenu(Kingdom k, Player p){
		
		Menu menu = new Menu(ChatColor.GRAY+"Gestion "+k.getName(), 3*9, k);
		
		ItemStack quit = new ItemStack(Material.BARRIER);
		ItemMeta quitm = quit.getItemMeta();
		quitm.setDisplayName(ChatColor.RED+"retour");
		quit.setItemMeta(quitm);
		
		ArrayList<Zone> zones = new ArrayList<Zone>();
		for(Zone zone : ZoneManager.getAllZones()){
			if(zone.getKingdom() == k){
				zones.add(zone);
			}
		}
		
		ArrayList<String> lore = new ArrayList<String>();
		
		lore.add(ChatColor.DARK_PURPLE+"nombre actuelle: "+ChatColor.WHITE+zones.size());
		
		ItemStack gestion = new ItemStack(Material.BEACON);
		ItemMeta gestionm = gestion.getItemMeta();
		gestionm.setDisplayName(ChatColor.AQUA+"Liste des Balises");
		gestionm.setLore(lore);
		gestion.setItemMeta(gestionm);
		
		ItemStack membre = new ItemStack(Material.SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
		
		if(HumineKingdom.getPlayerGrade(p).getName().equalsIgnoreCase("King")){
			SkullMeta membrem = (SkullMeta) membre.getItemMeta();
			membrem.setDisplayName(ChatColor.RED+"Suprimer votre royaume");
			membre.setItemMeta(membrem);
		}else{
			SkullMeta membrem = (SkullMeta) membre.getItemMeta();
			membrem.setDisplayName(ChatColor.RED+"Quitter le royaume");
			membre.setItemMeta(membrem);
		}
		
		for(int i = 0 ; i<menu.getSize() ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
		
		for(int i = 9 ; i<18 ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 10);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
		
		menu.addItem(gestion, 10);
		menu.addItem(membre, 13);
		menu.addItem(quit, 16);
		
		return menu;
		
	}
	
	public static Menu BeaconList(Kingdom k){
		int size = 0;
		
		ArrayList<Zone> zones = new ArrayList<Zone>(); 
		
		for(Zone zone : ZoneManager.getAllZones()){
			if(zone.getKingdom().getName().equalsIgnoreCase(k.getName())){
				size++;
				zones.add(zone);
			}
		}
		
		int c = 0;
		int multi = 1;
		for(int i = 1 ; i<size-2 ; i++){
			c++;
			if(c>=9){				
				c = 0;
				multi++;
			}
		}
		
		Menu menu = new Menu(ChatColor.AQUA+"Liste des Balises", (multi+1)*9, k);
		
		
		
		int slot = 0;
		
		for(@SuppressWarnings("unused") Zone zone : zones){
			
			ItemStack balise = new ItemStack(Material.BEACON);
			ItemMeta balisem = balise.getItemMeta();
			Random r = new Random();
			int color = r.nextInt(9)+1;
			balisem.setDisplayName("§"+color+"❤");
			balise.setItemMeta(balisem);
			menu.addItem(balise, slot);
			slot++;
		}
		
		ItemStack retour = new ItemStack(Material.BARRIER);
		ItemMeta retourm = retour.getItemMeta();
		retourm.setDisplayName(ChatColor.RED+"retour");
		retour.setItemMeta(retourm);
		
		for(int i = menu.getSize()-9 ; i<menu.getSize()-1 ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 10);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
		
		menu.addItem(retour, menu.getSize()-1);
		
		return menu;
		
	}
	
	//Ranking list
	@SuppressWarnings("deprecation")
	public static Menu rankingMenu(Player player){			
		int top = 3; //number of kingdom in the ranking		
		
		Menu menu = new Menu(ChatColor.DARK_PURPLE+"Classement", 2*9, null);
		
		for(int i = menu.getSize()-9 ; i<menu.getSize()-1 ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 10);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
		
		ArrayList<String> lore = new ArrayList<String>();
		ItemStack quit = new ItemStack(Material.BARRIER);
		ItemMeta quitm = quit.getItemMeta();
		quitm.setDisplayName(ChatColor.RED+"Quitter");
		quit.setItemMeta(quitm);
		
		lore.clear();
		
		ItemStack diamond = new ItemStack(Material.DIAMOND_BLOCK);
		ItemMeta diamondM = diamond.getItemMeta();
		diamondM.setDisplayName(ChatColor.AQUA+"# 1 #");
		lore.add(ChatColor.DARK_AQUA+"-----");
		diamondM.setLore(lore);
		diamond.setItemMeta(diamondM);
		
		lore.clear();
		
		ItemStack gold = new ItemStack(Material.GOLD_BLOCK);
		ItemMeta goldM = gold.getItemMeta();
		goldM.setDisplayName(ChatColor.YELLOW+"# 2 #");
		lore.add(ChatColor.GOLD+"-----");
		goldM.setLore(lore);
		gold.setItemMeta(goldM);
		
		lore.clear();
		
		ItemStack emerald = new ItemStack(Material.EMERALD_BLOCK);
		ItemMeta emeraldM = emerald.getItemMeta();
		emeraldM.setDisplayName(ChatColor.GREEN+"# 3 #");
		lore.add(ChatColor.DARK_GREEN+"-----");
		emeraldM.setLore(lore);
		emerald.setItemMeta(emeraldM);
		
		lore.clear();
		
		if(HumineKingdom.getPlayerkingdom(player) != null){
			
			ItemStack myGlory = new ItemStack(Material.EYE_OF_ENDER);
			ItemMeta myGloryM = myGlory.getItemMeta();
			myGloryM.setDisplayName(ChatColor.DARK_PURPLE+"# Ma place #");
			lore.add(ChatColor.UNDERLINE+""+ChatColor.GREEN+HumineKingdom.getPlayerkingdom(player).getName());
			lore.add(ChatColor.DARK_GREEN+"----------");
			lore.add(ChatColor.DARK_PURPLE+"King: "+ChatColor.WHITE+ HumineKingdom.getPlayerkingdom(player).getKing().getName());
			lore.add(ChatColor.DARK_PURPLE+"Glory: "+ChatColor.WHITE + HumineKingdom.getPlayerkingdom(player).getGlory());
			lore.add(ChatColor.DARK_GREEN+"----------");
			lore.add("");
			lore.add(ChatColor.DARK_PURPLE+"Ma gloire: "+ChatColor.WHITE+HumineKingdom.getPlayerGlory(player));
			myGloryM.setLore(lore);
			myGlory.setItemMeta(myGloryM);
			
			lore.clear();
			
			menu.addItem(myGlory, 13);
		}else{
			
			ItemStack myGlory = new ItemStack(Material.EYE_OF_ENDER);
			ItemMeta myGloryM = myGlory.getItemMeta();
			myGloryM.setDisplayName(ChatColor.DARK_PURPLE+"# Ma place #");
			lore.add(ChatColor.DARK_PURPLE+"Ma gloire: "+ChatColor.WHITE+HumineKingdom.getPlayerGlory(player));
			myGloryM.setLore(lore);
			myGlory.setItemMeta(myGloryM);
			
			lore.clear();
			
			menu.addItem(myGlory, 13);
			
		}
		
		int slot = 1;
		
		KingdomManager.sort(); //resort if glory has changed since last save
		
		if (KingdomManager.getKingdoms().size() < top) {
			for(Kingdom k : KingdomManager.getKingdoms()){
				String name = k.getKing().getName();
				ItemStack membre = new ItemStack(Material.SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
				SkullMeta membrem = (SkullMeta) membre.getItemMeta();
				if(Bukkit.getPlayer(name) == null){
					membrem.setDisplayName(ChatColor.UNDERLINE+""+ChatColor.GREEN+k.getName());
					membrem.setOwner(Bukkit.getOfflinePlayer(name).getName());
				}else{
					membrem.setDisplayName(ChatColor.UNDERLINE+""+ChatColor.GREEN+ k.getName());
					membrem.setOwner(Bukkit.getPlayer(name).getName());
				}
				if(HumineKingdom.getPlayerGrade(Bukkit.getOfflinePlayer(name)) != null){
					lore.add(ChatColor.DARK_PURPLE+"King: "+ChatColor.WHITE+ name);
					lore.add(ChatColor.DARK_PURPLE+"Glory: "+ChatColor.WHITE + k.getGlory());
				}
				membrem.setLore(lore);
				lore.clear();
				membre.setItemMeta(membrem);
				menu.addItem(membre, slot);
				slot+=3;
				}
			menu.addItem(quit, menu.getSize()-1);
			
			menu.addItem(diamond, 0);
			menu.addItem(diamond, 2);
			
			menu.addItem(gold, 3);
			menu.addItem(gold, 5);
			
			menu.addItem(emerald, 6);
			menu.addItem(emerald, 8);
			
            return menu;
			
		}
		
		for(Kingdom k : KingdomManager.getKingdoms().subList( 0, top)){
			String name = k.getKing().getName();
			ItemStack membre = new ItemStack(Material.SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
			SkullMeta membrem = (SkullMeta) membre.getItemMeta();
			if(Bukkit.getPlayer(name) == null){
				membrem.setDisplayName(ChatColor.UNDERLINE+""+ChatColor.GREEN+k.getName());
				membrem.setOwner(Bukkit.getOfflinePlayer(name).getName());
			}else{
				membrem.setDisplayName(ChatColor.UNDERLINE+""+ChatColor.GREEN+ k.getName());
				membrem.setOwner(Bukkit.getPlayer(name).getName());
			}
			if(HumineKingdom.getPlayerGrade(Bukkit.getOfflinePlayer(name)) != null){
				lore.add(ChatColor.DARK_PURPLE+"King: "+ChatColor.WHITE+ name);
				lore.add(ChatColor.DARK_PURPLE+"Glory: "+ChatColor.WHITE + k.getGlory());
			}
			membrem.setLore(lore);
			lore.clear();
			membre.setItemMeta(membrem);
			menu.addItem(membre, slot);
			slot+=3;
		}
		
		menu.addItem(diamond, 0);
		menu.addItem(diamond, 2);
		
		menu.addItem(gold, 3);
		menu.addItem(gold, 5);
		
		menu.addItem(emerald, 6);
		menu.addItem(emerald, 8);
		
		menu.addItem(quit, menu.getSize()-1);
		
		return menu;
	}

}

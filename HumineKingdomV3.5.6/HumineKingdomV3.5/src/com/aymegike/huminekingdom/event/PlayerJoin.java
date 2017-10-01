package com.aymegike.huminekingdom.event;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.manager.GradeManager;
import com.aymegike.huminekingdom.utils.objets.Kingdom;

import net.md_5.bungee.api.ChatColor;

public class PlayerJoin implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		GetName.getNameOfKingdom.remove(p);
		GetName.getNameOfGrade.remove(p);
		GetName.getNameOfPlayer.remove(p);
		try(FileInputStream fis = new FileInputStream(HumineKingdom.getDataKingdom())){
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(fis);
			Boolean exist = false;
			while(sc.hasNext()){
				String[] args = sc.nextLine().split(" = ");
				if(!args[0].split(" ")[0].equalsIgnoreCase("*")){
					if(args[0].equalsIgnoreCase(p.getName())){ //if player already exist
						exist = true;
						if (!args[1].equalsIgnoreCase("Aucun")) { //if he has a kingdom
							HumineKingdom.getKingdom(args[1]).addMember(p, false, false);
							HumineKingdom.getGrade(args[2], HumineKingdom.getKingdom(args[1])).addNewPlayer(Bukkit.getOfflinePlayer(p.getName()), false);										
						}
					}
				}
			}
			if (exist == false) {
				PrintWriter print = new PrintWriter(new FileOutputStream(HumineKingdom.getDataKingdom(), true));
				print.println(p.getName() + " = aucun = aucun = 0");
				print.close();
			}
			
		}catch(IOException ex){
			ex.printStackTrace();
		}	
		GradeManager.addPlayer(p);
			

		/********************************************
		 *      Gain/Perte de gloire                *
		 ********************************************/
		Kingdom k = HumineKingdom.getPlayerkingdom(p);
		if(k != null){
			if(HumineKingdom.getPlayerGrade(p).getName().equalsIgnoreCase("King") && k.haveDragonEgg()){
				if(HumineKingdom.isNewDay()){
					p.sendMessage(ChatColor.DARK_PURPLE+"Ho grand empereur tout puissant, allez caressez votre oeuf pour montrer a tous votre puissance !");
				}
			}
		}
		
		
	}

}

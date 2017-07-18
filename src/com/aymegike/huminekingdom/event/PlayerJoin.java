package com.aymegike.huminekingdom.event;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.GradeManager;

public class PlayerJoin implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		try(FileInputStream fis = new FileInputStream(HumineKingdom.getDataKingdom())){
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(fis);
			while(sc.hasNext()){
				String[] args = sc.nextLine().split(" = ");
				if(args[0].equalsIgnoreCase(p.getName())){
					HumineKingdom.getKingdom(args[1]).addMember(p, false, false);
					HumineKingdom.getGrade(args[2], HumineKingdom.getKingdom(args[1])).addNewPlayer(Bukkit.getOfflinePlayer(p.getName()), false);										
				}
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
		GradeManager.addPlayer(p);
		
		
		
		
	}

}

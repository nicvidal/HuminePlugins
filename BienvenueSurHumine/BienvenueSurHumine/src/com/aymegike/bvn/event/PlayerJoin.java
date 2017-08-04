package com.aymegike.bvn.event;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.aymegike.bvn.Main;
import com.aymegike.bvn.utils.PlayerJoinManager;

public class PlayerJoin implements Listener{
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent e){
		boolean firstjoin = true;
		Player p = e.getPlayer();
		e.setJoinMessage(null);
		try(FileInputStream fis = new FileInputStream(Main.getList())){
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(fis);
			while(sc.hasNext()){
				if(sc.nextLine().equalsIgnoreCase(p.getUniqueId().toString())){
					firstjoin = false;
				}
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
		if(firstjoin == true){
//			Title.sendTitle(p, ChatColor.DARK_PURPLE+"Humine"+ChatColor.WHITE+"Craft", ChatColor.DARK_PURPLE+"Bienvenue "+p.getName());
//			Bukkit.broadcastMessage(ChatColor.DARK_PURPLE+"Bienvenue "+ChatColor.WHITE+p.getName()+ChatColor.DARK_PURPLE+" sur "+ChatColor.WHITE+"HumineCraft "+ChatColor.DARK_PURPLE+"!");
//			p.getInventory().addItem(Main.getBook());
//			try(PrintWriter print = new PrintWriter(new FileOutputStream(Main.getList(), true))){
//				print.println(p.getUniqueId().toString());
//			}catch(IOException ex){
//				ex.printStackTrace();
//			}
			
		PlayerJoinManager.newPlayer(p);
		
		}else{

		}
	}

}

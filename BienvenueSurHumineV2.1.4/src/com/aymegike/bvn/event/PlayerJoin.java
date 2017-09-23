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
		try(FileInputStream fis = new FileInputStream(Main.getList())){
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(fis);
			while(sc.hasNext()){
				String[] args = sc.nextLine().split(" ");
				if(args[0].equalsIgnoreCase(p.getUniqueId().toString())){
					firstjoin = false;
				}
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
		if(firstjoin == true){		
			e.setJoinMessage(null);
			PlayerJoinManager.newPlayer(p);
		}else{
			
		}
	}

}

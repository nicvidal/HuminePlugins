package com.aymegike.bvn.event;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.aymegike.bvn.Main;

public class playerLeft implements Listener{
	
	@EventHandler
	public void onPlayerLeft(PlayerQuitEvent e){
		boolean firstjoin = true;
		OfflinePlayer p = e.getPlayer();
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
			e.setQuitMessage(null);
		}else{
			
		}
	}

}

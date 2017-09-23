package com.aymegike.bvn.event;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.aymegike.bvn.Main;

public class Respawn implements Listener{
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e){
		Player player = e.getPlayer();
		if(player.getBedSpawnLocation() == null){
			System.out.println("Le lit de "+player.getName()+" est introuvable. teleportation a sont lieu de crash.");
			Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("WelcomeOnHumineCraft"), new Runnable(){
	            @Override
				public void run(){
					player.teleport(getPlayerSpawn(player));
	            }
	        }, 1);
		}
	}
	
	@EventHandler
	public void passPortal(PlayerPortalEvent e){
		if(e.getFrom().getWorld() == Bukkit.getWorlds().get(2))
			if(e.getPlayer().getBedSpawnLocation() == null)
				e.setTo(getPlayerSpawn(e.getPlayer()));
		
	}

	public static Location getPlayerSpawn(Player player) {
		
		Location loc = null;
		String uuid = player.getUniqueId().toString();
		
		try(FileInputStream fis = new FileInputStream(Main.getList())){
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(fis);
			while(scan.hasNext()){
				String[] args = scan.nextLine().split(" ");
				
				if(args[0].equalsIgnoreCase(uuid)){
					loc = new Location(Bukkit.getServer().getWorlds().get(0), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));				
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return loc;
	}

}

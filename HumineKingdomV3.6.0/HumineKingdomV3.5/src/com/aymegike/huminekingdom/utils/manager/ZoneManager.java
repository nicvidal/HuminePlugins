package com.aymegike.huminekingdom.utils.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.MenuList;
import com.aymegike.huminekingdom.utils.objets.Kingdom;
import com.aymegike.huminekingdom.utils.objets.Zone;

import net.md_5.bungee.api.ChatColor;

public class ZoneManager {
	
	private static ArrayList<Zone> zones = new ArrayList<Zone>();
	
	public static void loadZone(){
		
		try(FileInputStream fis = new FileInputStream(HumineKingdom.getDataZone())){
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(fis);
			while(sc.hasNext()){
				String[] args = sc.nextLine().split(" = ");
				Location loc = new Location(Bukkit.getWorlds().get(0), Integer.parseInt(args[1]), 0, Integer.parseInt(args[2]));
				Zone zone = new Zone(HumineKingdom.getKingdom(args[0]), loc, false);
				zones.add(zone);
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
	}
	
	public static ArrayList<Zone> getAllZones(){
		return zones;
	}
	
	public static void addZone(Zone z){
		zones.add(z);
		for(OfflinePlayer op : z.getKingdom().getAllMember()){
			if(op.isOnline()){
				if(op.getPlayer().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA+"Liste des Balises")){
					op.getPlayer().closeInventory();
					MenuList.BeaconList(HumineKingdom.getPlayerkingdom(op)).openForPlayer(op.getPlayer());
				}
				if(op.getPlayer().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GRAY+"Gestion "+HumineKingdom.getPlayerkingdom(op).getName())){
					op.getPlayer().closeInventory();
					MenuList.gestionMenu(HumineKingdom.getPlayerkingdom(op), op.getPlayer()).openForPlayer(op.getPlayer());
				}
			}
		}
	    refresh();
	}
	
	public static void removeZone(Zone z){
		if(zones.contains(z))
			zones.remove(z);
			z.remove();
			for(OfflinePlayer op : z.getKingdom().getAllMember()){
				if(op.isOnline()){
					if(op.getPlayer().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.AQUA+"Liste des Balises")){
						op.getPlayer().closeInventory();
						MenuList.BeaconList(HumineKingdom.getPlayerkingdom(op)).openForPlayer(op.getPlayer());
					}
					if(op.getPlayer().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.GRAY+"Gestion "+HumineKingdom.getPlayerkingdom(op).getName())){
						op.getPlayer().closeInventory();
						MenuList.gestionMenu(HumineKingdom.getPlayerkingdom(op), op.getPlayer()).openForPlayer(op.getPlayer());
					}
				}
			}
		    refresh();
	}
	
	
	public static int getBeaconsOfKingdom(Kingdom k){
		int count = 0;
		for(Zone zone : zones){
			if(zone.getKingdom() == k){
				count++;
			}
			
		}
		
		return count;
		
	}
	
	public static void refresh(){
		zones.clear();
		loadZone();
	}
}

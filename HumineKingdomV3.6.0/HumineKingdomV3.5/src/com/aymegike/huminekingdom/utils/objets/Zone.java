package com.aymegike.huminekingdom.utils.objets;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import com.aymegike.huminekingdom.HumineKingdom;

public class Zone {

	private Kingdom kingdom;
	private Location location;
	
	public Zone(Kingdom kingdom, Location location, boolean isNewZone){
		
		this.kingdom = kingdom;
		this.location = location;
		
		if(isNewZone == true){
			
			try(PrintWriter print = new PrintWriter(new FileOutputStream(HumineKingdom.getDataZone(), true))){
				print.println(kingdom.getName()+" = "+location.getBlockX()+" = "+location.getBlockZ());
			}catch(IOException e){
				e.printStackTrace();
			}
		
		}
		
	}
	
	public Kingdom getKingdom(){
		return kingdom;
	}
	
	public Location getLocation(){
		return location;
	}
	
	public boolean playerIsInZone(Player p){
		if(p.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)){
			for(int x = location.getBlockX()-50 ; x<location.getBlockX()+50 ; x++){
				for(int z = location.getBlockZ()-50 ; z<location.getBlockZ()+50 ; z++){
					
					if(p.getLocation().getBlockX() == x && p.getLocation().getBlockZ() == z){
						return true;
					}
					
				}
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public boolean playerCanBuild(Player p){
		
		if(kingdom.containPlayer(Bukkit.getOfflinePlayer(p.getName()))){
			if(p.hasPermission("kingdom.build")){
				return true;
			}
		}
		
		
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public boolean playerCanBreak(Player p){
		
		if(kingdom.containPlayer(Bukkit.getOfflinePlayer(p.getName()))){
			if(p.hasPermission("kingdom.break")){
				return true;
			}
		}
		
		
		return false;
	}
	
	public void remove(){
		ArrayList<String> tempo = new ArrayList<String>();
		try(FileInputStream fis = new FileInputStream(HumineKingdom.getDataZone())){
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(fis);
			while(sc.hasNext()){
				String line = sc.nextLine();
				String[] args = line.split(" = ");
				if(Integer.parseInt(args[1]) == location.getBlockX() && Integer.parseInt(args[2]) == location.getBlockZ()){
					
				}else{
					tempo.add(line);
				}
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
		HumineKingdom.reloadZoneFile();
		try(PrintWriter print = new PrintWriter(new FileOutputStream(HumineKingdom.getDataZone(), true))){
			for(String str : tempo){
				print.println(str);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}

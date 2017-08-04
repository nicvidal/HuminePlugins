package com.aymegike.huminekingdom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.aymegike.huminekingdom.event.EventManager;
import com.aymegike.huminekingdom.utils.Clock;
import com.aymegike.huminekingdom.utils.GradeManager;
import com.aymegike.huminekingdom.utils.KingdomManager;
import com.aymegike.huminekingdom.utils.ParticleManager;
import com.aymegike.huminekingdom.utils.TeamManager;
import com.aymegike.huminekingdom.utils.ZoneManager;
import com.aymegike.huminekingdom.utils.command.CommandManager;
import com.aymegike.huminekingdom.utils.objets.Grade;
import com.aymegike.huminekingdom.utils.objets.Kingdom;


public class HumineKingdom extends JavaPlugin{
	
	private static HumineKingdom hk;
	
	static File grade;
	static File kingdom; 
	static File zone;
	
	public static int DAY;

	static FileConfiguration config;
	private static Location loceegs;
	
	public static boolean GIVED = false;
	
	public static  HumineKingdom getInstance(){
		return hk;
	}
	
	public void onEnable(){
		System.out.println("Useless Modif");
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println("[HumineKingdom] Aymegike production !");
		System.out.println("---------------------------------------------------------------------------------");
		EventManager.registerEvents(this);
		CommandManager.registerCommand(this);
		loadAllFiles();//create data file if !exit
		KingdomManager.loadKingdom();//register kingdom in variable
		GradeManager.loadGrades();//register grade in variable
		ZoneManager.loadZone();//register zones in variable
		TeamManager.createTeams();
		Clock.clock();
		
	}
	
	public void onDisable(){
		System.out.println("useless modif");

		//register location for dragon eegs
		File file = new File("./plugins/HumineKingdom/config.yml");
		file.delete();
		if(!file.exists()){
			try{
				file.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}
			try(PrintWriter print = new PrintWriter(new FileOutputStream(file, true))){
				if(loceegs != null){
					print.println("location:");
					print.println("   dragon:");
					print.println("     x: "+loceegs.getBlockX());
					print.println("     y: "+loceegs.getBlockY());
					print.println("     z: "+loceegs.getBlockZ());
				}else{
					print.println("location:");
					print.println("   dragon:");
					print.println("     x: 0");
					print.println("     y: 0");
					print.println("     z: 0");
					
				}
				print.println();
				print.println("day: "+DAY);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	public void loadAllFiles(){
		
		new File("./plugins/HumineKingdom/DATA/").mkdirs();//generate Folder
		new File("./plugins/HumineKingdom/Kingdom/").mkdirs();//generate folder
		
		grade = new File("./plugins/HumineKingdom/DATA/grade.yml");//generate listGrade File
		kingdom = new File("./plugins/HumineKingdom/DATA/kingdom.yml");//generate listKingdom File
		zone = new File("./plugins/HumineKingdom/DATA/zone.yml");//generate listZoneFile
		
		if(!grade.exists()){
			try{
				grade.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		if(!kingdom.exists()){
			try{
				kingdom.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		if(!zone.exists()){
			try{
				zone.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		//generate and set config for find loc of dragon eegs
		
		File file = new File("./plugins/HumineKingdom/config.yml");
		new File("./plugins/HumineKingdom").mkdirs();
		@SuppressWarnings("deprecation")
		int day = Calendar.getInstance().getTime().getDay();
		if(!file.exists()){
			System.out.println("generate configFile !");
			try{
				file.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}
			try(PrintWriter print = new PrintWriter(new FileOutputStream(file, true))){
				print.println("location:");
				print.println("   dragon:");
				print.println("     x: 0");
				print.println("     y: 0");
				print.println("     z: 0");
				print.println();
				print.println("day: "+day);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		config = YamlConfiguration.loadConfiguration(file);
		System.out.println("Location de l'oeuf: "+config.getInt("location.dragon.x")+" "+config.getInt("location.dragon.y")+" "+config.getInt("location.dragon.z"));
		if(!(config.getInt("location.dragon.x") == 0 && config.getInt("location.dragon.y") == 0 && config.getInt("location.dragon.z") == 0)){
			setLocation(new Location(Bukkit.getWorlds().get(0), config.getInt("location.dragon.x"), config.getInt("location.dragon.y"), config.getInt("location.dragon.z")));
			ParticleManager.loadParticle(new Location(Bukkit.getWorlds().get(0), config.getInt("location.dragon.x"), config.getInt("location.dragon.y"), config.getInt("location.dragon.z")));
			
		}
		DAY = config.getInt("day");
	}
	
	public static void isTime(){
		@SuppressWarnings("deprecation")
		int test = Calendar.getInstance().getTime().getDay();
		if(test != DAY && loceegs.getBlockX() != 0 && loceegs.getBlockY() != 0 && loceegs.getBlockZ() != 0){
			ParticleManager.presentParticle(loceegs);
			DAY = test;
		}
	}
	
	public static File getDataGrade(){
		return grade;
	}
	
	public static File getDataKingdom(){
		return kingdom;
	}
	
	public static File getDataZone(){
		return zone;
	}
	
	public static void setLocation(Location loc){
		System.out.println("Nouvelle coordoné pour l'oeuf !");
		loceegs = loc;
		
	}
	
	public static Kingdom getEggKingdom(){
		for(Kingdom k : KingdomManager.getKingdoms()){
			if(k.haveDragonEgg()){
				return k;
			}
		}
		
		return null;
	}
	
	public static Location getEegsLog(){
		return loceegs;
	}
	
	public static Grade getGrade(String grade, Kingdom kingdom){
		
		for(Grade g : GradeManager.getGrades()){
			if(g.getKingdom() == kingdom){
				if(g.getName().equalsIgnoreCase(grade)){
					return g;
				}
			}
		}
		
		return null;
	}
	public static Grade getPlayerGrade(OfflinePlayer offlinePlayer){
		for(Grade g : GradeManager.getGrades()){
			if(g.containPlayer(offlinePlayer)){
				return g;
			}
		}
		
		return null;
	}

	public static Kingdom getPlayerkingdom(OfflinePlayer p){
		for(Kingdom k : KingdomManager.getKingdoms()){
			if(k.containPlayer(p)){
				return k;
			}
		}
		return null;
	}
	
	public static Kingdom getKingdom(String name){
		for(Kingdom k : KingdomManager.getKingdoms()){
			if(k.getName().equalsIgnoreCase(name)){
				return k;
			}
		}
		return null;
	}

	public static void reloadKingdomFile(){
		kingdom.delete();
		try{
			kingdom.createNewFile();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public static void reloadGradeFile(){
		grade.delete();
		try{
			grade.createNewFile();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public static void reloadZoneFile(){
		zone.delete();
		try{
			zone.createNewFile();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	

}

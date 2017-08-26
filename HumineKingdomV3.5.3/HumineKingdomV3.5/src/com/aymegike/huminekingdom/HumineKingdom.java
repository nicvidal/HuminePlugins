package com.aymegike.huminekingdom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.aymegike.huminekingdom.event.EventManager;
import com.aymegike.huminekingdom.utils.GradeManager;
import com.aymegike.huminekingdom.utils.KingdomManager;
import com.aymegike.huminekingdom.utils.MenuList;
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
	
	static FileConfiguration config;
	private static Location loceegs;
	
	private static int date;
	
	public static  HumineKingdom getInstance(){
		return hk;
	}
	
	public void onEnable(){
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println("[HumineKingdom] Aymegike & coo production !");
		System.out.println("---------------------------------------------------------------------------------");
		EventManager.registerEvents(this);
		CommandManager.registerCommand(this);
		loadAllFiles();//create data file if !exit
		KingdomManager.loadKingdom();//register kingdom in variable
		GradeManager.loadGrades(true);//register grade in variable
		ZoneManager.loadZone();//register zones in variable
		TeamManager.createTeams();
		date = config.getInt("date");
	}
	public void onDisable(){
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
					print.println("#DON'T TOUCH#");
					print.println("date: "+date);
				}else{
					print.println("location:");
					print.println("   dragon:");
					print.println("     x: 0");
					print.println("     y: 0");
					print.println("     z: 0");
					print.println("#DON'T TOUCH#");
					print.println("date: "+date);
					
				}
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
		if(!file.exists()){
			System.out.println("generate configFile !");
			updateDate();
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
				print.println("#DON'T TOUCH#");
				print.println("date: "+date);
				
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
		System.out.println("Nouvelle coordoné pour l'oeuf: x: "+ loc.getBlockX()+" y: "+loc.getBlockY()+" z: "+loc.getBlockZ());
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
	
	public static Location getEegsLoc(){
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
	
	public static int getPlayerGlory(OfflinePlayer p){
		try(FileInputStream fis = new FileInputStream(HumineKingdom.getDataKingdom())){
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(fis);
			while(scan.hasNext()){
				String line = scan.nextLine();
				String args[] = line.split(" = ");
				if(p.getName().equalsIgnoreCase(args[0])){
					return Integer.parseInt(args[3]);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public static void setPlayerGlory(OfflinePlayer p, int glory){
		if(glory < 0)
			glory = 0;
		ArrayList<String> keep = new ArrayList<>();
		
		try(FileInputStream fis = new FileInputStream(HumineKingdom.getDataKingdom())){
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(fis);
			while(scan.hasNext()){
				String line = scan.nextLine();
				String args[] = line.split(" = ");
				if(!args[0].equalsIgnoreCase(p.getName())){
					keep.add(line);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		
		for(Player pls : Bukkit.getOnlinePlayers())
			if(pls.getOpenInventory().getTitle().contains("Classement"))
				MenuList.rankingMenu(pls).openForPlayer(pls);
		
		reloadKingdomFile();
		
		try(PrintWriter print = new PrintWriter(new FileOutputStream(HumineKingdom.getDataKingdom(), true))){
			for(String str : keep){
				print.println(str);
			}
			if(HumineKingdom.getPlayerkingdom(p) != null && HumineKingdom.getPlayerGrade(p) != null){
				print.println(p.getName()+" = "+HumineKingdom.getPlayerkingdom(p).getName()+" = "+HumineKingdom.getPlayerGrade(p).getName()+" = "+glory);
			}else if(HumineKingdom.getPlayerkingdom(p) == null && HumineKingdom.getPlayerGrade(p) == null){
				print.println(p.getName()+" = Aucun = Aucun = "+glory);
			}else if(HumineKingdom.getPlayerkingdom(p) != null && HumineKingdom.getPlayerGrade(p) == null){
				print.println(p.getName()+" = "+HumineKingdom.getPlayerkingdom(p).getName()+" = Aucun = "+glory);
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
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
	
	@SuppressWarnings("deprecation")
	public static void updateDate(){
		Calendar c = Calendar.getInstance();
		Date d = c.getTime();
		date = d.getDay();
	}
	
	@SuppressWarnings("deprecation")
	public static boolean isNewDay(){
		Calendar c = Calendar.getInstance();
		Date d = c.getTime();
		if(date == d.getDay()){
			return false;
		}
		return true;
	}	

	

}

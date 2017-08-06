package com.aymegike.huminekingdom.utils.objets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.GradeManager;
import com.aymegike.huminekingdom.utils.KingdomManager;
import com.aymegike.huminekingdom.utils.MenuList;
import com.aymegike.huminekingdom.utils.ZoneManager;

import net.md_5.bungee.api.ChatColor;

public class Kingdom {
	
	ArrayList<OfflinePlayer> pls = new ArrayList<OfflinePlayer>();
	String name;
	File member; 
	File kingdom;
	int glory;
	
	@SuppressWarnings("deprecation")
	public Kingdom(String name){
		this.name = name;
		this.glory = 50;
		kingdom = new File("./plugins/HumineKingdom/Kingdom/"+name);
		kingdom.mkdirs();
		member = new File("./plugins/HumineKingdom/Kingdom/"+name+"/member.yml");
		if(!member.exists()){
			try{
				member.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		try(FileInputStream fis = new FileInputStream(member)){
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(fis);
			while(sc.hasNext()){
				pls.add(Bukkit.getOfflinePlayer(sc.nextLine()));
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}		
	}
		
	//geters
	public ArrayList<OfflinePlayer> getAllMember(){
		return pls;
	}
	
	public boolean haveDragonEgg(){
		File dragon = new File("./plugins/HumineKingdom/Kingdom/"+name+"/megakingdom.check");
		if(dragon.exists())
			return true;
		
		return false;
	}
	
	public void setDragonEgg(boolean dragonEgg){
		File dragon = new File("./plugins/HumineKingdom/Kingdom/"+name+"/megakingdom.check");
		if(dragonEgg == true){
			try{
				dragon.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}
		}else{
			dragon.delete();
		}
	}
	
	public boolean containPlayer(OfflinePlayer p){
		for(OfflinePlayer pls : pls){
			if(pls == p){
				return true;
			}
		}
		return false;
	}
	
	public String getName(){
		return name;
	}
	
	public int getGlory() {
		return glory;
	}
	
	public void increaseGlory(int points) {
		glory += points;
	}
	
	public void decreaseGlory(int points) {
		glory -= points; //Allow negative glory ?
	}
	
	public String getKingdomFile(){
		return kingdom.getAbsolutePath();
	}
	
	//add
	@SuppressWarnings("deprecation")
	public void addMember(Player p, boolean registerInData, boolean isKing){
		pls.add(p);
		p.closeInventory();
		if(registerInData == true){
			if(isKing == false){
				if(HumineKingdom.getPlayerGrade(p) != null){ 
					HumineKingdom.getPlayerGrade(p).removePlayer(p);
				}
			}
			ArrayList<String> tempo = new ArrayList<String>();
			try(PrintWriter print = new PrintWriter(new FileOutputStream(member, true))){
				print.println(p.getName());
			}catch(IOException e){
				e.printStackTrace();
			}
			try(FileInputStream fis = new FileInputStream(HumineKingdom.getDataKingdom())){
				@SuppressWarnings("resource")
				Scanner scan = new Scanner(fis);
				while(scan.hasNext()){
					String line = scan.nextLine();
					String args[] = line.split(" = ");
					if(!args[0].equalsIgnoreCase(p.getName())){
						tempo.add(line);
					}
				}
			}catch(IOException e){
				e.printStackTrace();
			}
			HumineKingdom.reloadKingdomFile();
			try(PrintWriter print = new PrintWriter(new FileOutputStream(HumineKingdom.getDataKingdom(), true))){
				for(String str : tempo){
					print.println(str);
				}
				if(HumineKingdom.getPlayerGrade(Bukkit.getOfflinePlayer(p.getName())) != null){
					print.println(p.getName()+" = "+name+" = "+HumineKingdom.getPlayerGrade(p).getName());
				}else{
					print.println(p.getName()+" = "+name);
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		update();
	}
	
	public void removeMember(OfflinePlayer offlinePlayer){
		System.out.println("remove "+offlinePlayer.getName()+" to "+name);
		pls.remove(offlinePlayer);
		ArrayList<String> tempo = new ArrayList<String>();
		//remove player from kingdom file
		try(FileInputStream fis = new FileInputStream(member)){
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(fis);
			while(scan.hasNext()){
				tempo.add(scan.nextLine());
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		tempo.remove(offlinePlayer.getName());
		member.delete();
		try{
			member.createNewFile();
		}catch(IOException e){
			e.printStackTrace();
		}
		try(PrintWriter print = new PrintWriter(new FileOutputStream(member, true))){
			for(String str : tempo){
				print.println(str);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		tempo.clear();
		//remove player from kingdom data file
		try(FileInputStream fis = new FileInputStream(HumineKingdom.getDataKingdom())){
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(fis);
			while(scan.hasNext()){
				String line = scan.nextLine();
				String args[] = line.split(" = ");
				if(!args[0].equalsIgnoreCase(offlinePlayer.getName())){
					tempo.add(line);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		HumineKingdom.reloadKingdomFile();
		try(PrintWriter print = new PrintWriter(new FileOutputStream(HumineKingdom.getDataKingdom(), true))){
			for(String str : tempo){
				print.println(str);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		update();
	}
	
	public void delete(){
		System.out.println(pls.size());
		ArrayList<OfflinePlayer> tempo = new ArrayList<OfflinePlayer>();
		
		for(OfflinePlayer op : pls){
			tempo.add(op);			
		}
		
		for(OfflinePlayer op : tempo){
			removeMember(op);
			HumineKingdom.getPlayerGrade(op).removePlayer(op);
		}
		
		tempo.clear();
		
		for(Grade grades : GradeManager.getGrades()){
			if(grades.getKingdom().getName().equalsIgnoreCase(name)){
				System.out.println("delet: "+grades.getName()+" of "+kingdom.getName());
				grades.delete();
			}
		}	
		
		for(Zone zones : ZoneManager.getAllZones())
			if(zones.getKingdom().getName().equalsIgnoreCase(name)){
				zones.remove();
			}
		
		deleteDir(kingdom);
		KingdomManager.loadKingdom();
		GradeManager.refresh();
		ZoneManager.refresh();
	}
	
	private void update(){
		for(OfflinePlayer op : pls){
			if(op.isOnline()){
				if(op.getPlayer().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_PURPLE+"Membres "+ChatColor.WHITE+name)
				|| op.getPlayer().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_PURPLE+"Joueur")
				|| op.getPlayer().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_PURPLE+"Choisire un grade")){
					op.getPlayer().closeInventory();
					MenuList.MemberMenu(HumineKingdom.getPlayerkingdom(op)).openForPlayer(op.getPlayer());
				}
			}
		}
	}	
	
	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++){
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) 
					return false;	
			}
		}
		// The directory is now empty so delete it
		return dir.delete();
	}
}

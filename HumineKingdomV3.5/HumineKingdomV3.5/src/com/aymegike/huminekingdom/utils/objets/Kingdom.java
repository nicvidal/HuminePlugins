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
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.event.GetName;
import com.aymegike.huminekingdom.utils.GradeManager;
import com.aymegike.huminekingdom.utils.KingdomManager;
import com.aymegike.huminekingdom.utils.MenuList;
import com.aymegike.huminekingdom.utils.ZoneManager;

import net.md_5.bungee.api.ChatColor;
import java.lang.Comparable;

public class Kingdom implements Comparable<Object> {
	
	ArrayList<OfflinePlayer> pls = new ArrayList<OfflinePlayer>();
	String name;
	File member; 
	File kingdom;
	int glory;
	
	@SuppressWarnings("deprecation")
	public Kingdom(String name, int glory){
		this.name = name;
		this.glory = glory;
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
		}
		catch(IOException ex){
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
		if(dragonEgg == true && !dragon.exists()){
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
	
	public OfflinePlayer getKing() {
		for(OfflinePlayer player : pls){
            if(HumineKingdom.getPlayerGrade(player).getName().equalsIgnoreCase("King")){
                return player;
            }                
        }
		return null;
	}
	
	public String getName(){
		return name;
	}
	
	public void initFileGlory() {		
		try(PrintWriter print = new PrintWriter(new FileOutputStream(HumineKingdom.getDataKingdom(), true))){
			print.println("* "+name + " = glory = " + glory);
		}catch(IOException e){
			e.printStackTrace();
		}		
	}
	
	public int getGlory() {
		return glory;
	}
	
	private void updateGlory(int points) { //note necessary at the right place	
		setGlory(glory+points);
		ArrayList<String> keep = new ArrayList<>();
		
		try(FileInputStream fis = new FileInputStream(HumineKingdom.getDataKingdom())){
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(fis);
			while(scan.hasNext()){
				String line = scan.nextLine();
				String args[] = line.split(" = ");
				if(!args[0].equalsIgnoreCase("* "+name)){
					keep.add(line);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		
		HumineKingdom.reloadKingdomFile();
		
		try(PrintWriter print = new PrintWriter(new FileOutputStream(HumineKingdom.getDataKingdom(), true))){
			for(String str : keep){
				print.println(str);
			}
			print.println("* "+name+" = glory = "+this.glory);
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private void setGlory(int glory) {
		this.glory = glory;
		if(glory < 0){
			this.glory = 0;
			System.out.println(glory);
		}
	}
	
	public void increaseGlory(Player p, int points, boolean addForPlayer) {
		updateGlory(points);
		for(OfflinePlayer op : pls){
			if(op.isOnline()){
				if(points == 1)
					op.getPlayer().sendMessage(ChatColor.DARK_PURPLE+"Votre royaume gagne "+ChatColor.WHITE+points+ChatColor.DARK_PURPLE+" point.");
				else
					op.getPlayer().sendMessage(ChatColor.DARK_PURPLE+"Votre royaume gagne "+ChatColor.WHITE+points+ChatColor.DARK_PURPLE+" points.");
				op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_YES, 5, 1);
			}
		}
		if(addForPlayer)
			HumineKingdom.setPlayerGlory(p, HumineKingdom.getPlayerGlory(p)+points);
		else
			for(Player pls : Bukkit.getOnlinePlayers())
				if(pls.getOpenInventory().getTitle().contains("Classement"))
					MenuList.rankingMenu(pls).openForPlayer(pls);
	}
	
	public void decreaseGlory(Player p, int points, boolean addForPlayer) {
		updateGlory((points*-1));
		for(OfflinePlayer op : pls){
			if(op.isOnline()){
				if(points == 1)
					op.getPlayer().sendMessage(ChatColor.DARK_PURPLE+"Votre royaume perd "+ChatColor.WHITE+points+ChatColor.DARK_PURPLE+" point.");
				else
					op.getPlayer().sendMessage(ChatColor.DARK_PURPLE+"Votre royaume perd "+ChatColor.WHITE+points+ChatColor.DARK_PURPLE+" points.");
				op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 5, 1);
			}
		}
		if(addForPlayer)
			HumineKingdom.setPlayerGlory(p, HumineKingdom.getPlayerGlory(p)-points);
		else
			for(Player pls : Bukkit.getOnlinePlayers())
				if(pls.getOpenInventory().getTitle().contains("Classement"))
					MenuList.rankingMenu(pls).openForPlayer(pls);
	}
	
	public String getKingdomFile(){
		return kingdom.getAbsolutePath();
	}
	
	//add
	//@SuppressWarnings("deprecation")
	@SuppressWarnings("deprecation")
	public void addMember(Player p, boolean registerInData, boolean isKing){
		boolean isAdd = false;
		OfflinePlayer vire = null;
		
		int point = HumineKingdom.getPlayerGlory(p);
		
		for(OfflinePlayer op : pls){
			if(op.getName().equalsIgnoreCase(p.getName())){
				isAdd = true;
				vire = op;
			}
		}
		if(isAdd){
			pls.remove(vire);
		}
		pls.add(p);
		p.closeInventory();
		File Player7 = new File("./plugins/HumineKingdom/Kingdom/"+name+"/PlayerSeven.check");
		if(pls.size() >= 7 && !Player7.exists()){
			increaseGlory(null, 70, false);
			try{
				Player7.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
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
					System.out.println(str);
				}
				if(HumineKingdom.getPlayerGrade(Bukkit.getOfflinePlayer(p.getName())) != null){
					print.println(p.getName()+" = "+name+" = "+HumineKingdom.getPlayerGrade(p).getName()+" = "+point);
					System.out.println(p.getName()+" = "+name+" = "+HumineKingdom.getPlayerGrade(p).getName()+" = "+point);
				}else{
					print.println(p.getName()+" = "+name+" = aucun = "+point);
					System.out.println(p.getName()+" = "+name+" = aucun = "+point);
				}
			}catch(IOException e){
				e.printStackTrace();
			}
			
			update();
		}
	}
	
	public void removeMember(OfflinePlayer offlinePlayer){
		System.out.println("remove "+offlinePlayer.getName()+" to "+name);
		pls.remove(offlinePlayer);
		if(offlinePlayer.isOnline()){		
			GetName.getNameOfKingdom.remove(Bukkit.getPlayer(offlinePlayer.getName()));		
			GetName.getNameOfGrade.remove(Bukkit.getPlayer(offlinePlayer.getName()));		
			GetName.getNameOfPlayer.remove(Bukkit.getPlayer(offlinePlayer.getName()));		
		}
		ArrayList<String> tempo = new ArrayList<String>();
		//remove player from kingdom member file
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
				tempo.add(line);				
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		HumineKingdom.reloadKingdomFile();
		try(PrintWriter print = new PrintWriter(new FileOutputStream(HumineKingdom.getDataKingdom(), true))){
			for(String str : tempo){
				String[] args = str.split(" = ");
				if(args[0].equalsIgnoreCase(offlinePlayer.getName())){
					print.println(offlinePlayer.getName() + " = aucun = aucun = " + args[3]);
				}else
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
		
		for(OfflinePlayer op : tempo) {
			removeMember(op);
			if(HumineKingdom.getPlayerGrade(op) != null) {
				HumineKingdom.getPlayerGrade(op).removePlayer(op);
			}
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
		
		ArrayList<String> keep = new ArrayList<String>();
		
		try(FileInputStream fis = new FileInputStream(HumineKingdom.getDataKingdom())){
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(fis);
			while(scan.hasNext()){
				String line = scan.nextLine();
				String args[] = line.split(" = ");
				if(!args[0].equalsIgnoreCase("* "+name)){
					keep.add(line);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		HumineKingdom.reloadKingdomFile();
		
		try(PrintWriter print = new PrintWriter(new FileOutputStream(HumineKingdom.getDataKingdom(), true))){
			for(String str : keep){
				print.println(str);
			}
		}catch(IOException e){
			e.printStackTrace();
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
				|| op.getPlayer().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_PURPLE+"Choisir un grade")){
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
	
	 public int compareTo(Object o) {
		 Kingdom k = (Kingdom)o;
	        if (k.getGlory() == this.getGlory())
	            return 0;
	        else if (k.getGlory() > this.getGlory())
	            return 1;
	        else
	            return -1;
	    }
}

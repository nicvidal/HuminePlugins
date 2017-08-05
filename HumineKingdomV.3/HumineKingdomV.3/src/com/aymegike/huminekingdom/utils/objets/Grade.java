package com.aymegike.huminekingdom.utils.objets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.GradeManager;
import com.aymegike.huminekingdom.utils.MenuList;

public class Grade {
	
	private ArrayList<String> perms = new ArrayList<String>();
	private ArrayList<OfflinePlayer> pls = new ArrayList<OfflinePlayer>();
	
	private String name;
	private Kingdom kingdom;
	private File permFile;
	private File memberFile;
	
	@SuppressWarnings("deprecation")
	public Grade(String name, Kingdom kingdom, boolean registerindata){
		
		this.name = name;
		this.kingdom = kingdom;
		new File("./plugins/HumineKingdom/Kingdom/"+kingdom.getName()+"/grade/"+name+"/").mkdirs();
		permFile = new File("./plugins/HumineKingdom/Kingdom/"+kingdom.getName()+"/grade/"+name+"/perms.yml");
		memberFile = new File("./plugins/HumineKingdom/Kingdom/"+kingdom.getName()+"/grade/"+name+"/member.yml");
		if(registerindata == true){
			try(PrintWriter print = new PrintWriter(new FileOutputStream(new File("./plugins/HumineKingdom/DATA/grade.yml"), true))){
				print.println(name+" = "+kingdom.getName());
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		if(!permFile.exists()){
			try{
				permFile.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}
		}else{
			try(FileInputStream fis = new FileInputStream(permFile)){
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(fis);
				while(sc.hasNext()){
					perms.add(sc.nextLine());
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		if(!memberFile.exists()){
			try{
				memberFile.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}
		}else{
			try(FileInputStream fis = new FileInputStream(memberFile)){
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(fis);
				while(sc.hasNext()){
					pls.add(Bukkit.getOfflinePlayer(sc.nextLine()));
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		update();
		
	}
	
	//geters
	public ArrayList<String> getAllPerms(){
		return perms;
	}
	
	public ArrayList<OfflinePlayer> getAllPlayer(){
		return pls;
	}
	
	public String getName(){
		return name;
	}
	
	public Kingdom getKingdom(){
		return kingdom;
	}
	
	public File getPermFile(){
		return permFile;
	}
	
	public boolean containPlayer(OfflinePlayer offlinePlayer){
		for(OfflinePlayer pls : pls){
			if(offlinePlayer.getName().equalsIgnoreCase(pls.getName())){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasPerm(String perm){
		
		for(String str : perms){
			if(str.equalsIgnoreCase(perm)){
				return true;
			}
		}
		
		return false;
	}
	
	//seters
	public void addNewPerms(String perm){
		perms.add(perm);
		try(PrintWriter print = new PrintWriter(new FileOutputStream(permFile, true))){
			print.println(perm);
		}catch(IOException e){
			e.printStackTrace();
		}
		for(OfflinePlayer op : pls){
			if(op.isOnline()){
				op.getPlayer().closeInventory();
				GradeManager.addPerm(op.getPlayer(), perm);
			}
		}
		
		permUpdate();
	}
	
	public void removePerm(String perm){
		perms.remove(perm);
		permFile.delete();
		try{
			permFile.createNewFile();
		}catch(IOException e){
			e.printStackTrace();
		}
		try(PrintWriter print = new PrintWriter(new FileOutputStream(permFile, true))){
			for(String str : perms){
				print.println(str);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		for(OfflinePlayer op : pls){
			if(op.isOnline()){
				op.getPlayer().closeInventory();
				GradeManager.removePerm(op.getPlayer(), perm);
			}
		}
		
		permUpdate();
		
	}
	
	public void addNewPlayer(OfflinePlayer p, boolean register){
		
		pls.add(p);
		if(p.isOnline()){
			for(String perm : perms){
				p.getPlayer().closeInventory();
				GradeManager.addPerm(p.getPlayer(), perm);
			}
		}
		if(register == true){
			try(PrintWriter print = new PrintWriter(new FileOutputStream(memberFile, true))){
				print.println(p.getName());
			}catch(IOException e){
				e.printStackTrace();
			}
			//kingdom File
			ArrayList<String> tempo = new ArrayList<String>();
			try(FileInputStream fis = new FileInputStream(HumineKingdom.getDataKingdom())){
				@SuppressWarnings("resource")
				Scanner scan = new Scanner(fis);
				while(scan.hasNext()){
					String line = scan.nextLine();
					String[] args = line.split(" = ");
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
				
				print.println(p.getName()+" = "+kingdom.getName()+" = "+name);
			}catch(IOException e){
				e.printStackTrace();
			}

			GradeManager.refresh();
		}
	}
	
	public void removePlayer(OfflinePlayer offlinePlayer){
		System.out.println("remove "+offlinePlayer.getName()+" to "+name);
		pls.remove(offlinePlayer);
		
		if(offlinePlayer.isOnline()){
			for(String perms : perms){
				if(offlinePlayer.isOnline()){
					offlinePlayer.getPlayer().closeInventory();
					GradeManager.removePerm(offlinePlayer.getPlayer(), perms);
				}
			}
		}
		ArrayList<String> tempo = new ArrayList<String>();
		try(FileInputStream fis = new FileInputStream(memberFile)){
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(fis);
			while(scan.hasNext()){
				String line = scan.nextLine();
				if(!offlinePlayer.getName().equalsIgnoreCase(line)){
					tempo.add(line);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		memberFile.delete();
		try{
			memberFile.createNewFile();
		}catch(IOException e){
			e.printStackTrace();
		}
		try(PrintWriter print = new PrintWriter(new FileOutputStream(memberFile, true))){
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
				if(HumineKingdom.getPlayerkingdom(offlinePlayer) != null){
					print.println(offlinePlayer.getName()+" = "+HumineKingdom.getPlayerkingdom(offlinePlayer).getName()+" = Aucun");
				}
			}catch(IOException e){
				e.printStackTrace();
			}
			
			GradeManager.refresh();
		
	}

	public void delete() {
		for(OfflinePlayer pls : pls){
			new Grade(name, kingdom, false).removePlayer(pls);
			new Grade("Aucun", kingdom, false).addNewPlayer(pls, true);
		}
		memberFile.delete();
		permFile.delete();
		
		
		ArrayList<String> tempo = new ArrayList<String>();
		
		try(FileInputStream fis = new FileInputStream(HumineKingdom.getDataGrade())){
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(fis);
			while(scan.hasNext()){
				String line = scan.nextLine();
				String args[] = line.split(" = ");
				if(!(args[0].equalsIgnoreCase(name) && args[1].equalsIgnoreCase(kingdom.getName()))){
					tempo.add(line);
				}else{
					System.out.println("-"+line);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		HumineKingdom.reloadGradeFile();
		try(PrintWriter print = new PrintWriter(new FileOutputStream(HumineKingdom.getDataGrade(), true))){
			for(String str : tempo){
				print.println(str);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		new File("./plugins/HumineKingdom/Kingdom/"+kingdom.getName()+"/grade/"+name+"/").delete();
		update();
		
		
	}
	
	private void permUpdate(){
		for(OfflinePlayer op : kingdom.getAllMember()){
			if(op.isOnline()){
				if(op.getPlayer().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_PURPLE+"Liste des permissions") && op.getPlayer().getOpenInventory().getItem(13).getItemMeta().getDisplayName().replace("§9", "").equalsIgnoreCase(name)){
					op.getPlayer().closeInventory();
					MenuList.permsListMenu(kingdom, HumineKingdom.getGrade(name, kingdom)).openForPlayer(op.getPlayer());
				}
			}
		}
	}
	
	private void update(){
		for(OfflinePlayer op : kingdom.getAllMember()){
			if(op.isOnline()){
				if(op.getPlayer().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_PURPLE+"Liste des grades")){
					op.getPlayer().closeInventory();
					MenuList.gradeListMenu(kingdom).openForPlayer(op.getPlayer());
				}
				if(op.getPlayer().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_PURPLE+"Choisire un grade")){
					op.getPlayer().closeInventory();
					MenuList.MemberMenu(kingdom);
				}
				if((op.getPlayer().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.BLUE+"Grade") || op.getPlayer().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_PURPLE+"Liste des permissions") || op.getPlayer().getOpenInventory().getTitle().equalsIgnoreCase(ChatColor.DARK_PURPLE+"Ajouter une permission")) && op.getPlayer().getOpenInventory().getItem(13).getItemMeta().getDisplayName().replace("§9", "").equalsIgnoreCase(name)){
					op.getPlayer().closeInventory();
					MenuList.gradeListMenu(kingdom).openForPlayer(op.getPlayer());
					op.getPlayer().sendMessage(ChatColor.DARK_GRAY+"Le grade "+name+" vient d'être supprimé.");
				}
			}
		}
	}
	
	

}

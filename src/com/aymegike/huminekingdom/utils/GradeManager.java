package com.aymegike.huminekingdom.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.objets.Grade;

public class GradeManager {
	
	
	private static HashMap<Player,PermissionAttachment> hm = new HashMap<Player,PermissionAttachment>();
	
	private static ArrayList<Grade> grades = new ArrayList<Grade>();

	public static void loadGrades(){
		
		File grade = new File("./plugins/HumineKingdomV2/DATA/grade.yml");
		try(FileInputStream fis = new FileInputStream(grade)){
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(fis);
			while(sc.hasNext()){
				String[] args = sc.nextLine().split(" = ");
				Grade g = new Grade(args[0], HumineKingdom.getKingdom(args[1]), false);
				File member = new File("./plugins/HumineKingdomV2/Kingdom/"+args[1]+"/grade/"+args[0]+"/member.yml");
				addPlayer(member, g);
				grades.add(g);
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("deprecation")
	private static void addPlayer(File file, Grade g){
		
		try(FileInputStream fis = new FileInputStream(file)){
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(fis);
			while(scan.hasNext()){
				g.addNewPlayer(Bukkit.getOfflinePlayer(scan.nextLine()), false);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
		
	public static void refresh(){
		grades.clear();
		loadGrades();
	}
	
	
	public static ArrayList<Grade> getGrades(){
		return grades;
	}
	
	public static void addGrade(Grade g){
		grades.add(g);
	}
	
	public static void addPerm(Player p, String perm){		
		if(hm.containsKey(p)){
			System.out.println("Ajout de la permission: "+perm+" pour "+p.getName());
			hm.get(p).setPermission(perm, true);
		}
	}
	
	public static void removePerm(Player p, String perm){		
		if(hm.containsKey(p)){
			System.out.println("Suppression de la permission: "+perm+" pour "+p.getName());
			hm.get(p).unsetPermission(perm);
		}		
	}
	
	@SuppressWarnings("deprecation")
	public static void addPlayer(Player p){
		PermissionAttachment permA = p.addAttachment(Bukkit.getPluginManager().getPlugin("HumineKingdom"));
		hm.put(p, permA);
		System.out.println("Ajout du joueur "+p.getName()+" dans la list de permission");
		if(HumineKingdom.getPlayerGrade(Bukkit.getOfflinePlayer(p.getName())) != null){
			for(String perm : HumineKingdom.getPlayerGrade(Bukkit.getOfflinePlayer(p.getName())).getAllPerms()){
				addPerm(p, perm);
			}
		}
	}
	
	public static void removePlayer(Player p){
		hm.remove(p);
	}
	

}
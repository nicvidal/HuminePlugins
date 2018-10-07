package com.aymegike.huminekingdom.utils.models;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.aymegike.huminekingdom.utils.managers.FileManager;


public class Grade {
	
	private Kingdom kingdom;
	private String name;
	
	private File membersFile;
	private File permsFile;
	
	private ArrayList<String> permissions = new ArrayList<String>();
	private ArrayList<String> members = new ArrayList<String>();
	
	public Grade(Kingdom kingdom, String name) {
		
		this.kingdom = kingdom;
		this.name = name;
		
		membersFile = new File(FileManager.KINGDOM_FILE+"/"+kingdom.getName()+"/grades/"+name+"/members.craft");
		permsFile = new File(FileManager.KINGDOM_FILE+"/"+kingdom.getName()+"/grades/"+name+"/permissions.craft");
		
		new com.aypi.manager.FileManager(membersFile);
		new com.aypi.manager.FileManager(permsFile);
		
		if (membersFile.exists()) {
			members = new com.aypi.manager.FileManager(membersFile).getTextFile();
		}
		
		if (permsFile.exists()) {
			permissions = new com.aypi.manager.FileManager(permsFile).getTextFile();
		}
		
	}
	
	public void addPermission(String permission) {
		permissions.add(permission);
		new com.aypi.manager.FileManager(permsFile).printLine(permission);
	}
	
	public void addPermission(ArrayList<String> permission) {
		for (String str : permission) {
			permissions.add(str);
			new com.aypi.manager.FileManager(permsFile).printLine(str);
		}
	}
	
	public void removePermission(String permission) {
		permissions.remove(permission);
		new com.aypi.manager.FileManager(permsFile).removeAllLine(permission);
	}
	
	public void addMember(OfflinePlayer player) {
		members.add(player.getUniqueId().toString());
		new com.aypi.manager.FileManager(membersFile).printLine(player.getUniqueId().toString());
	}
	
	public void removeMember(OfflinePlayer player) {
		members.remove(player.getUniqueId().toString());
		new com.aypi.manager.FileManager(membersFile).removeAllLine(player.getUniqueId().toString());
	}
	
	public ArrayList<String> getPermissions(){
		return permissions;
	}
	
	public ArrayList<OfflinePlayer> getMembers(){
		
		ArrayList<OfflinePlayer> op = new ArrayList<OfflinePlayer>();
		
		for(String str : members) {
			op.add(Bukkit.getOfflinePlayer(UUID.fromString(str)));
		}
		
		return op;
		
	}
	
	public Kingdom getKingdom() {
		return kingdom;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean containPermission(String permission) {
		boolean yes = false;
		
		for (String str : permissions) {
			if (permission.equalsIgnoreCase(str)) {
				yes = true;
			}
		}
		
		return yes;
	}

	public void delet() {
		membersFile.delete();
		permsFile.delete();
		new File(FileManager.KINGDOM_FILE+"/"+kingdom.getName()+"/grades/"+name).delete();
	}

}

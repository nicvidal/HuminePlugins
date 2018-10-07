package com.aymegike.huminekingdom.utils.managers;

import java.io.File;
import java.util.ArrayList;

import com.aymegike.huminekingdom.utils.models.Kingdom;

public class KingdomManager {
	
	private ArrayList<Kingdom> kingdoms = new ArrayList<Kingdom>();
	private File kingdomList;
	
	public KingdomManager() {
		
		kingdomList = new File(FileManager.KINGDOM_FILE);
		File[] list = kingdomList.listFiles();
		
		for (File file : list) {
			kingdoms.add(new Kingdom(file.getName()));
		}
		
	}
	
	public void addKingdom(Kingdom kingdom) {
		kingdoms.add(kingdom);
	}
	
	public void removeKingdom(Kingdom kingdom) {
		kingdoms.remove(kingdom); 
	}
	
	public ArrayList<Kingdom> getKingdomList(){
		return kingdoms;
	}

}

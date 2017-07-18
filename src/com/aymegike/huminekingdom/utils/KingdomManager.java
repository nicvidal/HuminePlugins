package com.aymegike.huminekingdom.utils;

import java.io.File;
import java.util.ArrayList;

import com.aymegike.huminekingdom.utils.objets.Kingdom;

public class KingdomManager {
	
	private static ArrayList<Kingdom> kingdoms = new ArrayList<Kingdom>();

	public static void loadKingdom(){
		kingdoms.clear();
		File kingdom = new File("./plugins/HumineKingdomV2/Kingdom");
		File[] path = kingdom.listFiles();
		for(int i = 0 ; i<path.length ; i++){
			Kingdom k = new Kingdom(path[i].getName());
			kingdoms.add(k);	
		}
		
		
	}
	
	public static ArrayList<Kingdom> getKingdoms(){
		return kingdoms;
	}
	
	public static void setKingdom(Kingdom k){
		kingdoms.add(k);
	}

}

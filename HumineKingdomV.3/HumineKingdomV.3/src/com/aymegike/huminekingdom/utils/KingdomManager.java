package com.aymegike.huminekingdom.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.objets.Kingdom;

public class KingdomManager {
	
	private static ArrayList<Kingdom> kingdoms = new ArrayList<Kingdom>();

	public static void loadKingdom(){
		kingdoms.clear();
		
		try {
		    BufferedReader in = new BufferedReader(new FileReader(HumineKingdom.getDataKingdom()));
		    
		    String line;
		    while ((line = in.readLine()) != null) {
		    	if (line.split(" = ")[1].equals("glory")) {
					Kingdom k = new Kingdom(line.split(" = ")[0], Integer.parseInt(line.split(" = ")[2]));
					kingdoms.add(k);	
		    	}
		    }
		    in.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		sort();
	}
	
	@SuppressWarnings("unchecked")
	public static void sort() {
		Collections.sort(kingdoms);
	}
	
	public static ArrayList<Kingdom> getKingdoms(){
		return kingdoms;
	}
	
	public static void setKingdom(Kingdom k){
		kingdoms.add(k);
	}

}

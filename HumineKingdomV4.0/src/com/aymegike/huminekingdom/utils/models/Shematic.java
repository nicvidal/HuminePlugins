package com.aymegike.huminekingdom.utils.models;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import com.aymegike.huminekingdom.utils.BlockList;
import com.aypi.manager.FileManager;

public class Shematic {
	
	private String name;
	private Kingdom kingdom;
	private ShieldGenerator shieldGenerator;
	
	private File file;
	
	private int task;
	private int index = 0;	
	
	public Shematic(Kingdom kingdom, String name, ShieldGenerator sg) {
		
		this.name = name;
		this.kingdom = kingdom;
		this.shieldGenerator = sg;
		
		file = new File(kingdom.getShematicFile().getAbsolutePath()+"/"+name+"_"+sg.getLocation().getWorld().getName()+"_"+sg.getLocation().getBlockX()+"_"+sg.getLocation().getBlockY()+"_"+sg.getLocation().getBlockZ()+".craft");
		
		if (!file.exists()) {
			
			ArrayList<String> print = new ArrayList<String>();
			
			for (Location loc : sg.getZone().getSquare().getScareLoc()) {
				if (!BlockList.getBlackList(loc.getBlock().getType())) {
					
//					BlockData bd = (BlockData) loc.getBlock().getBlockData();
//					if (bd instanceof Rotatable) {
//						Rotatable rot = (Rotatable) bd;		
//						print.add(loc.getWorld()+" "+loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ()+" "+loc.getBlock().getType().name()+" "+rot.getRotation().name());
//					} else {
						print.add(loc.getWorld().getName()+" "+loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ()+" "+loc.getBlock().getType().name());
//					}					
				}
			}
			
			FileManager fm = new FileManager(file);
			fm.printList(print);
		}
		
	}
	
	public void rebuild() {
		
		ArrayList<String> str = new FileManager(file).getTextFile();
		
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("HumineKingdom"), new Runnable() {
			
			@Override
			public void run() {
				
				if (index >= str.size()) {
					Bukkit.getScheduler().cancelTask(task);
					index = 0;
					return;
				}
				
				String[] line = str.get(index).split(" ");
				
				while (new Location(Bukkit.getWorld(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3])).getBlock().getType() == Material.matchMaterial(line[4])) {
					index++;
					if (index >= str.size()) {
						Bukkit.getScheduler().cancelTask(task);
						return;
					}
					line = str.get(index).split(" ");
				}
				if (BlockList.getWhitList(new Location(Bukkit.getWorld(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3])).getBlock().getType()))
					new Location(Bukkit.getWorld(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3])).getBlock().setType(Material.matchMaterial(line[4]));
				
				index++;
				
			}
		}, 1, 1);
		
	}
	
	public String getName() {
		return name;
	}
	
	public File getFile() {
		return file;
	}

}
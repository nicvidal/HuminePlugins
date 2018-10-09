package com.aymegike.huminekingdom.utils.models;
import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.Stairs.Shape;

import com.aymegike.huminekingdom.utils.BlockList;
import com.aypi.manager.FileManager;

public class Shematic {
	
	private String name;
	
	private File file;
	
	private int task;
	private int index = 0;	
	
	public Shematic(Kingdom kingdom, String name, ShieldGenerator sg) {
		
		this.name = name;
		
		file = new File(kingdom.getShematicFile().getAbsolutePath()+"/"+name+"_"+sg.getLocation().getWorld().getName()+"_"+sg.getLocation().getBlockX()+"_"+sg.getLocation().getBlockY()+"_"+sg.getLocation().getBlockZ()+".craft");
		
		if (!file.exists()) {
			
			ArrayList<String> print = new ArrayList<String>();
			
			for (Location loc : sg.getZone().getSquare().getScareLoc()) {
				if (!BlockList.getBlackList(loc.getBlock().getType())) {
					
					print.add(getLineToPrint(loc));
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
				if (BlockList.getWhitList(new Location(Bukkit.getWorld(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3])).getBlock().getType())) {
					
					new Location(Bukkit.getWorld(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3])).getBlock().setType(Material.matchMaterial(line[4]));
					
					
					//SET ++
					Block block = new Location(Bukkit.getWorld(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3])).getBlock();
					
					if (block.getBlockData() instanceof Directional ) { //CLASSIQUE
						
						Directional dir = (Directional) block.getBlockData();
						
						dir.setFacing(BlockFace.valueOf(line[5]));
						
						block.setBlockData(dir);
						
					}
					
					if (block.getBlockData() instanceof Stairs) { //STAIRS
						
						Stairs s = (Stairs) block.getBlockData();
						
						s.setHalf(Half.valueOf(line[6]));
						s.setShape(Shape.valueOf(line[7]));
						
						block.setBlockData(s);
						
					} 
					
				}
					
				
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
	
	private String getLineToPrint(Location loc) {
		
		String lineToPrint = loc.getWorld().getName()+" "+loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ()+" "+loc.getBlock().getType().name();
		
		if (loc.getBlock().getBlockData() instanceof Directional ) {
			
			Directional dir = (Directional) loc.getBlock().getBlockData();
			
			lineToPrint +=" "+dir.getFacing().toString();
			
		}
		
		if (loc.getBlock().getBlockData() instanceof Stairs) {
			
			Stairs s = (Stairs) loc.getBlock().getBlockData();
			
			lineToPrint +=" "+s.getHalf().toString()+" "+s.getShape().toString();
		}
		
		if (loc.getBlock().getBlockData() instanceof Door) {
			
			Door d = (Door) loc.getBlock().getBlockData();
			//d.get
			
		}
		
		return lineToPrint;
		
	}

}
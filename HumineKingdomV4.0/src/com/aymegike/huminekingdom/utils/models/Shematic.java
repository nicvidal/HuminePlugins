package com.aymegike.huminekingdom.utils.models;

import java.awt.Shape;
import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;
import org.bukkit.material.Directional;
import org.bukkit.material.Stairs;

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
					
					setFacingDirection(BlockFace.NORTH, loc.getBlock());
					
					print.add(loc.getWorld().getName()+" "+loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ()+" "+loc.getBlock().getType().name());
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
	
	private void setFacingDirection(final BlockFace face, final Block block) {
	    
	    BlockData bd = block.getBlockData();

	    if(bd instanceof Rotatable){
	    	Rotatable rot = (Rotatable) bd;
	       	rot.setRotation(face);
	       	block.setBlockData(rot);
	    }
	    
	    if (block.getState().getData() instanceof Stairs) {
	    	Stairs stairs = (Stairs) block.getState().getData();
	    	stairs.setFacingDirection(face);	
	    	block.get
	    }
	    
	    if (block.getState().getData() instanceof Directional) {
	    	//System.out.println(((Directional) block.getState().getData()).getFacing());
	    }
	}

}
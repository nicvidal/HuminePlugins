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
import org.bukkit.block.data.Orientable;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.Comparator;
import org.bukkit.block.data.type.Comparator.Mode;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Slab.Type;
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
					
					if (block.getBlockData() instanceof Slab) {
						Slab s = (Slab) block.getBlockData();
						
						s.setType(Type.valueOf(line[5]));
						
						block.setBlockData(s);
					}
					
					if (block.getBlockData() instanceof Stairs) { //STAIRS
						
						Stairs s = (Stairs) block.getBlockData();
						
						s.setHalf(Half.valueOf(line[6]));
						s.setShape(Shape.valueOf(line[7]));
						
						block.setBlockData(s);
						
					} 
					
					if (block.getBlockData() instanceof Repeater) {
						
						Repeater rp = (Repeater) block.getBlockData();
						
						rp.setDelay(Integer.parseInt(line[6]));
						
						block.setBlockData(rp);
						
					}
					
					if (block.getBlockData() instanceof Comparator) {
						
						Comparator c = (Comparator) block.getBlockData();
						
						c.setMode(Mode.valueOf(line[6]));
						
						block.setBlockData(c);
					}
					
					
//					if (block.getBlockData() instanceof Door) { //DOORS
//										
//						
//						if (Half.valueOf(line[7]) == Half.BOTTOM) {
//							
//							Block b = new Location(Bukkit.getWorld(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2])+1, Integer.parseInt(line[3])).getBlock();
//							//b.setType(Material.matchMaterial(line[4]));
//							
//							Door d = (Door) block.getBlockData();
//							d.setHinge(Hinge.valueOf(line[6]));
//							d.setHalf(Half.BOTTOM);
//							
//							block.setBlockData(d);
//							
//							d = (Door) b.getBlockData();
//							d.setHinge(Hinge.valueOf(line[6]));
//							d.setHalf(Half.TOP);
//							
//							block.setBlockData(d);
//							
//							
//							
//						} else {
//							block.setType(Material.AIR);
//						}
//						
//					} 
					
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
		
		if (loc.getBlock().getBlockData() instanceof Directional ) { //DIRECTIONALS
			
			Directional dir = (Directional) loc.getBlock().getBlockData();
			
			lineToPrint +=" "+dir.getFacing().toString();
			
		}
		
		if (loc.getBlock().getBlockData() instanceof Orientable) {
			System.out.println(loc.getBlock().getType().toString());
		}
		
		if (loc.getBlock().getBlockData() instanceof Stairs) { //STAIRS
			
			Stairs s = (Stairs) loc.getBlock().getBlockData();
			
			lineToPrint +=" "+s.getHalf().toString()+" "+s.getShape().toString();
		}
		
		if (loc.getBlock().getBlockData() instanceof Slab) { //SLAB
			
			Slab s = (Slab) loc.getBlock().getBlockData();
			
			lineToPrint += " "+s.getType().toString();
			
		}
		
		if (loc.getBlock().getBlockData() instanceof Door) { //DOORS
			
			Door d = (Door) loc.getBlock().getBlockData();
			lineToPrint +=" "+d.getHinge().toString()+" "+d.getHalf().toString();
			
		}
		
		if (loc.getBlock().getBlockData() instanceof Repeater) {
			Repeater rp = (Repeater) loc.getBlock().getBlockData();
			
			lineToPrint += " "+rp.getDelay();
		}
		
		if (loc.getBlock().getBlockData() instanceof Comparator) {
			Comparator c = (Comparator) loc.getBlock().getBlockData();
			lineToPrint += " "+c.getMode().toString();
		}
		
		return lineToPrint;
		
	}

}
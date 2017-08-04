package com.aymegike.huminekingdom.utils;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.aymegike.huminekingdom.HumineKingdom;

import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;

public class ParticleManager {
	
	static int task;
	static int task2;
	static int task3;
	static int task4;
	static int task5;
	
	private static boolean continueParticle;
	private static boolean continueGivedParticle;
	
	public static void particleSpiral(Location loc, World world){
		
		double radius = 5;
		int i = 1;
		while(new Location(world, loc.getX(), loc.getY()+i, loc.getZ()).getBlock().getType() == Material.AIR && i < 6 && loc.getBlockY()+i < 255){
			i++;
		}
		double maxHeight = i-1;
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("HumineKingdom"), new Runnable(){
			float y = 0;
			@Override
			public void run() {
				y += 0.1;
					
				double x = Math.sin((y * radius)+1);
				double z = Math.cos((y * radius)+1);
				PacketPlayOutWorldParticles particle = new PacketPlayOutWorldParticles(EnumParticle.END_ROD, false,
						((float) (loc.getX() + x+0.5)), 
						((float) (loc.getY() + y)), 
						((float) (loc.getZ() + z+0.5)),
						0, 0, 0, 1, 0, null);
				for(Player pls : Bukkit.getOnlinePlayers()){
					((CraftPlayer)pls).getHandle().playerConnection.sendPacket(particle);
				}
				
				PacketPlayOutWorldParticles particl = new PacketPlayOutWorldParticles(EnumParticle.PORTAL, false,
						((float) (loc.getBlockX()+0.5)), 
						((float) (loc.getBlockY()+maxHeight)), 
						((float) (loc.getBlockZ()+0.5)),
						0, 0, 0, 10, 50, null);
				for(Player pls : Bukkit.getOnlinePlayers()){
					((CraftPlayer)pls).getHandle().playerConnection.sendPacket(particl);
				}
				
				if(y > maxHeight){
					Bukkit.getScheduler().cancelTask(task);
				}
				
				
			
				
			}
		}, 1, 0);
		
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule doFireTick false");
		
		
		
		task2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("HumineKingdom"), new Runnable(){
			
			boolean fire = false;
			
			@Override
			public void run() {
				if(fire == false){
					new Location(world, loc.getX(), loc.getY()+maxHeight, loc.getZ()).getBlock().setType(Material.DRAGON_EGG);
					world.strikeLightning(new Location(world, loc.getX(), loc.getY()+maxHeight, loc.getZ()));
					
					PacketPlayOutWorldParticles particle = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_HUGE, false,
							((float) (loc.getX() +0.5)), 
							((float) (loc.getY() +maxHeight)), 
							((float) (loc.getZ() +0.5)),
							0, 0, 0, 1, 0, null);
					for(Player pls : Bukkit.getOnlinePlayers()){
						((CraftPlayer)pls).getHandle().playerConnection.sendPacket(particle);
					}
					fire = true;
				}else{
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule doFireTick true");
					Bukkit.getScheduler().cancelTask(task2);
				}
			}
		}, 20*5, 20*5);
	}

	public static void loadParticle(Location location) {
		continueParticle = true;
		task3 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("HumineKingdom"), new Runnable(){
			double radius = 5;
			float y = 0;
			@Override
			public void run() {
				PacketPlayOutWorldParticles particl = new PacketPlayOutWorldParticles(EnumParticle.PORTAL, false,
						((float) (location.getBlockX()-0.5)), 
						((float) (location.getBlockY()+0.5)), 
						((float) (location.getBlockZ()+0.5)),
						0, 0, 0, 30, 100, null);
				for(Player pls : Bukkit.getOnlinePlayers()){
					((CraftPlayer)pls).getHandle().playerConnection.sendPacket(particl);
				}
				
				y += 0.1;
				double x = Math.sin((y * radius)+1);
				double z = Math.cos((y * radius)+1);
				PacketPlayOutWorldParticles particle = new PacketPlayOutWorldParticles(EnumParticle.END_ROD, false,
						((float) (location.getX() + x+0.5)), 
						((float) (location.getY() + 1.5)), 
						((float) (location.getZ() + z+0.5)),
						0, 0, 0, 1, 0, null);
				for(Player pls : Bukkit.getOnlinePlayers()){
					((CraftPlayer)pls).getHandle().playerConnection.sendPacket(particle);
				}
				if(continueParticle == false){
					Bukkit.getScheduler().cancelTask(task3);
				}
			}
		}, 5, 5);	
		
		continueParticle = true;
		
	}
	
	public static void presentParticle(Location location){
		continueGivedParticle = true;
		task4 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("HumineKingdom"), new Runnable(){
			
			@Override
			public void run() {
				Random r1 = new Random();
				Random r3 = new Random();
				PacketPlayOutWorldParticles particl = new PacketPlayOutWorldParticles(EnumParticle.DRAGON_BREATH, false,
						((float) (location.getBlockX()-1 + r1.nextInt(3))), 
						((float) (location.getBlockY()+1.5)), 
						((float) (location.getBlockZ()-1) + r3.nextInt(3)),
						0, 0, 0, 1, 50, null);
				for(Player pls : Bukkit.getOnlinePlayers()){
					((CraftPlayer)pls).getHandle().playerConnection.sendPacket(particl);
				}
				
				
				HumineKingdom.GIVED = true;
				
				if(continueGivedParticle == false){
					HumineKingdom.GIVED = false;
					Bukkit.getScheduler().cancelTask(task4);
				}
			}
		}, 5, 5);	
		
	}
	
	public static void popGived(Location loc) {
		
		task5 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("HumineKingdom"), new Runnable(){
			
			double t = 0;
			
			@Override
			public void run() {
				
				t = t - 0.1*Math.PI;
				for(double theta = 0 ; theta <= 2*Math.PI ; theta = theta + Math.PI/8){
					double x = t*Math.cos(theta);
					double y = Math.exp(-0.1*t) * Math.sin(t) + 1.5;
					double z = t*Math.sin(theta);
					loc.add(x, y, z);
					PacketPlayOutWorldParticles particl = new PacketPlayOutWorldParticles(EnumParticle.FIREWORKS_SPARK, false,
							((float) (loc.getBlockX())), 
							((float) (loc.getBlockY())), 
							((float) (loc.getBlockZ())),
							0, 0, 0, 1, 0, null);
					for(Player pls : Bukkit.getOnlinePlayers()){
						((CraftPlayer)pls).getHandle().playerConnection.sendPacket(particl);
					}
					loc.subtract(x, y ,z);
					
					 theta = theta + Math.PI/64;
					 x = t*Math.cos(theta);
					 y = 2*Math.exp(-0.1*t) * Math.sin(t) + 1.5;
					 z = t*Math.sin(theta);
					 loc.add(x,y,z);
					 PacketPlayOutWorldParticles particl1 = new PacketPlayOutWorldParticles(EnumParticle.SPELL_WITCH, false,
								((float) (loc.getBlockX())), 
								((float) (loc.getBlockY())), 
								((float) (loc.getBlockZ())),
								0, 0, 0, 1, 1, null);
						for(Player pls : Bukkit.getOnlinePlayers()){
							((CraftPlayer)pls).getHandle().playerConnection.sendPacket(particl1);
						}
					 loc.subtract(x,y,z);
					
					if(t < 0){
						Bukkit.getScheduler().cancelTask(task5);
					}
				}
				
			}
		}, 1, 1);	
		
	}
	
	public static void downGivedParticle(){
		continueGivedParticle = false;
	}
	
	public static void downParticle(){
		continueParticle = false;
	}

	
	
	

}

package com.aymegike.huminekingdom.utils.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;

public class ParticleManager {
	
	static int task;
	static int task2;
	static int task3;
	static int task4;
	static int task5;
	
	private static boolean continueParticle;
	
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
				
		task2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("HumineKingdom"), new Runnable(){
		
			@Override
			public void run() {
					new Location(world, loc.getX(), loc.getY()+maxHeight, loc.getZ()).getBlock().setType(Material.DRAGON_EGG);
					
					PacketPlayOutWorldParticles particle = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_HUGE, false,
							((float) (loc.getX() +0.5)), 
							((float) (loc.getY() +maxHeight)), 
							((float) (loc.getZ() +0.5)),
							0, 0, 0, 1, 0, null);
					for(Player pls : Bukkit.getOnlinePlayers()){
						((CraftPlayer)pls).getHandle().playerConnection.sendPacket(particle);
						pls.playSound(new Location(world, loc.getX(), loc.getY()+maxHeight, loc.getZ()), Sound.ENTITY_ENDERDRAGON_GROWL, 50, 50);
						pls.playSound(new Location(world, loc.getX(), loc.getY()+maxHeight, loc.getZ()), Sound.ENTITY_LIGHTNING_IMPACT, 50, 50);
						pls.playSound(new Location(world, loc.getX(), loc.getY()+maxHeight, loc.getZ()), Sound.ENTITY_LIGHTNING_THUNDER, 50, 50);
					}
					Bukkit.getScheduler().cancelTask(task2);
				
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
						((float) (location.getBlockX()+0.5)), 
						((float) (location.getBlockY()+1)), 
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
	
	public static void downParticle(){
		continueParticle = false;
	}

	
	
	

}

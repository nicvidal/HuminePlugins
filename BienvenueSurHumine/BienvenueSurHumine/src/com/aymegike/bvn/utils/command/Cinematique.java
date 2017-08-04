package com.aymegike.bvn.utils.command;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.aymegike.bvn.Main;

import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;

public class Cinematique {
	
	boolean upDown = true;
	int second = 0;
	
	Location loc;
	
	public Cinematique(Player player){
		player.teleport(Main.getSpaceShip());
		player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 3, 1));
		
		startTravel(player);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("WelcomeOnHumineCraft"), new Runnable(){
			
			@Override
			public void run() {
				
				if(upDown == true){
					
					Location loc = player.getLocation();
					player.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()+1));
					
					upDown = false;
				}else{
					
					Location loc = player.getLocation();
					player.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()-1));
					
					upDown = true;
				}
				
				
			}
			
		}, 1, 1);	
	}
	
	private void startTravel(Player player){
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("WelcomeOnHumineCraft"), new Runnable(){
			
			@Override
			public void run() {
				
				PacketPlayOutWorldParticles particl = new PacketPlayOutWorldParticles(EnumParticle.FLAME, false,
						((float) (Main.getSpaceShip().getBlockX()-0.5)), 
						((float) (Main.getSpaceShip().getBlockY()+0.5)), 
						((float) (Main.getSpaceShip().getBlockZ()+0.5)),
						0, 0, 0, 30, 1000, null);
				((CraftPlayer)player).getHandle().playerConnection.sendPacket(particl);
				
				Sound.
				
				second++;
			}
			
		}, 20, 10);	
	}
	
	private void choseLoc(){
		
		int x;
		int z;
		
		Random r;
		
		x = r.nextInt();
		z = r.nextInt();
		
		loc = new Location(Bukkit.getWorlds().get(0), x, 200, z);
		
		
	}

}

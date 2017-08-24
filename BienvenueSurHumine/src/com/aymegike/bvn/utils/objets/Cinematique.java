package com.aymegike.bvn.utils.objets;


import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.aymegike.bvn.Main;
import com.aymegike.bvn.event.PlayerFall;
import com.aymegike.bvn.utils.PlayerJoinManager;
import com.aymegike.bvn.utils.Title;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;

public class Cinematique {
	
	boolean upDown = true;
	int second = 0;
	
	int keepDown;
	int lockUp;
	int dontmoove;
	int atmosphere;
	int particle;
	
	Location loc;
	
	public Cinematique(Player player){
		
		for(int i = 0 ; i< 150 ; i++)
			player.sendMessage("");
		for(Player pls : Bukkit.getOnlinePlayers()){
			pls.hidePlayer(player);
			player.hidePlayer(pls);
		}
		player.setAllowFlight(true);
    	player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1, 1000));
		player.playSound(player.getLocation(), Sound.RECORD_13, 500, 1);

		
		keepDown = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("WelcomeOnHumineCraft"), new Runnable(){
		int whait = 0;	
			@Override
			public void run() {
				if(player.isOnline()){
					whait++;
					if(whait >= (20*8)-10){
						Bukkit.getScheduler().cancelTask(keepDown);
						spaceship(player);
					}
					if(whait == 1){
						Title.sendTitle(player, ChatColor.DARK_PURPLE+"Preparez - vous", null, 0, 0, 40);
					}
					if(whait == 40){
						Title.sendTitle(player, ChatColor.DARK_PURPLE+"Preparez - vous", ChatColor.WHITE+"Pour votre envol...", 0 ,Title.defaultFadeOut, 60);
					}
					player.teleport(Main.getSpawn());						
				}else{
					Bukkit.getScheduler().cancelTask(keepDown);
				}
			}
			
       	}, 1, 1);
            	
        
        
        
        
     }
	
	private void spaceship(Player player){
		player.teleport(Main.getSpaceShip());
		lockUp = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("WelcomeOnHumineCraft"), new Runnable(){
		int whait = 0;	
			@Override
			public void run() {
				if(player.isOnline()){
					whait+=2;
					if(whait >= 20*3){
						player.closeInventory();
						Location loc = player.getLocation();
						if(loc.getPitch() > 0){
							if(loc.getPitch() > 30)
								player.teleport(new Location(Main.getSpaceShip().getWorld(), Main.getSpaceShip().getX(), Main.getSpaceShip().getY(), Main.getSpaceShip().getZ(), loc.getYaw(), loc.getPitch()-2));
							else
								player.teleport(new Location(Main.getSpaceShip().getWorld(), Main.getSpaceShip().getX(), Main.getSpaceShip().getY(), Main.getSpaceShip().getZ(), loc.getYaw(), (float) (loc.getPitch()-1)));
						}else{
							Bukkit.getScheduler().cancelTask(lockUp);
					        Title.sendTitle(player, ChatColor.DARK_PURPLE+"Bienvenue", null);
					        lookUp(player);
						}
					}else{
						player.teleport(Main.getSpaceShip());
					}
				}else{
					Bukkit.getScheduler().cancelTask(lockUp);
				}
			}
			
         }, 1, 2);
	}
	
	private void lookUp(Player player){
		dontmoove = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("WelcomeOnHumineCraft"), new Runnable(){
			int tempo = 0;
			@Override
			public void run() {
				if(player.isOnline()){
					tempo++;
					if(tempo >= 20*35){
						player.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FIREBALL_EXPLODE, 500, 1);
						Bukkit.getScheduler().cancelTask(dontmoove);
						PacketPlayOutWorldParticles particle = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_HUGE, false,
								((float) (player.getLocation().getX())), 
								((float) (player.getLocation().getBlockY())), 
								((float) (player.getLocation().getZ())),
								0, 0, 0, 1, 0, null);
						
						((CraftPlayer)player).getHandle().playerConnection.sendPacket(particle);
						atmosphere(player);
						particleRandom(player);
					}
					Location loc = player.getLocation();
					if(loc.getX() != Main.getSpaceShip().getX() || loc.getY() != Main.getSpaceShip().getY() || loc.getZ() != Main.getSpaceShip().getZ()){
						player.teleport(new Location(Main.getSpaceShip().getWorld(), Main.getSpaceShip().getX(), Main.getSpaceShip().getY(), Main.getSpaceShip().getZ(), loc.getYaw(), loc.getPitch()));
					}
						
				}else{
					Bukkit.getScheduler().cancelTask(dontmoove);
				}
			}	
         }, 1, 1);
	}
	
	private void particleRandom(Player player){
		
		particle = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("WelcomeOnHumineCraft"), new Runnable(){
			
			@Override
			public void run() {
				if(player.isOnline()){
					Random r = new Random();
					int x = Main.getSpaceShip().getBlockX()+r.nextInt(5)-2;
					int y = Main.getSpaceShip().getBlockY()+r.nextInt(3);
					int z = Main.getSpaceShip().getBlockZ()+r.nextInt(5)-2;
					
					PacketPlayOutWorldParticles particle = new PacketPlayOutWorldParticles(EnumParticle.FLAME, false,
							((float) (x)), 
							((float) (y)), 
							((float) (z)),
							0, 0, 0, 10, 5, null);
					
					
					((CraftPlayer)player).getHandle().playerConnection.sendPacket(particle);
					
					particle = new PacketPlayOutWorldParticles(EnumParticle.SMOKE_LARGE, false,
							((float) (x)), 
							((float) (y)), 
							((float) (z)),
							0, 0, 0, 10, 0, null);
					((CraftPlayer)player).getHandle().playerConnection.sendPacket(particle);
					
					if(r.nextInt(10) < 1){
						 particle = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_HUGE, false,
								((float) (player.getLocation().getX())), 
								((float) (player.getLocation().getBlockY())), 
								((float) (player.getLocation().getZ())),
								0, 0, 0, 1, 0, null);
						
						((CraftPlayer)player).getHandle().playerConnection.sendPacket(particle);
					}
				}else{
					Bukkit.getScheduler().cancelTask(particle);
				}
			}
			
		}, 1, 1);
		
	}
		
		
	
	private void atmosphere(Player player){
		
		atmosphere = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("WelcomeOnHumineCraft"), new Runnable(){

			int whait = 0;
			int boum = 0;
			@Override
			public void run() {
				if(player.isOnline()){
					whait++;
					if(whait == 20*37-10){
						Title.sendTitle(player, ChatColor.WHITE+"Humine"+ChatColor.DARK_PURPLE+"Craft", ChatColor.DARK_PURPLE+"Votre imagination est la seule limite.", 20*3, 20*3, 20*10);
					}
					player.closeInventory();
					Location loc = player.getLocation();
					if(upDown == true){	
						player.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()+2));
						
						upDown = false;
					}else{
						player.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()-2));
						
						upDown = true;
					}
					loc = player.getLocation();
					if(player.getLocation().getYaw() < 90 && player.getLocation().getYaw() > -90){
						player.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw()+1, loc.getPitch()));
					}
					
					if(player.getLocation().getYaw() > 91 || player.getLocation().getYaw() < -90){
						player.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw()-1, loc.getPitch()));
					}
					
					if(player.getLocation().getPitch() > 5){
						player.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()-3));
					}
					if(player.getLocation().getPitch() < -5){
						player.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()+3));
					}
					
					if(whait == 20*82-10){				
						Bukkit.getScheduler().cancelTask(particle);
					}
					if(whait > 20*82-10){
						boum++;
						PacketPlayOutWorldParticles particle = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_HUGE, false,
								((float) (player.getLocation().getX())), 
								((float) (player.getLocation().getBlockY())), 
								((float) (player.getLocation().getZ())),
								0, 0, 0, 1, 0, null);
						
						((CraftPlayer)player).getHandle().playerConnection.sendPacket(particle);
						player.playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FIREBALL_EXPLODE, 500, 1);
						if(boum == 20){
							Bukkit.getScheduler().cancelTask(atmosphere);
							finalExplosion(player);
						}
					}
				}else{
					Bukkit.getScheduler().cancelTask(atmosphere);
				}
				
				
			}
			
		}, 1, 1);
	}
	
	private void finalExplosion(Player player){
		for(Preview pv : PlayerJoinManager.pvs){
			if(pv.getPlayer() == player){
				pv.stop();
				player.setAllowFlight(true);
				player.setFlying(false);
				
				player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000, 120));
				PlayerFall.addFalling(player);
				PacketPlayOutWorldParticles particle = new PacketPlayOutWorldParticles(EnumParticle.EXPLOSION_HUGE, false,
						((float) (player.getLocation().getX())), 
						((float) (player.getLocation().getBlockY())), 
						((float) (player.getLocation().getZ())),
						0, 0, 0, 1, 0, null);
				
				((CraftPlayer)player).getHandle().playerConnection.sendPacket(particle);
				for(Player pls : Bukkit.getOnlinePlayers()){
					pls.showPlayer(player);
					player.showPlayer(pls);
				}
				Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("WelcomeOnHumineCraft"), new Runnable(){
		            @Override
					public void run(){
		            	player.setResourcePack(Main.getURL2());
		                System.out.println("Download resourcePack for "+player.getName()+" to this adress: \n"+Main.getURL2());
						player.teleport(new Location(pv.getFinalLocation().getWorld(), pv.getFinalLocation().getX(), 500, pv.getFinalLocation().getZ(), 90, 90));

						player.setGameMode(GameMode.SURVIVAL);
		                player.getLocation().getWorld().strikeLightning(player.getLocation().add(0, 10, 0));
						new Fall(player);
		            }
		        }, 1);
			}
		}
	}

}




package com.aymegike.huminekingdom.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.potion.PotionEffectType;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.ParticleManager;
import com.aymegike.huminekingdom.utils.ZoneManager;
import com.aymegike.huminekingdom.utils.objets.Zone;

import net.md_5.bungee.api.ChatColor;

public class PlaceBlock implements Listener{
	
	static int task;
	static int timer = 20;
	

	/********************************************
	 *      Permissions/Interdictions           *
	 ********************************************/
	
	private static boolean compareZone(Zone zone, Location loc){
		int x = zone.getLocation().getBlockX() - loc.getBlockX();
		int z = zone.getLocation().getBlockZ() - loc.getBlockZ();
		
		if(x < 0 && z < 0){
			
			x = loc.getBlockX() - zone.getLocation().getBlockX();
			z = loc.getBlockZ() - zone.getLocation().getBlockZ();
		}
		if(x <= 100 && z <= 100){
			return false;
		}else{
			return true;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void placeDragon(Location loc, Player p){
		
		HumineKingdom.updateDate();
		ParticleManager.downParticle();
		for(Player pls : Bukkit.getOnlinePlayers()){
			pls.playSound(loc, Sound.ENTITY_ENDERMEN_TELEPORT, 5, 5);
			pls.playSound(loc, Sound.BLOCK_PORTAL_AMBIENT, 5, 5);
		}
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		for(int i = x-1 ; i<= x+1 ; i++){
			for(int i2 = z-1 ; i2<=z+1 ; i2++){
				new Location(p.getWorld(), i, loc.getBlockY(), i2).getBlock().setType(Material.AIR);
			}
		}
		
		new Location(p.getWorld(), loc.getBlockX()+1, loc.getBlockY()-1, loc.getBlockZ()).getBlock().setType(Material.BEDROCK);
		new Location(p.getWorld(), loc.getBlockX()-1, loc.getBlockY()-1, loc.getBlockZ()).getBlock().setType(Material.BEDROCK);
		new Location(p.getWorld(), loc.getBlockX()+1, loc.getBlockY()-1, loc.getBlockZ()+1).getBlock().setType(Material.BEDROCK);
		new Location(p.getWorld(), loc.getBlockX()+1, loc.getBlockY()-1, loc.getBlockZ()-1).getBlock().setType(Material.BEDROCK);
		new Location(p.getWorld(), loc.getBlockX()-1, loc.getBlockY()-1, loc.getBlockZ()+1).getBlock().setType(Material.BEDROCK);
		new Location(p.getWorld(), loc.getBlockX()-1, loc.getBlockY()-1, loc.getBlockZ()-1).getBlock().setType(Material.BEDROCK);
		new Location(p.getWorld(), loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()+1).getBlock().setType(Material.BEDROCK);
		new Location(p.getWorld(), loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()-1).getBlock().setType(Material.BEDROCK);
		new Location(p.getWorld(), loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()).getBlock().setType(Material.PURPUR_BLOCK);
		p.setItemInHand(null);
		for(Player pls : Bukkit.getOnlinePlayers())
			pls.playSound(loc, Sound.ENTITY_IRONGOLEM_HURT, 5, 5);
		ParticleManager.particleSpiral(loc, p.getWorld());
		HumineKingdom.setLocation(loc);
		int i=1;
		while((new Location(p.getWorld(), x, y+i, z).getBlock().getType() == Material.AIR &&
				new Location(p.getWorld(), x+1, y+i, z).getBlock().getType() == Material.AIR &&
				new Location(p.getWorld(), x+1, y+i, z+1).getBlock().getType() == Material.AIR &&
				new Location(p.getWorld(), x+1, y+i, z-1).getBlock().getType() == Material.AIR &&
				new Location(p.getWorld(), x, y+i, z+1).getBlock().getType() == Material.AIR &&
				new Location(p.getWorld(), x, y+i, z-1).getBlock().getType() == Material.AIR &&
				new Location(p.getWorld(), x-1, y+i, z+1).getBlock().getType() == Material.AIR &&
				new Location(p.getWorld(), x-1, y+i, z).getBlock().getType() == Material.AIR &&
				new Location(p.getWorld(), x-1, y+i, z-1).getBlock().getType() == Material.AIR) &&i < 75 && y+i < 100){
			i++;
		}
		final int I = i;
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("HumineKingdom"), new Runnable(){

			@Override
			public void run() {
				timer--;
				if(timer == 15){
					Bukkit.getWorld(p.getWorld().getName()).spawnFallingBlock(new Location(p.getWorld(), x+0.5, y+I, z+0.5), Material.GLOWSTONE, (byte) 0);
				}
				if(timer == 14){
					Bukkit.getWorld(p.getWorld().getName()).spawnFallingBlock(new Location(p.getWorld(), x+1.5, y+I, z+0.5), Material.QUARTZ_STAIRS, (byte) 1);
				}
				if(timer == 13){
					Bukkit.getWorld(p.getWorld().getName()).spawnFallingBlock(new Location(p.getWorld(), x+0.5, y+I, z-0.5), Material.QUARTZ_STAIRS, (byte) 2);
				}
				if(timer == 12){
					Bukkit.getWorld(p.getWorld().getName()).spawnFallingBlock(new Location(p.getWorld(), x-0.5, y+I, z+0.5), Material.QUARTZ_STAIRS, (byte) 0);
				}
				if(timer == 11){
					Bukkit.getWorld(p.getWorld().getName()).spawnFallingBlock(new Location(p.getWorld(), x+0.5, y+I, z+1.5), Material.QUARTZ_STAIRS, (byte) 3);

				}
				if(timer == 0){
					Bukkit.getScheduler().cancelTask(task);
					new Location(p.getWorld(), loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()).getBlock().setType(Material.BEDROCK);
					timer = 20;
					ParticleManager.loadParticle(loc);
				}
				for(Player pls : Bukkit.getOnlinePlayers())
					pls.playSound(loc, Sound.BLOCK_PORTAL_AMBIENT, 5, 5);
				if(new Location(p.getWorld(), loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()).getBlock().getType() == Material.QUARTZ_BLOCK){
					new Location(p.getWorld(), loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()).getBlock().setType(Material.PURPUR_BLOCK);
				}else if(new Location(p.getWorld(), loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()).getBlock().getType() == Material.PURPUR_BLOCK){
					new Location(p.getWorld(), loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()).getBlock().setType(Material.QUARTZ_BLOCK);
				}
				
				
			}
			
		},5, 5);

		
	}
	
	
	@EventHandler
	public void onPlayerPlaceBlock(BlockPlaceEvent e){

		
		Player p = e.getPlayer();
		Block b = e.getBlock();
		if(b.getType().equals(Material.PISTON_BASE) || b.getType().equals(Material.PISTON_STICKY_BASE)){
			if(new Location(b.getWorld(), b.getLocation().getBlockX()+1, b.getLocation().getBlockY(), b.getLocation().getBlockZ()).getBlock().getType() == Material.DRAGON_EGG
				|| new Location(b.getWorld(), b.getLocation().getBlockX()-1, b.getLocation().getBlockY(), b.getLocation().getBlockZ()).getBlock().getType() == Material.DRAGON_EGG
				|| new Location(b.getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY(), b.getLocation().getBlockZ()+1).getBlock().getType() == Material.DRAGON_EGG
				|| new Location(b.getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY(), b.getLocation().getBlockZ()-1).getBlock().getType() == Material.DRAGON_EGG){
				
				e.setCancelled(true);
			}
		}
		
		if(b.getType().equals(Material.BEACON)){
			boolean canBuild = true;
			if(HumineKingdom.getPlayerkingdom(p) != null){
				for(Zone zone : ZoneManager.getAllZones()){
					if(!compareZone(zone, b.getLocation()) && HumineKingdom.getPlayerkingdom(p) != zone.getKingdom()){
						p.sendMessage(ChatColor.DARK_PURPLE+"Cette zone est trop proche d'un champ de force de "+ChatColor.BLUE+zone.getKingdom().getName()+ChatColor.DARK_PURPLE+" !");
						canBuild = false;
					}
				}
				if(canBuild == true){
					Zone z = new Zone(HumineKingdom.getPlayerkingdom(p), b.getLocation(), true);
					ZoneManager.addZone(z);
					p.sendMessage(ChatColor.YELLOW+"Félicitation ! Vous venez de créer un champ de force contre vos ennemis.");
					p.sendMessage(ChatColor.DARK_PURPLE+"Pour l'activer il faudra rajouter l'effet "+ChatColor.BLUE+"RESISTANCE"+ChatColor.DARK_PURPLE+" !");
					p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 5, 5);
				}else{
					e.setCancelled(true);
					return;
				}
//				boolean canBuild = true;
//				for(Zone zone : ZoneManager.getAllZones()){
//					if(zone.playerIsInZone(p) && (!zone.playerCanBuild(p) && p.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE))){
//						canBuild = false;
//						p.sendMessage(ChatColor.DARK_PURPLE+"Cette zone est protégée par un champ de force de "+ChatColor.BLUE+zone.getKingdom().getName()+ChatColor.DARK_PURPLE+" !");
//					}
//				}
//				if(canBuild == true){
//					Zone z = new Zone(HumineKingdom.getPlayerkingdom(p), b.getLocation(), true);
//					ZoneManager.addZone(z);
//					p.sendMessage(ChatColor.YELLOW+"Félicitation ! Vous venez de créer un champ de force contre vos ennemis.");
//					p.sendMessage(ChatColor.DARK_PURPLE+"Pour l'activer il faudra rajouter l'effet "+ChatColor.BLUE+"RESISTANCE"+ChatColor.DARK_PURPLE+" !");
//					p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 5, 5);
//				}else{
//					e.setCancelled(true);
//					p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
//				}
			}else{
				for(Zone zone : ZoneManager.getAllZones()){
					if(zone.playerIsInZone(p) && p.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)){
						e.setCancelled(true);
						p.sendMessage(ChatColor.DARK_PURPLE+"Cette zone est protégée par un champ de force de "+ChatColor.BLUE+zone.getKingdom().getName()+ChatColor.DARK_PURPLE+" !");
						p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					}
				}
			}
		}
		
		if(b.getType().equals(Material.DRAGON_EGG)){
			if(HumineKingdom.getPlayerkingdom(p) == null){
				e.setCancelled(true);
				p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 5, 5);
				p.sendMessage(ChatColor.YELLOW+"Mais... Vous avez deja l'oeuf alors que vous n'avez même pas de royaume ! Il faut a tout prix en créer un si vous voulez en profiter.");
				return;
			}
			if(!HumineKingdom.getPlayerkingdom(p).haveDragonEgg()){
				HumineKingdom.getPlayerkingdom(p).setDragonEgg(true);
				HumineKingdom.getPlayerkingdom(p).increaseGlory(null, 500, false);
				Bukkit.broadcastMessage(ChatColor.DARK_PURPLE+"L'oeuf de dragon a trouvé de nouveaux maitres ! Gloire a "+ChatColor.WHITE+HumineKingdom.getPlayerkingdom(p).getName()+ChatColor.DARK_PURPLE+" !");
				for(Player pls : Bukkit.getOnlinePlayers()) pls.playSound(pls.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 5, 5);
			}else{
				for(OfflinePlayer op : HumineKingdom.getPlayerkingdom(p).getAllMember()){
					if(op.isOnline()){
						op.getPlayer().sendMessage(ChatColor.WHITE+p.getName()+ChatColor.DARK_PURPLE+" vien de replacer l'oeuf");
						op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.ENTITY_ENDERDRAGON_HURT, 5, 1);
					}
				}
			}

			e.setCancelled(true);
			placeDragon(b.getLocation(), p);			
			
		}
		
		int y = b.getLocation().getBlockY();
		
		boolean stop = false;
		
		while(stop == false){
			
			y--;
			Location loc = new Location(Bukkit.getWorlds().get(0), b.getLocation().getBlockX(), y, b.getLocation().getBlockZ());
			if(loc.getBlock().getType() != Material.AIR){
				if(loc.getBlock().getType() == Material.BEACON){
					for(Zone zone : ZoneManager.getAllZones()){
						if(zone.playerIsInZone(p)){
							if(!zone.playerCanBuild(p)){
								e.setCancelled(true);
							}
						}
					}
				}
				stop = true;
			}
			
		}
		
		for(Zone zone : ZoneManager.getAllZones()){
			if(zone.playerIsInZone(p)){
				if(!zone.playerCanBuild(p)){
					if((b.getType() != Material.TNT && b.getType() != Material.REDSTONE_TORCH_OFF && b.getType() != Material.REDSTONE_TORCH_ON && b.getType() != Material.TORCH)  && !p.hasPermission("huminekingdom.admin")){
						e.setCancelled(true);
						p.sendMessage(ChatColor.DARK_PURPLE+"Cette zone est protégée par un champ de force de "+ChatColor.BLUE+zone.getKingdom().getName()+ChatColor.DARK_PURPLE+" !");
						p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
						return;
					}else if(b.getType() == Material.TNT){
						if(HumineKingdom.getPlayerkingdom(p) != null){
							if(HumineKingdom.getPlayerkingdom(p).getGlory() < 1){
								e.setCancelled(true);
								p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 5, 5);
								p.sendMessage(ChatColor.YELLOW+"Votre royaume n'a pas assez de gloire pour vous permettre de réaliser cette action.");
								return;
							}else
								HumineKingdom.getPlayerkingdom(p).decreaseGlory(p, 1, false);
						}else{
							e.setCancelled(true);
							p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 5, 5);
							p.sendMessage(ChatColor.YELLOW+"Mais... Vous voulez déjà attaquer alors que vous n'avez même pas de royaume ! Il faut a tout prix en créer un si vous voulez en profiter.");
							return;				
						}
					}
				}
			}
		}

		/********************************************
		 *      Gain/Perte de gloire                *
		 ********************************************/
		
		
		if(b.getType().equals(Material.BEACON)){
			if(HumineKingdom.getPlayerkingdom(p) != null){
				boolean canBuild = true;
				for(Zone zone : ZoneManager.getAllZones()){
					if(zone.playerIsInZone(p) && !zone.playerCanBuild(p) && !p.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE) && ZoneManager.getBeaconsOfKingdom(HumineKingdom.getPlayerkingdom(p)) <= 36){
						canBuild = false;
					}
				}
				if(canBuild == true){
					HumineKingdom.getPlayerkingdom(p).increaseGlory(p, 300, false);
				}
			}
		}
		
		
	}

}

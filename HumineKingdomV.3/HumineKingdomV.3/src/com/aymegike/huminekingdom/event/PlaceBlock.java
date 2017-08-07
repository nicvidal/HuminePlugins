package com.aymegike.huminekingdom.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
	
	int task;
	int timer = 20;
	
	/********************************************
	 *      Permissions/Interdictions           *
	 ********************************************/
	
	
	/**********************************
	 * 
	 * @param e
	 */
	@SuppressWarnings("deprecation")
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
			if(HumineKingdom.getPlayerkingdom(p) != null){
				boolean canBuild = true;
				for(Zone zone : ZoneManager.getAllZones()){
					if(zone.playerIsInZone(p) && !zone.playerCanBuild(p) && !p.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE) && ZoneManager.getBeaconsOfKingdom(HumineKingdom.getPlayerkingdom(p)) <= 36){
						canBuild = false;
					}
				}
				if(canBuild == true){
					Zone z = new Zone(HumineKingdom.getPlayerkingdom(p), b.getLocation(), true);
					ZoneManager.addZone(z);
					p.sendMessage(ChatColor.YELLOW+"Félicitation ! Vous venez de créer un champ de force contre vos ennemis.");
					p.sendMessage(ChatColor.DARK_PURPLE+"Pour l'activer il faudra rajouter l'effet "+ChatColor.BLUE+"RESISTANCE"+ChatColor.DARK_PURPLE+" !");
					p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 5, 5);
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
			HumineKingdom.getPlayerkingdom(p).setDragonEgg(true);
			Bukkit.broadcastMessage(ChatColor.DARK_PURPLE+"L'oeuf de dragon a trouvé de nouveaux maitres ! Gloire a "+ChatColor.WHITE+HumineKingdom.getPlayerkingdom(p).getName()+ChatColor.DARK_PURPLE+" !");
			for(Player pls : Bukkit.getOnlinePlayers()) pls.playSound(pls.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 5, 5);
			ParticleManager.downParticle();
			e.setCancelled(true);
			Location loc = b.getLocation();
			for(Player pls : Bukkit.getOnlinePlayers()){
				pls.playSound(loc, Sound.ENTITY_ENDERMEN_TELEPORT, 5, 5);
				pls.playSound(loc, Sound.BLOCK_PORTAL_AMBIENT, 5, 5);
			}
			int x = loc.getBlockX();
			int y = loc.getBlockY();
			int z = loc.getBlockZ();
			for(int i = x-1 ; i<= x+1 ; i++){
				for(int i2 = z-1 ; i2<=z+1 ; i2++){
					new Location(p.getWorld(), i, b.getLocation().getBlockY(), i2).getBlock().setType(Material.AIR);
				}
			}
			new Location(p.getWorld(), b.getLocation().getBlockX()+1, b.getLocation().getBlockY()-1, b.getLocation().getBlockZ()).getBlock().setType(Material.BEDROCK);
			new Location(p.getWorld(), b.getLocation().getBlockX()-1, b.getLocation().getBlockY()-1, b.getLocation().getBlockZ()).getBlock().setType(Material.BEDROCK);
			new Location(p.getWorld(), b.getLocation().getBlockX()+1, b.getLocation().getBlockY()-1, b.getLocation().getBlockZ()+1).getBlock().setType(Material.BEDROCK);
			new Location(p.getWorld(), b.getLocation().getBlockX()+1, b.getLocation().getBlockY()-1, b.getLocation().getBlockZ()-1).getBlock().setType(Material.BEDROCK);
			new Location(p.getWorld(), b.getLocation().getBlockX()-1, b.getLocation().getBlockY()-1, b.getLocation().getBlockZ()+1).getBlock().setType(Material.BEDROCK);
			new Location(p.getWorld(), b.getLocation().getBlockX()-1, b.getLocation().getBlockY()-1, b.getLocation().getBlockZ()-1).getBlock().setType(Material.BEDROCK);
			new Location(p.getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY()-1, b.getLocation().getBlockZ()+1).getBlock().setType(Material.BEDROCK);
			new Location(p.getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY()-1, b.getLocation().getBlockZ()-1).getBlock().setType(Material.BEDROCK);
			new Location(p.getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY()-1, b.getLocation().getBlockZ()).getBlock().setType(Material.PURPUR_BLOCK);
			p.setItemInHand(null);
			for(Player pls : Bukkit.getOnlinePlayers())
				pls.playSound(loc, Sound.ENTITY_IRONGOLEM_HURT, 5, 5);
			ParticleManager.particleSpiral(loc, p.getWorld());
			HumineKingdom.setLocation(loc);
			int i=1;
			while(new Location(p.getWorld(), x, y+i, z).getBlock().getType() == Material.AIR && i < 75 && y+i < 100){
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
						new Location(p.getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY()-1, b.getLocation().getBlockZ()).getBlock().setType(Material.BEDROCK);
						timer = 20;
						ParticleManager.loadParticle(loc);
					}
					for(Player pls : Bukkit.getOnlinePlayers())
						pls.playSound(loc, Sound.BLOCK_PORTAL_AMBIENT, 5, 5);
					if(new Location(p.getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY()-1, b.getLocation().getBlockZ()).getBlock().getType() == Material.QUARTZ_BLOCK){
						new Location(p.getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY()-1, b.getLocation().getBlockZ()).getBlock().setType(Material.PURPUR_BLOCK);
					}else if(new Location(p.getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY()-1, b.getLocation().getBlockZ()).getBlock().getType() == Material.PURPUR_BLOCK){
						new Location(p.getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY()-1, b.getLocation().getBlockZ()).getBlock().setType(Material.QUARTZ_BLOCK);
					}					
				}				
			},5, 5);			
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
					if(b.getType() != Material.TNT && b.getType() != Material.FIRE && b.getType() != Material.REDSTONE_TORCH_OFF && b.getType() != Material.REDSTONE_TORCH_ON && b.getType() != Material.TORCH){
						e.setCancelled(true);
						p.sendMessage(ChatColor.DARK_PURPLE+"Cette zone est protégée par un champ de force de "+ChatColor.BLUE+zone.getKingdom().getName()+ChatColor.DARK_PURPLE+" !");
						p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					}
				}
			}
		}

		/********************************************
		 *      Gain/Perte de gloire                *
		 ********************************************/
		
		if(b.getType().equals(Material.TNT)){
			if(HumineKingdom.getPlayerkingdom(p) != null){
				boolean isAtHome = false;
				for(Zone zone : ZoneManager.getAllZones()){
					if(zone.playerIsInZone(p) && zone.getKingdom().containPlayer(p)){
						isAtHome = true;
					}
				}
				if (!isAtHome) {
					HumineKingdom.getPlayerkingdom(p).decreaseGlory(5);
				}
			}
		}
		
	}

}

package com.aymegike.huminekingdom.event;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.ZoneManager;
import com.aymegike.huminekingdom.utils.objets.Zone;

public class Explosion implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityExplode(EntityExplodeEvent e){
		
		/********************************************
		 *      Gain/Perte de gloire                *
		 ********************************************/
		
		
		List<Block> blockDestroyed = e.blockList();
		
		Zone zone = null;
		
		if(e.getEntity().isGlowing()){
			for(Block b : blockDestroyed){
				if(b.getType() == Material.BEACON){
					for(Zone z : ZoneManager.getAllZones()){
						if(b.getLocation().getBlockX() == z.getLocation().getBlockX() && b.getLocation().getBlockZ() == z.getLocation().getBlockZ()){
							z.getKingdom().decreaseGlory(null, 300, false);
							zone = z;
							e.setCancelled(true);
							b.setType(Material.AIR);
						}
					}
				}
			}
			e.getEntity().getWorld().createExplosion(e.getLocation(), 100);
			e.getEntity().getWorld().strikeLightning(e.getLocation());
		}
		
		for(Block b : blockDestroyed){
			if(b.getType() == Material.QUARTZ_STAIRS){
				if(new Location(b.getLocation().getWorld(), b.getLocation().getBlockX()-1, b.getLocation().getBlockY()+1, b.getLocation().getBlockZ()).getBlock().getType() == Material.DRAGON_EGG
				|| new Location(b.getLocation().getWorld(), b.getLocation().getBlockX()+1, b.getLocation().getBlockY()+1, b.getLocation().getBlockZ()).getBlock().getType() == Material.DRAGON_EGG
				|| new Location(b.getLocation().getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY()+1, b.getLocation().getBlockZ()+1).getBlock().getType() == Material.DRAGON_EGG
				|| new Location(b.getLocation().getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY()+1, b.getLocation().getBlockZ()-1).getBlock().getType() == Material.DRAGON_EGG){
					
					e.setCancelled(true);
					return;
				}
			}
			else if(b.getType() == Material.GLOWSTONE){
				if(new Location(b.getLocation().getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY()+1, b.getLocation().getBlockZ()).getBlock().getType() == Material.DRAGON_EGG){
					e.setCancelled(true);
					return;
				}
			}
			else if(b.getType() == Material.DRAGON_EGG){
				e.setCancelled(true);
				return;
			}
			else if(b.getType() == Material.BEACON){
				for(Zone z : ZoneManager.getAllZones()){
					if(b.getLocation().getBlockX() == z.getLocation().getBlockX() && b.getLocation().getBlockZ() == z.getLocation().getBlockZ()){
						if(e.getEntityType() == EntityType.PRIMED_TNT){
							TNTPrimed tnt = (TNTPrimed) e.getEntity();
							if(tnt.getSource() instanceof Player){
								Player p = (Player) tnt.getSource();
								if(HumineKingdom.getPlayerkingdom(p) == z.getKingdom()){
									z.getKingdom().decreaseGlory(null, 300, false);
								}else{
									HumineKingdom.getPlayerkingdom(p).increaseGlory(p, 300, false);
									z.getKingdom().decreaseGlory(null, 300, false);
								}
							}
						}else{
							z.getKingdom().decreaseGlory(null, 300, false);
						}
						zone = z;
					}
				}
								
			}
		}
		
		if(e.getEntityType() == EntityType.PRIMED_TNT){
			TNTPrimed tnt = (TNTPrimed) e.getEntity();
			if(tnt.getSource() instanceof Player){
				Player p = (Player) tnt.getSource();
				if(HumineKingdom.getPlayerkingdom(p) != null)
					for(Zone z : ZoneManager.getAllZones()){
						if(z.playerIsInZone(p) && z.getKingdom() != HumineKingdom.getPlayerkingdom(p)){
							HumineKingdom.getPlayerkingdom(p).decreaseGlory(p, 9, false);
						}
					}
				
			}
		}
		
		if(zone != null){
			ZoneManager.removeZone(zone);
		}
		
		
	}

}


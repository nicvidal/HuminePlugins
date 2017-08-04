package com.aymegike.huminekingdom.event;

import java.util.Calendar;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.ParticleManager;
import com.aymegike.huminekingdom.utils.ZoneManager;
import com.aymegike.huminekingdom.utils.objets.Zone;

import net.md_5.bungee.api.ChatColor;

public class BreakBlock implements Listener{
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent e){
		if(e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(HumineKingdom.getPlayerkingdom(e.getPlayer()) == null && e.getClickedBlock().getType() == Material.DRAGON_EGG){
				e.setCancelled(true);
				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 5, 5);
				e.getPlayer().sendMessage(ChatColor.YELLOW+"Si tu veux interagire avec l'oeuf tu dois avoir un royaume !");
				return;
			}
			if(e.getClickedBlock().getType().equals(Material.DRAGON_EGG) && HumineKingdom.GIVED == false){
				e.setCancelled(true);
				Player p = e.getPlayer();
				if(p.getItemOnCursor().getType() == Material.AIR){
					e.getClickedBlock().setType(Material.AIR);
					p.getInventory().setItem(p.getInventory().getHeldItemSlot(), new ItemStack(Material.DRAGON_EGG));
					for(int x = e.getClickedBlock().getLocation().getBlockX()-1 ; x <= e.getClickedBlock().getLocation().getBlockX()+1 ; x++){
						for(int z = e.getClickedBlock().getLocation().getBlockZ()-1 ; z <= e.getClickedBlock().getLocation().getBlockZ()+1 ; z++){
							for(int y = e.getClickedBlock().getLocation().getBlockY()-2 ; y <= e.getClickedBlock().getLocation().getBlockY() ; y++){
								if(y != 0)
									new Location(e.getClickedBlock().getLocation().getWorld(), x, y, z).getBlock().setType(Material.AIR);
							}
						}
					}
					if(HumineKingdom.getEggKingdom() != null)
						HumineKingdom.getEggKingdom().setDragonEgg(false);
					Bukkit.broadcastMessage(ChatColor.DARK_PURPLE+"L'oeuf de dragon a été capturé !");
					ParticleManager.downParticle();
					for(Player pls : Bukkit.getOnlinePlayers()){
						pls.playSound(pls.getLocation(), Sound.ENTITY_WITHER_DEATH, 5, 5);
					}
					HumineKingdom.setLocation(new Location(p.getWorld(), 0, 0, 0));
				}else{
					p.sendMessage(ChatColor.DARK_PURPLE+"[OEUF] -> Les mains vide uniquement, me recupérer vous pourrez !");
				}
			}else if(e.getClickedBlock().getType().equals(Material.DRAGON_EGG) && HumineKingdom.GIVED == true){
				e.setCancelled(true);
				e.getPlayer().sendMessage(ChatColor.DARK_PURPLE+"Vous venez de récuperer la récompense !");
				ParticleManager.downGivedParticle();
				@SuppressWarnings("deprecation")
				int day = Calendar.getInstance().getTime().getDay();
				HumineKingdom.DAY = day;
				ParticleManager.popGived(e.getClickedBlock().getLocation());
			}
		}
	}
	
	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent e){
		
		Player p = e.getPlayer();
		Block b = e.getBlock();
		
		if(b.getType() == Material.QUARTZ_STAIRS){
			if(new Location(b.getLocation().getWorld(), b.getLocation().getBlockX()-1, b.getLocation().getBlockY()+1, b.getLocation().getBlockZ()).getBlock().getType() == Material.DRAGON_EGG
			|| new Location(b.getLocation().getWorld(), b.getLocation().getBlockX()+1, b.getLocation().getBlockY()+1, b.getLocation().getBlockZ()).getBlock().getType() == Material.DRAGON_EGG
			|| new Location(b.getLocation().getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY()+1, b.getLocation().getBlockZ()+1).getBlock().getType() == Material.DRAGON_EGG
			|| new Location(b.getLocation().getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY()+1, b.getLocation().getBlockZ()-1).getBlock().getType() == Material.DRAGON_EGG){
				
				e.setCancelled(true);
			
			}
		}
		if(b.getType() == Material.GLOWSTONE){
			if(new Location(b.getLocation().getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY()+1, b.getLocation().getBlockZ()).getBlock().getType() == Material.DRAGON_EGG){
				e.setCancelled(true);
			}
		}
		boolean active = false;
		boolean remove = false;
		Zone tempo = null;
		for(Zone zone : ZoneManager.getAllZones()){
			if(zone.playerIsInZone(p)){
				if(zone.playerCanBreak(p) == false){
					if(b.getType() != Material.TNT && b.getType() != Material.FIRE && b.getType() != Material.REDSTONE_TORCH_OFF && b.getType() != Material.REDSTONE_TORCH_ON && b.getType() != Material.TORCH){
						active = true;
						e.setCancelled(true);
						p.sendMessage(ChatColor.DARK_PURPLE+"Cette Zone est protégée par un champ de force de "+ChatColor.BLUE+zone.getKingdom().getName()+ChatColor.DARK_PURPLE+" !");
						p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 5);
					}					
				}
			}
			if(b.getType() == Material.BEACON){
				if(active == false){
					if(b.getLocation().getBlockX() == zone.getLocation().getBlockX() && b.getLocation().getBlockZ() == zone.getLocation().getBlockZ()){
						p.sendMessage(ChatColor.BLUE+"Vous venez de supprimer une zone de "+zone.getKingdom().getName()+" !");
						remove = true;
						tempo = zone;
					}
				}
			}
		
		}
		
		if(remove == true){
			ZoneManager.removeZone(tempo);
		}
		
		
		
		
		
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onEntityExplode(EntityExplodeEvent event) {
	
	 
	List<Block> destroyed = event.blockList();
	
	boolean remove = false;
	Zone tempo = null;
	for(Block b : destroyed){
		if(b.getType() == Material.BEACON){
			for(Zone z : ZoneManager.getAllZones()){
				if(z.getLocation().getBlockX() == b.getX() && z.getLocation().getBlockZ() == b.getZ()){
					
					remove = true;
					tempo = z;
					
				}
			}
		}
	}
	
	if(remove == true)
		ZoneManager.removeZone(tempo);
	
	}
}

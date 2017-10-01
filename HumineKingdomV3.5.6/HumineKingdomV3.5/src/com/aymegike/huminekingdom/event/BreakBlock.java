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
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.ZoneManager;
import com.aymegike.huminekingdom.utils.manager.ParticleManager;
import com.aymegike.huminekingdom.utils.objets.Zone;

import net.md_5.bungee.api.ChatColor;

public class BreakBlock implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent e){

		Player p = e.getPlayer();
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(e.getClickedBlock().getType() == Material.BEACON){
				for(Zone zones : ZoneManager.getAllZones()){
					if(zones.playerIsInZone(e.getPlayer()))
						if(!zones.playerCanBuild(e.getPlayer()) && !zones.playerCanBreak(e.getPlayer())){
							e.setCancelled(true);
						}
				}
			}
			if(e.getPlayer().getItemInHand().getType() == Material.LAVA_BUCKET){
				for(Zone zones : ZoneManager.getAllZones()){
					if(zones.playerIsInZone(e.getPlayer()))
						if(!zones.playerCanBuild(e.getPlayer()))
							e.setCancelled(true);
				}
			}
			if(e.getPlayer().getItemInHand().getType() == Material.FLINT_AND_STEEL){
				for(Zone zones : ZoneManager.getAllZones()){
					if(zones.playerIsInZone(e.getPlayer()))
						if(!zones.playerCanBuild(e.getPlayer()))
							if(!(e.getClickedBlock().getType() == Material.TNT))
								e.setCancelled(true);
				}
			}
		}
				
		
		if(e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(HumineKingdom.getPlayerkingdom(e.getPlayer()) == null && e.getClickedBlock().getType() == Material.DRAGON_EGG){
				e.setCancelled(true);
				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 5, 5);
				e.getPlayer().sendMessage(ChatColor.YELLOW+"Si tu veux interagire avec l'oeuf tu dois avoir un royaume !");
				return;
			}
			if(e.getClickedBlock().getType().equals(Material.DRAGON_EGG)){
				e.setCancelled(true);
				if(HumineKingdom.getPlayerkingdom(p).haveDragonEgg() && HumineKingdom.getPlayerGrade(p).getName().equalsIgnoreCase("King")){
					if(HumineKingdom.isNewDay()){
						HumineKingdom.getPlayerkingdom(p).increaseGlory(p, 50, false);
						HumineKingdom.updateDate();
						p.sendMessage(ChatColor.DARK_PURPLE+"Vous venez de comfirmer votre gloire !");
						for(Player pls : Bukkit.getOnlinePlayers()){
							pls.sendMessage(ChatColor.DARK_PURPLE+"Gloire à l'empire "+ChatColor.WHITE+HumineKingdom.getPlayerkingdom(p).getName()+ChatColor.DARK_PURPLE+" qui renouvelle sa puissance une fois encore !");
							pls.playSound(pls.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 5, 1);
						}
						return;
					}
				}
				if(p.getItemInHand().getType() == Material.AIR){
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
					
					if(HumineKingdom.getPlayerkingdom(p).haveDragonEgg()){
						for(OfflinePlayer op : HumineKingdom.getPlayerkingdom(p).getAllMember()){
							if(op.isOnline()){
								op.getPlayer().sendMessage(ChatColor.WHITE+p.getName()+ChatColor.DARK_PURPLE+" vient de prendre l'oeuf");
								op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.ENTITY_ENDERDRAGON_HURT, 5, 1);
							}
						}
					}else if(HumineKingdom.getEggKingdom() != null) {
						HumineKingdom.getEggKingdom().decreaseGlory(null, 500, false);
						HumineKingdom.getEggKingdom().setDragonEgg(false);
						Bukkit.broadcastMessage(ChatColor.DARK_PURPLE+"L'oeuf de dragon a été capturé !");
						for(Player pls : Bukkit.getOnlinePlayers()){
							pls.playSound(pls.getLocation(), Sound.ENTITY_WITHER_DEATH, 5, 5);
						}
					}else{
						Bukkit.broadcastMessage(ChatColor.DARK_PURPLE+"L'oeuf de dragon a été capturé !");
						for(Player pls : Bukkit.getOnlinePlayers()){
							pls.playSound(pls.getLocation(), Sound.ENTITY_WITHER_DEATH, 5, 5);
						}
					}
					ParticleManager.downParticle();
					HumineKingdom.setLocation(new Location(p.getWorld(), 0, 0, 0));
				}else{
					p.sendMessage(ChatColor.DARK_PURPLE+"Vous ne pouvez pas récupérer l'oeuf avec un objet dans les mains.");
				}
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
					if((b.getType() != Material.TNT && b.getType() != Material.FIRE && b.getType() != Material.REDSTONE_TORCH_OFF && b.getType() != Material.REDSTONE_TORCH_ON && b.getType() != Material.TORCH) && !p.hasPermission("huminekingdom.admin")){
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
						if(HumineKingdom.getPlayerkingdom(p) != null)
							if(HumineKingdom.getPlayerkingdom(p) != zone.getKingdom()){
								HumineKingdom.getPlayerkingdom(p).increaseGlory(p, 300, false);
								zone.getKingdom().decreaseGlory(null, 300, false);
							}else
								zone.getKingdom().decreaseGlory(null, 300, false);
						else
							zone.getKingdom().decreaseGlory(null, 300, false);
						p.sendMessage(ChatColor.BLUE+"Vous venez de supprimer une zone de "+zone.getKingdom().getName()+" !");
						remove = true;
						tempo = zone;
						System.out.println("remove "+zone.getLocation().getBlockX()+" "+zone.getLocation().getBlockZ());
					}
				}
			}
		
		}
		
		if(remove == true){
			ZoneManager.removeZone(tempo);
			
		}
		
		
		
		
		
	}

}

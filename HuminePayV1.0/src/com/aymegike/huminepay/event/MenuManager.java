package com.aymegike.huminepay.event;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.aymegike.huminepay.utils.MenuList;
import com.aymegike.huminepay.utils.particles.AngryParticle;
import com.aymegike.huminepay.utils.particles.CloudParticle;
import com.aymegike.huminepay.utils.particles.ColorParticle;
import com.aymegike.huminepay.utils.particles.CritParticle;
import com.aymegike.huminepay.utils.particles.FlameParticle;
import com.aymegike.huminepay.utils.particles.GreenParticle;
import com.aymegike.huminepay.utils.particles.LoveParticle;
import com.aymegike.huminepay.utils.particles.MagicParticle;
import com.aymegike.huminepay.utils.particles.ParticleManager;
import com.aymegike.huminepay.utils.particles.SnowParticle;
import com.aymegike.huminepay.utils.particles.WhitchMagic;

public class MenuManager implements Listener {
	
	@EventHandler
	public void onPlayerUseMenu(InventoryClickEvent e){
		
		if(e.getWhoClicked() instanceof Player){
		
			Player p = (Player) e.getWhoClicked();
			
			if(e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta()){             
			  return;
			}
			
			if(e.getInventory().getTitle().contains(ChatColor.GOLD+"Humine"+ChatColor.DARK_PURPLE+"Club")){
				e.setCancelled(true);
				if(e.getCurrentItem().getType() == Material.BLAZE_POWDER){
					MenuList.mainParticleMenu().openForPlayer(p);
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
				}
			
			}
			
			if(e.getInventory().getTitle().contains(ChatColor.GOLD+"Particules")){
				e.setCancelled(true);
				if(e.getCurrentItem().getType() == Material.BLAZE_POWDER){
					MenuList.followParticleMenu(p).openForPlayer(p);
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
				}
			}
			
			if(e.getInventory().getTitle().contains(ChatColor.GREEN+"particule pisteuse")){
				e.setCancelled(true);
				if(e.getCurrentItem().getType() == Material.APPLE){
					LoveParticle lp = new LoveParticle(p);
					ParticleManager.addParticle(lp);
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
				}
				
				if(e.getCurrentItem().getType() == Material.FIREBALL){
					AngryParticle ap = new AngryParticle(p);
					ParticleManager.addParticle(ap);
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
				}
				
				if(e.getCurrentItem().getType() == Material.BOOK){
					MagicParticle mp = new MagicParticle(p);
					ParticleManager.addParticle(mp);
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
				}
				
				if(e.getCurrentItem().getType() == Material.WEB){
					CloudParticle cp = new CloudParticle(p);
					ParticleManager.addParticle(cp);
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
				}
				
				if(e.getCurrentItem().getType() == Material.EMERALD){
					GreenParticle gp = new GreenParticle(p);
					ParticleManager.addParticle(gp);
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
				}
				
				if(e.getCurrentItem().getType() == Material.FLINT_AND_STEEL){
					FlameParticle fp = new FlameParticle(p);
					ParticleManager.addParticle(fp);
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
				}
				
				if(e.getCurrentItem().getType() == Material.SNOW_BALL){
					SnowParticle sp = new SnowParticle(p);
					ParticleManager.addParticle(sp);
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
				}
				
				if(e.getCurrentItem().getType() == Material.IRON_SWORD){
					CritParticle cp = new CritParticle(p);
					ParticleManager.addParticle(cp);
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
				}
				
				
				//PAY//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if(e.getCurrentItem().getType() == Material.REDSTONE){
					ColorParticle cp = new ColorParticle(p);
					ParticleManager.addParticle(cp);
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
				}
				
				if(e.getCurrentItem().getType() == Material.POTION){
					WhitchMagic wm = new WhitchMagic(p);
					ParticleManager.addParticle(wm);
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
				}
				
				if(e.getCurrentItem().getType() == Material.ENDER_PEARL){
					//EnderParticle ep = new EnderParticle(p);
					//ParticleManager.addParticle(ep);
					p.closeInventory();
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
				}
				
				if(e.getCurrentItem().getType() == Material.BARRIER){
					MenuList.mainParticleMenu().openForPlayer(p);
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
				}
			}
		
		}
	
	}
		

}

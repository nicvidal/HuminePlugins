package com.aymegike.huminekingdom.listener.events;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.models.Kingdom;
import com.aymegike.huminekingdom.utils.models.ShieldGenerator;

public class PlayerClick implements Listener { 
	
	public static ArrayList<OfflinePlayer> getBeacon = new ArrayList<OfflinePlayer>();
	
	public static HashMap<OfflinePlayer, ShieldGenerator> getShieldGenerator = new HashMap<OfflinePlayer, ShieldGenerator>();
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent e) {
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && getBeacon.contains(e.getPlayer())) {
			e.setCancelled(true);
			if (e.getClickedBlock().getType() == Material.BEACON) {
				if (HumineKingdom.getPlayerKingdom(e.getPlayer()).getShieldGenerator(e.getClickedBlock().getLocation()) != null){
					e.getPlayer().sendMessage(ChatColor.GREEN+"Bien maintenant il va falloir lui donner un petit nom !");
					e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 1);
					getShieldGenerator.put(e.getPlayer(), HumineKingdom.getPlayerKingdom(e.getPlayer()).getShieldGenerator(e.getClickedBlock().getLocation()));
					PlayerChatEvent.getNameOfShematic.add(e.getPlayer());
				} else {
					e.getPlayer().sendMessage(ChatColor.RED+"C'est bien une balise mais elle n'est pas reconue en temps que generateur de bouclier...");
					e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
				}
			} else {
				e.getPlayer().sendMessage(ChatColor.RED+"Tu dois selectioner un generateur de bouclier...");
				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_SHULKER_AMBIENT, 5, 1);
			}
			
			getBeacon.remove(e.getPlayer());
		}
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && HumineKingdom.getPlayerKingdom(e.getPlayer()) != null) {
			Kingdom kingdom = HumineKingdom.getPlayerKingdom(e.getPlayer());
			
			if (kingdom.getShieldGenerator(e.getClickedBlock().getLocation()) != null && !kingdom.getShieldGenerator(e.getClickedBlock().getLocation()).isActive() ) {
				
				if (e.getItem().getType() == Material.BEACON) {
					e.setCancelled(true);
					kingdom.regeneShield(kingdom.getShieldGenerator(e.getClickedBlock().getLocation()));
					e.getPlayer().getInventory().remove(new ItemStack(Material.BEACON));
				}
				
			}
	
		}
	}

}

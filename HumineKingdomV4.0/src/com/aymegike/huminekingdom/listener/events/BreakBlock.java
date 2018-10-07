package com.aymegike.huminekingdom.listener.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.models.Kingdom;

public class BreakBlock implements Listener {
	
	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent e) {		
		if (e.getBlock().getType() == Material.BEACON) {
			for (Kingdom kingdom : HumineKingdom.getKingdomManager().getKingdomList()) {
				for (int i = 0 ; i < kingdom.getShieldGenerators().size() ; i++) {
					if (kingdom.getShieldGenerators().get(i).getLocation().getWorld() == e.getBlock().getLocation().getWorld() && kingdom.getShieldGenerators().get(i).getLocation().getBlockX() == e.getBlock().getLocation().getBlockX()
					&& kingdom.getShieldGenerators().get(i).getLocation().getBlockY() == e.getBlock().getLocation().getBlockY() && kingdom.getShieldGenerators().get(i).getLocation().getBlockZ() == e.getBlock().getLocation().getBlockZ()) {
						kingdom.removeShield(kingdom.getShieldGenerators().get(i));
						i--;
						for (OfflinePlayer op : kingdom.getMembers()) {
							
							if (op.isOnline()) {
								op.getPlayer().sendMessage(ChatColor.RED+"Un bouclié a été détruit...");
								op.getPlayer().playSound(op.getPlayer().getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 5, 1);
							}
							
						}
					}
				}
			}
		}
		
	}

}

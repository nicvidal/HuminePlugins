package com.aymegike.huminekingdom.listener.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.models.Kingdom;

public class ExplosionBlock implements Listener {
	
	@EventHandler
	public void onBlockExplos(EntityExplodeEvent  e) {
		for (Block bloc : e.blockList()) {
			if (bloc.getType() == Material.BEACON) {
				for (Kingdom kingdom : HumineKingdom.getKingdomManager().getKingdomList()) {
					for (int i = 0 ; i < kingdom.getShieldGenerators().size() ; i++) {
						if (kingdom.getShieldGenerators().get(i).getLocation().getWorld() == bloc.getLocation().getWorld() && kingdom.getShieldGenerators().get(i).getLocation().getBlockX() == bloc.getLocation().getBlockX()
						&& kingdom.getShieldGenerators().get(i).getLocation().getBlockY() == bloc.getLocation().getBlockY() && kingdom.getShieldGenerators().get(i).getLocation().getBlockZ() == bloc.getLocation().getBlockZ()) {
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

}

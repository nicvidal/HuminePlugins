package com.humine.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.humine.main.BattleMain;

import net.md_5.bungee.api.ChatColor;

public class ArmorDeadEvent implements Listener{

	@EventHandler
	public void onDead(EntityDeathEvent event) {
		final Entity entity = event.getEntity();
		
		if(entity instanceof ArmorStand) {
			for(com.humine.util.ArmorStand as : BattleMain.getInstance().getArmors()) {
				if(as.getName().equals(entity.getCustomName())) {
					for(Player player : Bukkit.getOnlinePlayers()) {
						BattleMain.sendMessage(player, ChatColor.GREEN + entity.getCustomName() + ChatColor.RESET + " was slain by " + ChatColor.RED + event.getEntity().getKiller());
					}
					break;
				}
			}
		}
	}
}

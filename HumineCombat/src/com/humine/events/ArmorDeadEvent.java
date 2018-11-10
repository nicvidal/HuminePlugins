package com.humine.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.humine.main.BattleMain;

public class ArmorDeadEvent implements Listener {

	@EventHandler
	public void onDead(EntityDeathEvent event) {
		final Entity entity = event.getEntity();
		final Entity killer = event.getEntity().getKiller();

		if (entity instanceof ArmorStand && killer instanceof Player) {

			com.humine.util.ArmorStand armor = getArmorStand((Player) killer);

			if (armor != null) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					BattleMain.sendMessage(player, ChatColor.GREEN + armor.getCustomName() + ChatColor.RESET + " was slain by " + ChatColor.RED + killer.getName());
				}
			}

		}
	}

	private com.humine.util.ArmorStand getArmorStand(Player player) {
		com.humine.util.ArmorStand playerArmor = null;

		int i = 0;
		boolean find = false;

		while (i < BattleMain.getInstance().getArmors().size() && find == false) {
			if (BattleMain.getInstance().getArmors().get(i).getCustomName().equals(player.getName())) {
				find = true;
				playerArmor = BattleMain.getInstance().getArmors().get(i);
			}
		}

		return playerArmor;
	}
}

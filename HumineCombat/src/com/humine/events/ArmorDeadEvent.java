package com.humine.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.humine.main.BattleMain;

public class ArmorDeadEvent implements Listener {

	@EventHandler
	public void onDead(EntityDamageByEntityEvent event) {
		Entity victim = event.getEntity();
		Entity damager = event.getDamager();
		com.humine.util.ArmorStand armorStandUtil;
		ArmorStand armorStandVictim = null;

		if (victim instanceof ArmorStand) {
			armorStandVictim = (ArmorStand) victim;
			armorStandUtil = getArmorStandUtil(armorStandVictim);

			if (armorStandUtil != null) {
				armorStandUtil.getArmorStand().setHealth((armorStandUtil.getArmorStand().getHealth() - 5.0));

				if (armorStandUtil.getArmorStand().getHealth() <= 0.0) {
					armorStandUtil.getArmorStand().remove();
					
					if (damager instanceof Player) {
						for (Player p : Bukkit.getOnlinePlayers())
							BattleMain.sendMessage(p, armorStandUtil.getCustomName() + " was slain by " + damager.getName() + " (Disconnected)");
					} else {
						for (Player p : Bukkit.getOnlinePlayers())
							BattleMain.sendMessage(p, armorStandUtil.getCustomName() + " was killed");
					}

					dropArmor(armorStandUtil.getArmorStand(), armorStandUtil.getInventory());
				}

			}

		}
	}

	private void dropArmor(ArmorStand armor, ItemStack[] inventory) {
		if (inventory != null) {
			for (int i = 0; i < inventory.length; i++) {
				if (inventory[i] != null) {
					if (inventory[i].getType() != Material.AIR) {
						armor.getLocation().getWorld().dropItem(armor.getLocation(), inventory[i]);
					}
				}
			}
		}
	}

	private com.humine.util.ArmorStand getArmorStandUtil(ArmorStand armorStand) {
		boolean find = false;
		int i = 0;
		com.humine.util.ArmorStand armor = null;

		while (i < BattleMain.getInstance().getArmors().size() && find == false) {
			if (BattleMain.getInstance().getArmors().get(i).getArmorStand().equals(armorStand)) {
				find = true;
				armor = BattleMain.getInstance().getArmors().get(i);
			}
			i++;
		}

		return armor;
	}
}

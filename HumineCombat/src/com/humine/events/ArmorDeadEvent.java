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
		Player killer = null;
		
		if(victim instanceof ArmorStand) { // si la cible est une armorStand
			if(damager instanceof Player) { // si la personne qui met des dégats est un joueur
				
				//initiation des variables
				killer = (Player) damager;
				armorStandVictim = (ArmorStand) victim;
				armorStandUtil = getArmorStandUtil(armorStandVictim);
				//fin
				
				if(armorStandUtil != null) { // si l'armorStand victim est une bien une armorStand d'un joueur
					
					armorStandUtil.getArmorStand().setHealth(0.0);
					
					for (Player p : Bukkit.getOnlinePlayers())
						BattleMain.sendMessage(p, armorStandUtil.getCustomName() + " was slain by " + killer.getName() + " (Disconnected)");
					
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
			if (BattleMain.getInstance().getArmors().get(i).getArmorStand().getCustomName().equals(armorStand.getCustomName())) {
				find = true;
				armor = BattleMain.getInstance().getArmors().get(i);
			}
			i++;
		}

		return armor;
	}
}

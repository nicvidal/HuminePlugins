package com.humine.events;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.humine.main.BattleMain;
import com.humine.util.ArmorStand;

public class BloodParticleEvent implements Listener {

	@EventHandler
	public void onTouch(EntityDamageEvent event) {
		Entity entity = event.getEntity();

		if (entity instanceof org.bukkit.entity.ArmorStand) {
			for (ArmorStand as : BattleMain.getInstance().getArmors()) {
				if (as.getCustomName().equalsIgnoreCase(entity.getCustomName())) {
					entity.getWorld().spawnParticle(Particle.REDSTONE, entity.getLocation(), 0);
					break;
				}
			}
		}

	}
}

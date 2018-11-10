package com.humine.util;

import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;

import com.humine.main.BattleMain;

public class ArmorStand {

	private String name;
	private EulerAngle angle;
	private org.bukkit.entity.ArmorStand armorStand;
	
	public ArmorStand(final Player player) {
		this.armorStand = player.getWorld().spawn(player.getLocation(), org.bukkit.entity.ArmorStand.class);
		this.angle = new EulerAngle(0.0, 90.0, 0.0);
		this.name = player.getName();
		
		this.armorStand.setBasePlate(false);
		this.armorStand.setSmall(false);
		this.armorStand.setArms(true);
		this.armorStand.setCollidable(true);
		this.armorStand.setCustomName(this.name);
		this.armorStand.setCustomNameVisible(true);
		this.armorStand.setBodyPose(this.angle);
		this.armorStand.setVisible(true);
		this.armorStand.setGravity(true);
		this.armorStand.setInvulnerable(false);
		this.armorStand.setHealth(20.0);
		
		if(player.getInventory().getHelmet() != null)
			this.armorStand.setHelmet(player.getInventory().getHelmet());
		if(player.getInventory().getChestplate() != null)
			this.armorStand.setChestplate(player.getInventory().getChestplate());
		if(player.getInventory().getLeggings() != null)
			this.armorStand.setLeggings(player.getInventory().getLeggings());
		if(player.getInventory().getBoots() != null)
			this.armorStand.setBoots(player.getInventory().getBoots());
		if(player.getInventory().getItemInMainHand() != null)
			this.armorStand.setItemInHand(player.getInventory().getItemInMainHand());
		
		BattleMain.getInstance().getArmors().add(this);
	}
	
	public void kill() {
		this.armorStand.remove();
		BattleMain.getInstance().getArmors().remove(this);
	}

	public String getCustomName() {
		return name;
	}

	public void setCustomName(String name) {
		this.name = name;
	}

	public org.bukkit.entity.ArmorStand getArmorStand() {
		return armorStand;
	}
}

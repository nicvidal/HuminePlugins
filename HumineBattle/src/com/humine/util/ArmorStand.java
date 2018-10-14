package com.humine.util;

import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;

import com.humine.main.BattleMain;

public class ArmorStand {

	private String name;
	private EulerAngle angle;
	private org.bukkit.entity.ArmorStand armorStand;
	
	public ArmorStand(Player player) {
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
		
		this.armorStand.setHelmet(player.getInventory().getHelmet());
		this.armorStand.setChestplate(player.getInventory().getChestplate());
		this.armorStand.setLeggings(player.getInventory().getLeggings());
		this.armorStand.setBoots(player.getInventory().getBoots());
		
		BattleMain.getInstance().getArmors().add(this);
	}
	
	public boolean exist() {
		if(this.armorStand.isDead())
			return true;
		else
			return false;
	}
	
	public void kill() {
		this.armorStand.remove();
		BattleMain.getInstance().getArmors().remove(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public org.bukkit.entity.ArmorStand getArmorStand() {
		return armorStand;
	}
}

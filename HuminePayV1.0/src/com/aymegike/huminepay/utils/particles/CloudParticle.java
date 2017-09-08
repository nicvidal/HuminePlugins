package com.aymegike.huminepay.utils.particles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;

public class CloudParticle extends Particles{

	public CloudParticle(Player player) {
		super(player);
	}

	@Override
	public void animation() {
		Location loc = super.getPlayer().getLocation();
		
		PacketPlayOutWorldParticles particl = new PacketPlayOutWorldParticles(EnumParticle.CLOUD, false,
				((float) (loc.getX())), 
				((float) (loc.getY()+1)), 
				((float) (loc.getZ())),
				0, 0, 0, 1, 0, null);
		for(Player pls : Bukkit.getOnlinePlayers()){
			((CraftPlayer)pls).getHandle().playerConnection.sendPacket(particl);
		}
		
	}

}

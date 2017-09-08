package com.aymegike.huminepay.utils.particles;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;

public class ColorParticle extends Particles{

	public ColorParticle(Player player) {
		super(player);
	}

	@Override
	protected void animation() {
		Location loc = super.getPlayer().getLocation();
		
		Random r = new Random();
		
		float red = r.nextFloat();
		float green = r.nextFloat();
		float blue = r.nextFloat();
		
		@SuppressWarnings("rawtypes")
		Packet example = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, 
				(float) loc.getX(), 
				(float) loc.getY()+1, 
				(float) loc.getZ(), 
				 red, green, blue, 
				 1, 1);
		
		for(Player online : Bukkit.getOnlinePlayers()) {
			((CraftPlayer)online).getHandle().playerConnection.sendPacket(example);
		}
	}

}

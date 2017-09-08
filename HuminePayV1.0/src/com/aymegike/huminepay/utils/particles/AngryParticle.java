package com.aymegike.huminepay.utils.particles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;

public class AngryParticle extends Particles{
	
	private int i = 0;
	
	public AngryParticle(Player player) {
		super(player);
	}

	@Override
	public void animation() {
		i++;
		if(i == 5){
			i = 0;
			Location loc = super.getPlayer().getLocation();
			
			PacketPlayOutWorldParticles particl = new PacketPlayOutWorldParticles(EnumParticle.VILLAGER_ANGRY, false,
					((float) (loc.getX())), 
					((float) (loc.getY()+2)), 
					((float) (loc.getZ())),
					0, 0, 0, 1, 5, null);
			for(Player pls : Bukkit.getOnlinePlayers()){
				((CraftPlayer)pls).getHandle().playerConnection.sendPacket(particl);
			}
		}
		
	}

}

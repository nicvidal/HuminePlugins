package com.aymegike.huminepay.utils;

import org.bukkit.scheduler.BukkitRunnable;

import com.aymegike.huminepay.HuminePay;
import com.aymegike.huminepay.utils.particles.ParticleManager;
import com.aymegike.huminepay.utils.particles.Particles;

public class PlayerUpdater {
	
	public static void Updater(HuminePay huminePay){
		new BukkitRunnable() {
			
			@Override
			public void run() {

				Particles toRemove = null;
				for(Particles particle : ParticleManager.getParticles()){
					if(!particle.generate()){
						toRemove = particle;
					}
				}
				ParticleManager.removeParticle(toRemove);
			}
			
		}.runTaskTimer(huminePay, 0, 1);
	}

}

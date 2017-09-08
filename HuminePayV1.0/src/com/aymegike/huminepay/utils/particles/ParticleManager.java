package com.aymegike.huminepay.utils.particles;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class ParticleManager {
	
	private static ArrayList<Particles> particle = new ArrayList<>();
	
	public static void addParticle(Particles par){
		Player player = par.getPlayer();
		Particles toRemove = null;
		for(Particles pars : particle){
			if(player == pars.getPlayer()){
				toRemove = pars;
			}
		}
		particle.remove(toRemove);
		particle.add(par);
	}
	
	public static void removeParticle(Particles par){
		particle.remove(par);
	}
	
	public static ArrayList<Particles> getParticles(){
		return particle;
	}
	
	

}

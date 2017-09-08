package com.aymegike.huminepay.utils.particles;

import org.bukkit.entity.Player;

public abstract class Particles {
	
	private Player player;
	
	public Particles(Player player){
		this.player = player;
	}
	
	public Player getPlayer(){
		return this.player;
	}
	
	protected abstract void animation();
	
	public boolean generate(){
		if(this.player.isOnline()){
			animation();
			return true;
		}else{
			return false;
		}
	}

}

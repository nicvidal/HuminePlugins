package com.aymegike.bvn.utils.objets;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.aymegike.bvn.Main;
import com.aymegike.bvn.utils.PlayerJoinManager;

public class PositionSpawn {
	
	int task;
	
	Player player;
	boolean stop = false;
	boolean complet;
	
	public PositionSpawn(Player player){
		
		this.player = player;
		
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("WelcomeOnHumineCraft"), new Runnable(){
			
			@Override
			public void run() {
				player.teleport(Main.getSpawn());
				if(stop == true){
					Bukkit.getScheduler().cancelTask(task);
					if(complet == false){
						PlayerJoinManager.stap1.remove(player);
						PlayerJoinManager.stap2.remove(player);
						PlayerJoinManager.stap3.remove(player);
						PlayerJoinManager.stap4.add(player);
						Preview pv = new Preview(player);
						PlayerJoinManager.pvs.add(pv);
						pv.passView();
					}
				}
			}
			
		}, 2, 2);	
		
	}
	
	
	public Player getPlayer(){
		return player;
	}
	
	public void stop(boolean complet){
		this.complet = complet;
		stop = true;
	}

}

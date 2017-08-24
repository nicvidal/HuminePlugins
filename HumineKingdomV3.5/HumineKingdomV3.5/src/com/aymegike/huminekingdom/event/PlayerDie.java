package com.aymegike.huminekingdom.event;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.aymegike.huminekingdom.HumineKingdom;

public class PlayerDie implements Listener {
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e){
		
		/********************************************
		 *      Gain/Perte de gloire                *
		 ********************************************/
		Player p = e.getEntity();
		Player killer = e.getEntity().getKiller();
		
		if(!(killer instanceof Player)){
			if(HumineKingdom.getPlayerkingdom(p) != null){
				HumineKingdom.getPlayerkingdom(p).decreaseGlory(p, 25, true);
				HumineKingdom.setPlayerGlory(p, HumineKingdom.getPlayerGlory(p) - 25);
			}else
				HumineKingdom.setPlayerGlory(p, HumineKingdom.getPlayerGlory(p) - 25);
		}else{
			
			if(HumineKingdom.getPlayerkingdom(p) != null){
				
				HumineKingdom.getPlayerkingdom(p).decreaseGlory(p, 35, true);
				
			}else{
				
				HumineKingdom.setPlayerGlory(p, HumineKingdom.getPlayerGlory(p) - 35);
					
			}
			
			if(HumineKingdom.getPlayerkingdom(killer) != null){
				
				HumineKingdom.getPlayerkingdom(killer).increaseGlory(killer, 35, true);
				
			}else{
				
				HumineKingdom.setPlayerGlory(killer, HumineKingdom.getPlayerGlory(killer)+35);
				
			}
			
		}
		
//		if (!(killer instanceof Player)) { //if it's PVE
//			
//		}else if(HumineKingdom.getPlayerkingdom(p) != null && HumineKingdom.getPlayerkingdom(killer) != null){
//			
//			if (HumineKingdom.getPlayerkingdom(killer).getName().equalsIgnoreCase(HumineKingdom.getPlayerkingdom(p).getName())) {
//				HumineKingdom.getPlayerkingdom(p).decreaseGlory(p, 35, true);		
//			}
//			else {
//				HumineKingdom.getPlayerkingdom(p).decreaseGlory(p, 35, true);	
//				HumineKingdom.getPlayerkingdom(killer).increaseGlory(killer, 35, true);
//			}
//		}else if(HumineKingdom.getPlayerkingdom(killer) != null && HumineKingdom.getPlayerkingdom(p) == null){
//			
//			HumineKingdom.setPlayerGlory(p, HumineKingdom.getPlayerGlory(p) - 35);
//			HumineKingdom.getPlayerkingdom(killer).increaseGlory(p, 35, true);
//			
//		}else if(HumineKingdom.getPlayerkingdom(killer) == null && HumineKingdom.getPlayerkingdom(p) != null){
//			
//			HumineKingdom.setPlayerGlory(killer, HumineKingdom.getPlayerGlory(killer) - 35);
//			HumineKingdom.getPlayerkingdom(p).increaseGlory(p, 35, true);
//			
//		}
	}
}

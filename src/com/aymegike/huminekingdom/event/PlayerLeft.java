package com.aymegike.huminekingdom.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.aymegike.huminekingdom.utils.GradeManager;

public class PlayerLeft implements Listener {
	
	@EventHandler
	public void onPlayerLeft(PlayerQuitEvent e){
		
		Player p = e.getPlayer();
		GradeManager.removePlayer(p);		
		
	}

}

package com.aypi.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.aypi.Aypi;
import com.aypi.utils.Zone;

public class PlayerMove implements Listener {
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		
		Player player = e.getPlayer();
		Location loc = player.getLocation();
		for (Zone zone : Aypi.getZoneManager().getZones()) {
			if (zone.containLocation(new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()))) {
				zone.getZoneListener().onPlayerEnter(player, e);
			}
		}
		
	}

}

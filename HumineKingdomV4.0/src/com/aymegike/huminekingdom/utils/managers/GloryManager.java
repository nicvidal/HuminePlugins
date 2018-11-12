package com.aymegike.huminekingdom.utils.managers;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerEvent;

import com.aymegike.huminekingdom.utils.models.GloryEvent;

public class GloryManager implements Listener {
	
	private ArrayList<GloryEvent> ges;
	
	public GloryManager() {
		ges = new ArrayList<GloryEvent>();
	}
	
	private void update(Player player, Event event) {
		for (GloryEvent ge : ges) {
			if (ge.getPlayerEvent().getEventName().equalsIgnoreCase(event.getEventName())) {
				ge.onPlayerBeGlorious(player);
			}
		}
	}
	
	public void addEvent(GloryEvent gloryEvent) {
		ges.add(gloryEvent);
	}
	
	public void removeEvent(GloryEvent gloryEvent) {
		ges.remove(gloryEvent);
	}
	
	public ArrayList<GloryEvent> getGloryEvents() {
		return ges;
	}
	
	//implementer une liste d'events susebtible d'être utilisé
	@EventHandler
	public void onPlayerPlaceBlock(BlockPlaceEvent e) {
		update(e.getPlayer(), e);
	}
	
	
	
	

}

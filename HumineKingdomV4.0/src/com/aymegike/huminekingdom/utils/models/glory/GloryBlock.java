package com.aymegike.huminekingdom.utils.models.glory;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerEvent;

import com.aymegike.huminekingdom.utils.models.GloryEvent;

public class GloryBlock extends GloryEvent{

	public GloryBlock(PlayerEvent playerEvent) {
		super(playerEvent);
		
	}

	@Override
	public void onPlayerBeGlorious(Player player) {
		
		BlockPlaceEvent e = (BlockPlaceEvent) getPlayerEvent();
		Block block = e.getBlock();
		if () {
			
		}
		
	}

}

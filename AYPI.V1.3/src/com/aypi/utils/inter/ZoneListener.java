package com.aypi.utils.inter;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public interface ZoneListener {
	
	public abstract void onPlayerEnter(Player player, PlayerMoveEvent e);
	public abstract void onPlayerTryToPlaceBlock(Player player, Block block, BlockPlaceEvent e);
	public abstract void onPlayerTryToRemoveBlock(Player player, Block block, BlockBreakEvent e);
	

}

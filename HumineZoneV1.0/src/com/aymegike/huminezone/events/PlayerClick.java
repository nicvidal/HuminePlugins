package com.aymegike.huminezone.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.aymegike.huminezone.utils.PlayerRegister;
import com.aypi.utils.Square;
import com.aypi.utils.Zone;
import com.aypi.utils.inter.ZoneListener;

public class PlayerClick implements Listener {
	
	public class ClicCountDown {
		
		private Player player;
		private int task;
		
		public ClicCountDown(Player player) {
			PlayerClick.cc.add(this);
			this.player = player;
			task = Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("HumineZone"), new Runnable() {
				
				@Override
				public void run() {
					remove();
					Bukkit.getScheduler().cancelTask(task);
				}
				
			}, 20);
		}
		
		private void remove() {
			PlayerClick.cc.remove(this);
		}
		
		public Player getPlayer() {
			return player;
		}
		
	}
	
	private static ArrayList<PlayerRegister> pr = new ArrayList<PlayerRegister>();
	private static ArrayList<ClicCountDown> cc = new ArrayList<ClicCountDown>();
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		boolean firstClick = true;
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			for (ClicCountDown c : cc) {
				
				if (c.getPlayer().getName().equalsIgnoreCase(player.getName())) {
					
					firstClick = false;
					
				}	
			
			}
			
			if (firstClick) {

				new ClicCountDown(player);
				
				Location loc = e.getClickedBlock().getLocation();
				boolean isFirst = true;
				boolean isPass = false;
				
				for (int i = 0 ; i<pr.size() ; i++) {
					if (pr.get(i).getPlayer().getName().equalsIgnoreCase(player.getName())) {
						if (!(pr.get(i).getLocation().getBlockX() == loc.getBlockX() && pr.get(i).getLocation().getBlockY() == loc.getBlockY() && pr.get(i).getLocation().getBlockZ() == loc.getBlockZ())) {
							Square s = new Square(pr.get(i).getLocation(), loc);
							
							new Zone(s, new ZoneListener() {
								
								@Override
								public void onPlayerTryToRemoveBlock(Player arg0) {
									arg0.sendMessage("break block");
								}
								
								@Override
								public void onPlayerTryToPlaceBlock(Player arg0) {
									arg0.sendMessage("place block");
								}
								
								@Override
								public void onPlayerEnter(Player arg0) {
									arg0.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 5);
								}
							});
							
							pr.remove(pr.get(i));
							i--;
							player.sendMessage(ChatColor.GREEN+"second location: "+loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ());
							isPass = true;
						}
						isFirst = false;
					}
				}
				
				if (isFirst && isPass == false) {
					player.sendMessage(ChatColor.GREEN+"first location: "+loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ());
					pr.add(new PlayerRegister(player, loc));
				}
			
			}
			
		}
		

	}
	
	

}

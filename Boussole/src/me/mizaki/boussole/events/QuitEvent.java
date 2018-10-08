package me.mizaki.boussole.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.mizaki.boussole.main.CompassMain;

public class QuitEvent implements Listener
{

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		final Player player = event.getPlayer();
		
		if(CompassMain.getInstance().getSearchDemands().contains(player.getName())) {
			CompassMain.getInstance().getSearchDemands().remove(player.getName());
		}
		
		if(CompassMain.getInstance().getTargetDemands().containsKey(player.getName())) {
			Player target = Bukkit.getPlayer(CompassMain.getInstance().getTargetDemands().get(player.getName()));
			
			if(target != null) {
				CompassMain.sendMessage(target, ChatColor.GOLD + player.getName() + ChatColor.RESET + " s'est deconnecte(e)");
			}
			
			CompassMain.getInstance().getTargetDemands().remove(player.getName());
		}
	}
}

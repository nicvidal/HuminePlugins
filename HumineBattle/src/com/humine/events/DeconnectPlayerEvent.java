package com.humine.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.aypi.utils.Timer;
import com.aypi.utils.inter.TimerFinishListener;
import com.humine.main.BattleMain;
import com.humine.util.ArmorStand;

public class DeconnectPlayerEvent implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		final Player player = event.getPlayer();
		
		
		ArmorStand as = new ArmorStand(player);
		
		Timer timer = new Timer(BattleMain.getInstance(), 120, player.getName(), new TimerFinishListener() {
			
			@Override
			public void execute() {
				as.getArmorStand().remove();
			}
		});
		
		timer.start();
		
	}
}

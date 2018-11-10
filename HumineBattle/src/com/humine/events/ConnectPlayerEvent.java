package com.humine.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.aypi.Aypi;
import com.aypi.utils.Timer;
import com.humine.main.BattleMain;
import com.humine.util.ArmorStand;

public class ConnectPlayerEvent implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();

		for (ArmorStand as : BattleMain.getInstance().getArmors()) {
			if (as.getCustomName().equals(player.getName())) {
				for (Timer timer : Aypi.getTimerManager().getTimers()) {
					if (timer.getName().equals(player.getName())) {
						timer.finish();
						break;
					}
				}
				break;
			}
		}
	}
}

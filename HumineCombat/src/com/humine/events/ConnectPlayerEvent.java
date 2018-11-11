package com.humine.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.aypi.Aypi;
import com.humine.main.BattleMain;

public class ConnectPlayerEvent implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();

		if (hasArmorStand(player)) {

			if (armorStandIsDead(player)) {
				player.setHealth(0.0);
			}

			removeArmorStand(player);
			TimerFinish(player);
		}
	}

	@EventHandler
	public void onDrop(PlayerDeathEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = event.getEntity();
			if (hasArmorStand(player)) {
				removeArmorStandFromList(player);
				event.getDrops().clear();
			}
		}
	}

	// detecte si le joueur avais une armorStand a son effigie sur le jeu
	private boolean hasArmorStand(Player player) {
		boolean valid = false;
		int i = 0;

		while (i < BattleMain.getInstance().getArmors().size() && valid == false) {

			if (BattleMain.getInstance().getArmors().get(i).getCustomName().equals(player.getName()))
				valid = true;

			i++;
		}

		return valid;
	}

	// renvoie si le Timer lié au joueur est terminé ou non
	private void TimerFinish(Player player) {
		boolean finish = false;
		int i = 0;

		while (i < BattleMain.getInstance().getArmors().size() && finish == false) {

			if (Aypi.getTimerManager().getTimers().get(i).getName().equals(player.getName())) {
				if (Aypi.getTimerManager().getTimers().get(i).isStart()) {
					Aypi.getTimerManager().getTimers().get(i).finish();
				}

				Aypi.getTimerManager().getTimers().remove(Aypi.getTimerManager().getTimers().get(i));
				finish = true;
			}

			i++;
		}

	}

	// Verifie si l'armorStand du joueur est mort ou pas dans le jeu
	private boolean armorStandIsDead(Player player) {
		boolean dead = false;
		int i = 0;

		while (i < BattleMain.getInstance().getArmors().size() && dead == false) {

			if (BattleMain.getInstance().getArmors().get(i).getArmorStand().isDead())
				dead = true;

			i++;
		}

		return dead;
	}

	// permet de supprimer l'armorStand du jeu
	private void removeArmorStand(Player player) {
		boolean remove = false;
		int i = 0;

		while (i < BattleMain.getInstance().getArmors().size() && remove == false) {

			if (BattleMain.getInstance().getArmors().get(i).getCustomName().equals(player.getName())) {
				if (!BattleMain.getInstance().getArmors().get(i).getArmorStand().isDead()) {
					remove = true;
					BattleMain.getInstance().getArmors().get(i).getArmorStand().remove();
				}

			}

			i++;
		}

	}

	// permet de supprimer l'armorStand de la liste de BattleMain
	private void removeArmorStandFromList(Player player) {
		boolean remove = false;
		int i = 0;

		while (i < BattleMain.getInstance().getArmors().size() && remove == false) {

			if (BattleMain.getInstance().getArmors().get(i).getCustomName().equals(player.getName())) {
				BattleMain.getInstance().getArmors().remove(BattleMain.getInstance().getArmors().get(i));
			}

			i++;
		}
	}

}

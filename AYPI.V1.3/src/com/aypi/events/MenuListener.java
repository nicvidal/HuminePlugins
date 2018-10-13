package com.aypi.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.aypi.Aypi;
import com.aypi.utils.Button;
import com.aypi.utils.Menu;

public class MenuListener implements Listener {

	/*
	 * Class permettant de gérer automatiquement les Boutons (Class Bouton) d'un
	 * Menu (Class Menu)
	 */

	@EventHandler
	public void onPlayerUseMenu(InventoryClickEvent event) {

		ItemStack item = event.getCurrentItem();

		if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) {
			return;
		}

		Button toPush = null;

		for (Menu menu : Aypi.getMenuManager().getMenus()) {

			if (event.getWhoClicked().getName().equalsIgnoreCase(menu.getPlayer().getName())) {

				if (event.getInventory().getName().equalsIgnoreCase(menu.getTitle())) {
					event.setCancelled(true);
				}

				for (Button button : menu.getButtons()) {
					if (button.getName().equalsIgnoreCase(item.getItemMeta().getDisplayName())) {
						toPush = button;
					}
				}

			}
		}

		if (toPush != null)
			toPush.push();
	}

}

package com.aypi.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.aypi.Aypi;
import com.aypi.utils.Button;
import com.aypi.utils.Menu;

public class MenuListener implements Listener {
	
	@EventHandler
	public void onPlayerUseMenu(InventoryClickEvent e) {
		
		ItemStack item = e.getCurrentItem();
		
		if(e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta()){             
			  return;
		}
		
		Button toPush = null;
				
		for(Menu menu : Aypi.getMenuManager().getMenus()) {
			
			if (e.getWhoClicked().getName().equalsIgnoreCase(menu.getPlayer().getName())) {			
			
				if (e.getInventory().getName().equalsIgnoreCase(menu.getTitle())) {
					e.setCancelled(true);
				}
				
				for (Button button : menu.getButtons()) {
					if(button.getName().equalsIgnoreCase(item.getItemMeta().getDisplayName())) {
						toPush = button;
					}
				}
			
			}
		}
		
		if (toPush != null)
			toPush.push();
	}

}

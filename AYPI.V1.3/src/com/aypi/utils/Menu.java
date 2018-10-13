package com.aypi.utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.aypi.Aypi;

public class Menu {

	private String title;
	private int size;
	private Player player;

	private Inventory inv;

	private ArrayList<Button> buttons;

	private boolean security;

	private int task;

	/*
	 * Class menu permettant de creer une interface graphique et de mieux le gerer
	 */
	public Menu(Player player, String title, int size, boolean security) {
		Aypi.getMenuManager().addMenu(this);

		while (size % 9 != 0) {
			size++;
		}

		this.title = title;
		this.size = size;
		this.player = player;

		this.inv = Bukkit.createInventory(null, size, title);

		buttons = new ArrayList<Button>();
		this.security = security;

	}

	public void setButton(int slot, Button button) {
		buttons.add(button);
		inv.setItem(slot, button.getItem());
	}

	public void setItem(int slot, ItemStack item) {
		inv.setItem(slot, item);
	}

	public void fillLine(ItemStack itemStack, int line) {
		line--;
		for (int i = 0; i < 9; i++) {
			inv.setItem(9 * line + i, itemStack);
		}

	}

	public void openMenu() {

		if (this.security) {
			task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("Aypi"),
					new Runnable() {

						@Override
						public void run() {
							player.openInventory(inv);
						}
					}, 5, 5);
		} else {
			player.openInventory(inv);
		}

	}

	public void closePlayerMenu() {
		Aypi.getMenuManager().removeMenu(this);
		Bukkit.getScheduler().cancelTask(task);
		player.closeInventory();
	}

	public boolean getSecurity() {
		return security;
	}

	public void setSecurity(boolean value) {
		this.security = value;
	}

	public String getTitle() {
		return title;
	}

	public int getSize() {
		return size;
	}

	public Inventory getInventory() {
		return inv;
	}

	public ArrayList<Button> getButtons() {
		return buttons;
	}

	public Player getPlayer() {
		return player;
	}

}

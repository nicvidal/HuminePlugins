package com.aymegike.huminekingdom.utils.objets;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class Menu {
	
	private String title;
	private int size;
	private Kingdom kingdom;
	
	Inventory inv;
		
	public Menu(String title, int size, Kingdom kingdom){
	
		this.title = title;
		this.size = size;
		this.kingdom = kingdom;
		inv = Bukkit.createInventory(null, size, title);
	
	}
	
	public void openForPlayer(Player p){
		p.openInventory(inv);
	}

	public String getTitle(){
		return title;
	}

	public int getSize(){
		return size;
	}

	public Kingdom getKingdom(){
		return kingdom;
	}
	
	public ItemStack getItem(int slot){
		return inv.getItem(slot);
	}
	
	public void addItem(ItemStack is, int slot){
		inv.setItem(slot, is);
	}
	

}

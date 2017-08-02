package com.aymegike.bvn.utils.objets;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Menu {
	
	private String title;
	private int size;
	
	Inventory inv;
		
	public Menu(String title, int size){
	
		this.title = title;
		this.size = size;
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
	
	public ItemStack getItem(int slot){
		return inv.getItem(slot);
	}
	
	public void addItem(ItemStack is, int slot){
		inv.setItem(slot, is);
	}
	

}


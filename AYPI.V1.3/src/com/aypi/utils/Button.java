package com.aypi.utils;

import org.bukkit.inventory.ItemStack;

import com.aypi.utils.inter.MenuItemListener;

public class Button {
	
	private String name;
	private ItemStack itemStack;
	private MenuItemListener listener;
	
	public Button(ItemStack itemStack, MenuItemListener listener) {
		
		this.itemStack = itemStack;
		this.listener = listener;
		
		this.name = itemStack.getItemMeta().getDisplayName();
		
	}
	
	public ItemStack getItem() {
		return itemStack;
	}
	
	public String getName() {
		return name;
	}
	
	public void push() {
		listener.onItemClick();
	}

}

package com.aymegike.huminekingdom.utils.models;

import java.util.ArrayList;

public class PermData {
	
	private String name;
	private String permission;
	private ArrayList<String> lore;
	
	public PermData(String name, String permission, FillList fill) {
		this.name = name;
		this.permission = permission;
		this.lore = fill.fillList();
	}
	
	public String getName() {
		return name;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public ArrayList<String> getLore() {
		return lore;
	}
	
}

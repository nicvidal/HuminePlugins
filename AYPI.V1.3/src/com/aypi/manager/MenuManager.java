package com.aypi.manager;

import java.util.ArrayList;

import com.aypi.utils.Menu;

public class MenuManager {
	
	private ArrayList<Menu> menus;
	
	public MenuManager() {
		menus = new ArrayList<Menu>();
	}

	public void addMenu(Menu menu) {
		menus.add(menu);
	}
	
	public void removeMenu(Menu menu) {
		menus.remove(menu);	
	}
	
	public ArrayList<Menu> getMenus(){
		return menus;
	}

}

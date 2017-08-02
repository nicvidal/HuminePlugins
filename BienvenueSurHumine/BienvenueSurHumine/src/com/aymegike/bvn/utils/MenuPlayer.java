package com.aymegike.bvn.utils;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class MenuPlayer {
	
	private static ArrayList<Player> welcomeMenu = new ArrayList<Player>();
	private static ArrayList<Player> inviteMenu = new ArrayList<Player>();
	
	public static void playerOpenWelcomeMenu(Player player){
		if(!welcomeMenu.contains(player))
			welcomeMenu.add(player);
	}
	
	public static void playerOpenInviteMenu(Player player){
		if(!inviteMenu.contains(player))
			inviteMenu.add(player);
	}
	
	public static void playerCloseWelcomeMenu(Player player){
		if(welcomeMenu.contains(player))
			welcomeMenu.remove(player);
	}
	
	public static void playerCloseInviteMenu(Player player){
		if(inviteMenu.contains(player))
			inviteMenu.remove(player);
	}
	
	
	public static boolean welcomeMenuIsOpen(Player player){
		if(welcomeMenu.contains(player))
			return true;
		
		return false;
	}
	
	public static boolean inviteMenuIsOpen(Player player){
		if(inviteMenu.contains(player))
			return true;
		
		return false;
	}
	

}

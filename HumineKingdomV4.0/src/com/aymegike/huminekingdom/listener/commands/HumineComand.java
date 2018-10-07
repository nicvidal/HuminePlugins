package com.aymegike.huminekingdom.listener.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.MenuList;

public class HumineComand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] arg) {
		
		Player p = (Player) sender;
		if(lbl.equalsIgnoreCase("kingdom")){
			if(HumineKingdom.getPlayerKingdom(p) == null){
				MenuList.noKingdomMenu(p).openMenu();
			}else{
				MenuList.mainKingdomMenu(p).openMenu();
			}
		}
		
		return false;
	}

}

package com.aymegike.huminekingdom.utils.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.MenuList;


public class commandKingdom implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] arg) {
		
		Player p = (Player) sender;
		if(lbl.equalsIgnoreCase("kingdom")){
			if(HumineKingdom.getPlayerkingdom(p) == null){
				MenuList.noKingdomMenu().openForPlayer(p);
			}else{
				MenuList.MainKingdomMenu(HumineKingdom.getPlayerkingdom(p), p).openForPlayer(p);
			}
		}

		if(lbl.equalsIgnoreCase("ranking")){
				MenuList.RankingMenu().openForPlayer(p);
		}
		
		return false;
	}

}

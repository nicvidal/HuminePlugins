package com.aymegike.huminepay.command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aymegike.huminepay.utils.MenuList;

public class HumineClubCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		
		if(sender instanceof Player){
		
			Player p = (Player) sender;
			if(lbl.equalsIgnoreCase("humineclub")){
				MenuList.HumineClubMenu().openForPlayer(p);
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
			}
		}else{
			
		}
		
		return false;
	}

}

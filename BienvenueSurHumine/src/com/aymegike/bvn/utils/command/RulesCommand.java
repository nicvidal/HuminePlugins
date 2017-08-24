package com.aymegike.bvn.utils.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aymegike.bvn.Main;

public class RulesCommand implements CommandExecutor {
	
	Main pl;
	
	public RulesCommand(Main pl) {
		this.pl = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] arg) {
		
		if(sender instanceof Player){
			Player p = (Player) sender;
			
			if(lbl.equalsIgnoreCase("guide")){
				p.getInventory().addItem(Main.getBook());
			}
		}else{
			System.out.println("please don't use this command with the consol !");
		}
		
		
	
		return false;
	}

}

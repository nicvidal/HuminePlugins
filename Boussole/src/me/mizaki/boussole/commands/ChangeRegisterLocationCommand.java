package me.mizaki.boussole.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.mizaki.boussole.main.CompassMain;

public class ChangeRegisterLocationCommand implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			CompassMain.getInstance().getPositions().put(player.getName(), player.getLocation());
			CompassMain.sendMessage(player, ChatColor.GREEN + "Position enregistrée !");
			CompassMain.sendMessage(player, ChatColor.GOLD + player.getLocation().toString());
			return true;
		}
		else
			CompassMain.sendMessage(CompassMain.getInstance().getServer().getConsoleSender(), "vous n'etes pas un joueur");
		
		return false;
	}

}

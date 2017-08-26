package com.aymegike.bvn.utils.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aymegike.bvn.Main;
import com.aymegike.bvn.utils.PlayerJoinManager;

import net.md_5.bungee.api.ChatColor;

public class CommandDownLoadPack implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] arg) {
		
		if(sender instanceof Player){
			Player p = (Player) sender;
			if(lbl.equalsIgnoreCase("downloadpack")){
				if(PlayerJoinManager.stap1.contains(p)){
					Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("WelcomeOnHumineCraft"), new Runnable(){
			            @Override
						public void run(){
			            	p.setResourcePack(Main.getURL());
			                System.out.println("Download resourcePack for "+p.getName()+" to this adress: \n"+Main.getURL());
			            }
			        }, 20);
					
					p.sendMessage(ChatColor.DARK_PURPLE+"Vous pouvez à présent télécharger le ressource pack. "+ChatColor.LIGHT_PURPLE+""+ChatColor.ITALIC+"(si un problème persiste, veuillez vous reconnecter)");
				}
			}
		
		}else{
			System.out.println("Vous ne pouvez pas executer cette commande depuis la console !");
		}
		
		
		return true;
	}

}

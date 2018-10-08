package me.mizaki.boussole.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.mizaki.boussole.main.CompassMain;

public class ReceiptTargetNameEvent implements Listener
{

	@EventHandler
	public void onReceipt(AsyncPlayerChatEvent event) {
		final Player player = event.getPlayer();
		
		if(CompassMain.getInstance().getTargetDemands().containsKey(player.getName())) {
			Player target = Bukkit.getPlayer(CompassMain.getInstance().getTargetDemands().get(player.getName()));
			
			if(target != null) {
				if(event.getMessage().equalsIgnoreCase("yes") || event.getMessage().equalsIgnoreCase("oui")) {
					CompassMain.sendMessage(player, "Vous avez accepte(e) la demande");
					CompassMain.sendMessage(target, ChatColor.GOLD + player.getName() + ChatColor.GREEN + " a accepte(e) votre demande");
					target.setCompassTarget(player.getLocation());
				}
				else if(event.getMessage().equalsIgnoreCase("no") || event.getMessage().equalsIgnoreCase("non")) {
					CompassMain.sendMessage(player, "Vous avez refuse(e) la demande");
					CompassMain.sendMessage(target, ChatColor.GOLD + player.getName() + ChatColor.RED + " a refuse(e) votre demande");
				}
				else {
					CompassMain.sendMessage(player, "Reponse inconnu, nous repondrons par defaut non");
					CompassMain.sendMessage(target, ChatColor.GOLD + player.getName() + ChatColor.RED + " a refuse(e) votre demande");
				}
			}
			else
				CompassMain.sendMessage(player, "Joueur deconnecte");
			
			CompassMain.getInstance().getTargetDemands().remove(player.getName());
			event.setCancelled(true);
		}
		
		
		if(CompassMain.getInstance().getSearchDemands().contains(player.getName())) {
			
			Player target = Bukkit.getPlayer(event.getMessage());
			
			if(target != null) {
				if(!CompassMain.getInstance().getTargetDemands().containsKey(target.getName())) {
					CompassMain.sendMessage(player, "Demande envoyee a " + ChatColor.GREEN + target.getName());
					CompassMain.sendMessage(target, ChatColor.GOLD + player.getName() + ChatColor.RESET + " vous demandes si il peut prendre vos coordonnes");
					CompassMain.sendMessage(target, ChatColor.GREEN + "yes | oui " + ChatColor.RESET + "ou " + ChatColor.RED + "no | non");
					CompassMain.getInstance().getTargetDemands().put(target.getName(), player.getName());
				}
				else
					CompassMain.sendMessage(player, "Une demande est deja en cours vers ce joueur");
			}
			else
				CompassMain.sendMessage(player, ChatColor.RED + "Joueur introuvable !");
			
			CompassMain.getInstance().getSearchDemands().remove(player.getName());
			event.setCancelled(true);
		}
		
		
	}
}

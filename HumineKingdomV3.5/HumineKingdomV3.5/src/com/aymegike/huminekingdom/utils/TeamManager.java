package com.aymegike.huminekingdom.utils;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;

import com.aymegike.huminekingdom.utils.objets.Team;


public class TeamManager {
	
	
	
	public static void createTeams() {
	    // Parcourt de tous les joueurs en ligne
	    Bukkit.getOnlinePlayers().forEach(player -> {
	        // On crÃ©e une team rouge et une team verte contenant leurs noms (pour Ã©viter les soucis)
	        final Team redTeam = new Team("r"+player.getName());
	        final Team greenTeam = new Team("g"+player.getName());

	        // On envoie les teams au joueur actuel
	        greenTeam.create(player);
	        redTeam.create(player);

	        // On dit que le joueur actuel peut voir ces teams
	        redTeam.addViewer(player);
	        greenTeam.addViewer(player);
	        
	        redTeam.setTeamColor(DyeColor.RED);
	        greenTeam.setTeamColor(DyeColor.GREEN);

	        // On ajoute tous les joueurs dans la team rouge sauf lui
	        Bukkit.getOnlinePlayers().stream().filter(pls -> !pls.getName().equalsIgnoreCase(player.getName())).forEach(redTeam::addPlayer);
	        // On l'ajoute dans la team verte
	        greenTeam.addPlayer(player);
	    });
	}	
}



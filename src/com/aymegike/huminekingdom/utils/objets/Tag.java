package com.aymegike.huminekingdom.utils.objets;

import java.util.Collection;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;



public class Tag {	
	
	private String prefix;	
	private String suffix;	
	
	private Team team;	
	private Scoreboard board;	
	
	public Tag(String name, String prefix, String suffix, Scoreboard current) throws Exception {		
		
		this.setPrefix(prefix);		
		this.setSuffix(suffix);		
		this.team = current.registerNewTeam(name);		
		this.board = current;		
		team.setCanSeeFriendlyInvisibles(false);		
		team.setAllowFriendlyFire(true);		
		int prefixLength = team.getPrefix().length();		
		int suffixLength = team.getSuffix().length();		
		
		if (prefix != null)			
			prefixLength = prefix.length();		
		if (suffix != null)			
			suffixLength = suffix.length();		
		if (prefixLength + suffixLength >= 16) {			
			throw new Exception("prefix and suffix lenghts are greater than 16");		
		}		
		if (suffix != null)			
			team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));		
		if (prefix != null)			
			team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));	
		}	
	
	public Tag(String name, String prefix, String suffix) throws Exception {		
		this(name, prefix, suffix, Bukkit.getScoreboardManager().getMainScoreboard());	
	}	
	
	@SuppressWarnings("deprecation")
	public void set(Player player) {		
		this.team.addPlayer(player);		
		player.setScoreboard(board);	
	}	
	
	public void setAll(Collection<Player> players) {		
		for (Player player : players) {			
			set(player);		
		}	
		
	}
	
	public void setAll(Player[] players) {		
		for (Player player : players) {			
			set(player);		
		}	
	}	
	
	public String getPrefix() {		
		return prefix;	
	}	
	
	public void setPrefix(String prefix) {		
		this.prefix = ChatColor.translateAlternateColorCodes('&', prefix);	
	}	
	
	public String getSuffix() {		
		return suffix;	
	}	
	
	public void setSuffix(String suffix) {		
		this.suffix = ChatColor.translateAlternateColorCodes('&', suffix);	
	}	
	
	public static void resetTag(Player player) {		
		if (Bukkit.getScoreboardManager().getMainScoreboard().getTeam(player.getName()) != null)
			Bukkit.getScoreboardManager().getMainScoreboard().getTeam(player.getName()).unregister();	
	}	
	
	public static void resetTag(UUID id) {		
		resetTag(Bukkit.getPlayer(id));	
	}	
	
	public static void resetAll() {		
		if (Bukkit.getOfflinePlayers().length != 0)			
			for (OfflinePlayer player : Bukkit.getOfflinePlayers())				
				Bukkit.getScoreboardManager().getMainScoreboard().getTeam(player.getUniqueId().toString()).unregister();	
		}
	}



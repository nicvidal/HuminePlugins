package com.aymegike.bvn.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.aymegike.bvn.Main;
import com.aymegike.bvn.event.PlayerChat;
import com.aymegike.bvn.utils.objets.PositionSpawn;
import com.aymegike.bvn.utils.objets.Preview;

import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;

public class PlayerJoinManager {
	
	public static ArrayList<Player> stap1 = new ArrayList<>();
	public static ArrayList<Player> stap2 = new ArrayList<>();
	public static ArrayList<Player> stap3 = new ArrayList<>();
	public static ArrayList<Player> stap4 = new ArrayList<>();
	
	public static ArrayList<PositionSpawn> ps = new ArrayList<>();
	
	public static ArrayList<Preview> pvs = new ArrayList<>();
	
	public static void newPlayer(Player p){
		
		p.setGameMode(GameMode.SPECTATOR);
		p.getInventory().setHelmet(null);
		for(Player pls : Bukkit.getOnlinePlayers()){
			pls.hidePlayer(p);
			p.hidePlayer(pls);
		}
		PlayerChat.addPlayerStopChat(p);
		PositionSpawn s = new PositionSpawn(p);
		ps.add(s);
		stap1.add(p);
		PacketPlayOutChat chat = new PacketPlayOutChat(ChatSerializer.a("[\"\",{\"text\":\"Le ressource pack s'est-il bien téléchargé automatiquement ? (Merci de vérifier dans vos ressources packs)\",\"color\":\"yellow\",\"bold\":true,\"underlined\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Il est tr\u00E8s important de télécharger \",\"color\":\"light_purple\"},{\"text\":\"le ressource pack afin de profiter au \",\"color\":\"light_purple\"},{\"text\":\"mieux des premi\u00E8res minutes de jeu.\",\"color\":\"light_purple\"}]}}},{\"text\":\" \\n[\",\"color\":\"gray\",\"bold\":false,\"underlined\":false},{\"text\":\"oui\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 1\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"J'ai bien téléchargé le ressource pack. On peut passer à la suite.\",\"color\":\"light_purple\"}]}}},{\"text\":\"] [\",\"color\":\"gray\"},{\"text\":\"non\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/downloadpack\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Envoyez moi une commande pour télécharger le ressource pack.\",\"color\":\"light_purple\"}]}}},{\"text\":\"]\",\"color\":\"gray\"}]"));
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(chat);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("WelcomeOnHumineCraft"), new Runnable(){
            @Override
			public void run(){
            	p.setResourcePack(Main.getURL());
                System.out.println("Download resourcePack for "+p.getName()+" to this adress: \n"+Main.getURL());
            }
        }, 20);
		
	}

	public static void finalisePlayerConnection(Player player) {
		try(PrintWriter print = new PrintWriter(new FileOutputStream(Main.getList(), true))){
			print.println(player.getUniqueId().toString()+" "+player.getLocation().getBlockX()+" "+player.getLocation().getBlockY()+" "+player.getLocation().getBlockZ());
		}catch(IOException e){
			e.printStackTrace();
		}
		PlayerChat.removePlayerStopChat(player);
		player.setAllowFlight(false);
		player.setFlying(false);
	}

}

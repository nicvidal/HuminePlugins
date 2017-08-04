package com.aymegike.bvn.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.aymegike.bvn.Main;
import com.aymegike.bvn.event.PlayerChat;

import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;

public class PlayerJoinManager {
	
	public static void newPlayer(Player p){
		
		p.setGameMode(GameMode.SPECTATOR);
		PlayerChat.addPlayerStopChat(p);
		p.teleport(Main.getSpawn());
		PacketPlayOutChat chat = new PacketPlayOutChat(ChatSerializer.a("[\"\",{\"text\":\"Avez vous telecharger le resource pack ?\",\"color\":\"yellow\",\"bold\":true,\"underlined\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Il est tr\u00E8s important de telecharger\",\"color\":\"light_purple\"},{\"text\":\"le resource pack afin de profiter au \",\"color\":\"light_purple\"},{\"text\":\"mieux des premi\u00E8re minutes de jeu.\",\"color\":\"light_purple\"}]}}},{\"text\":\" \\n[\",\"color\":\"gray\",\"bold\":false,\"underlined\":false},{\"text\":\"oui\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 1\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"J'ai bien télécharger le resource pack. On peut passer a la suite !\",\"color\":\"light_purple\"}]}}},{\"text\":\"] [\",\"color\":\"gray\"},{\"text\":\"non\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/downloadpack\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Renvoyer moi une dommande pour télécharger le resourcepack\",\"color\":\"light_purple\"}]}}},{\"text\":\"]\",\"color\":\"gray\"}]"));
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(chat);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("WelcomeOnHumineCraft"), new Runnable(){
            @Override
			public void run(){
            	p.setResourcePack(Main.getURL());
                System.out.println("Download resourcePack for "+p.getName()+" to this adress: \n"+Main.getURL());
            }
        }, 20);
		
	}

}

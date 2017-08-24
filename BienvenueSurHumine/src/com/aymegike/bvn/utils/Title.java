package com.aymegike.bvn.utils;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PlayerConnection;

public class Title {
	
	public static int defaultFadeIn = 20;
	  public static int defaultFadeOut = 20;
	  public static int defaultTimeStay = 60;
	  
	  public static void sendTitle(Player player, String title, String subTitle)
	  {
	    sendTitle(player, title, subTitle, defaultFadeIn, defaultFadeOut, defaultTimeStay);
	  }
	  
	  public static void sendTitle(Player p, String title, String subTitle, int fadeIn, int fadeOut, int time)
	  {
	    if (title == null) {
	      title = "";
	    }
	    if (subTitle == null) {
	      subTitle = "";
	    }
	    PlayerConnection Connection = ((CraftPlayer)p).getHandle().playerConnection;
	    
	    IChatBaseComponent titleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
	    IChatBaseComponent subtitleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subTitle + "\"}");
	    
	    PacketPlayOutTitle titletimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, titleComponent, 
	      fadeIn, time, fadeOut);
	    PacketPlayOutTitle subtitletimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, 
	      subtitleComponent, fadeIn, time, fadeOut);
	    
	    PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, 
	      titleComponent);
	    PacketPlayOutTitle subtitelPacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, 
	      subtitleComponent);
	    
	    Connection.sendPacket(titletimes);
	    Connection.sendPacket(subtitletimes);
	    Connection.sendPacket(titlePacket);
	    Connection.sendPacket(subtitelPacket);
	  }

}

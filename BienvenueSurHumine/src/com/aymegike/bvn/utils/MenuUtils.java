package com.aymegike.bvn.utils;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.aymegike.bvn.utils.objets.Menu;

import net.md_5.bungee.api.ChatColor;

public class MenuUtils {
	
	public static Menu openMenuJoin(Player sender, Player target, int chrono){
		
		Menu menu = new Menu(sender.getName()+ChatColor.DARK_PURPLE+" souhaite se joindre a toi !", 3*9);
		
		ArrayList<String> lore = new ArrayList<String>();
		
		ItemStack emerald = new ItemStack(Material.EMERALD);
		ItemMeta emeraldM = emerald.getItemMeta();
		emeraldM.setDisplayName(ChatColor.GREEN+"INFORMATION !");
		lore.add(ChatColor.DARK_PURPLE+"Afin de devenir plus puissant, vous");
		lore.add(ChatColor.DARK_PURPLE+"pouvez créer votre royaume et recruter");
		lore.add(ChatColor.DARK_PURPLE+"des amis pour dominer le monde de "+ChatColor.WHITE+"HumineCraft"+ChatColor.DARK_PURPLE+" !");
		lore.add(ChatColor.YELLOW+"ce menu ce refermera automatiquement dans "+ChatColor.WHITE+(60-chrono)+ChatColor.YELLOW+" secondes");
		emeraldM.setLore(lore);
		emerald.setItemMeta(emeraldM);
		
		lore.clear();
		
		lore.add(ChatColor.DARK_PURPLE+""+ChatColor.ITALIC+"Cliquez pour valider");
		
		ItemStack accept = new ItemStack(Material.EMERALD_BLOCK);
		ItemMeta acceptm = accept.getItemMeta();
		acceptm.setDisplayName(ChatColor.YELLOW+"Accepter"+ChatColor.GREEN+" ✔");
		acceptm.setLore(lore);
		accept.setItemMeta(acceptm);
		
		ItemStack decline = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta declineM = decline.getItemMeta();
		declineM.setDisplayName(ChatColor.YELLOW+"Refuser"+ChatColor.RED+" X");
		declineM.setLore(lore);
		decline.setItemMeta(declineM);
			
		for(int i = 0 ; i<menu.getSize() ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
		
		for(int i = 9 ; i<18 ; i++){
			ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 10);
			ItemMeta glassM = glass.getItemMeta();
			glassM.setDisplayName(" ");
			glass.setItemMeta(glassM);
			menu.addItem(glass, i);
		}
			
		menu.addItem(emerald, 13);
		menu.addItem(accept, 10);
		menu.addItem(decline, 16);
			
			
		return menu;
		
	}
		
}



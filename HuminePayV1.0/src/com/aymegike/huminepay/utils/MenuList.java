package com.aymegike.huminepay.utils;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.aymegike.huminepay.utils.objets.Menu;

import net.md_5.bungee.api.ChatColor;

public class MenuList {
	
	//NO KINGDOM MENU
		public static Menu HumineClubMenu(){
			Menu menu = new Menu(ChatColor.GOLD+"Humine"+ChatColor.DARK_PURPLE+"Club", 3*9);
			
			ArrayList<String> lore = new ArrayList<String>();
			
			ItemStack bp = new ItemStack(Material.BLAZE_POWDER);
			ItemMeta bpM = bp.getItemMeta();
			bpM.setDisplayName(ChatColor.GREEN+"Particules");
			lore.add(ChatColor.DARK_PURPLE+"Pour faire le joli coeur,");
			lore.add(ChatColor.DARK_PURPLE+"ou bien pour faire peurt.");
			bpM.setLore(lore);
			bp.setItemMeta(bpM);
			
			lore.clear();
			
			
			
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
			
			menu.addItem(bp, 13);
			
			
			return menu;
		}
		
		public static Menu mainParticleMenu(){
			Menu menu = new Menu(ChatColor.GOLD+"Particules", 3*9);
			
			
			ArrayList<String> lore = new ArrayList<String>();
			
			ItemStack bp = new ItemStack(Material.BLAZE_POWDER);
			ItemMeta bpM = bp.getItemMeta();
			bpM.setDisplayName(ChatColor.GREEN+"particule pisteuse");
			lore.add(ChatColor.DARK_PURPLE+"Plein de jolies particules");
			lore.add(ChatColor.DARK_PURPLE+"qui vont te suivre partout");
			lore.add(ChatColor.DARK_PURPLE+"où tu vas.");
			bpM.setLore(lore);
			bp.setItemMeta(bpM);
			
			lore.clear();
			
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
			
			menu.addItem(bp, 11);
			
			return menu;
			
		}
		
		public static Menu followParticleMenu(Player player){
			Menu menu = new Menu(ChatColor.GREEN+"particule pisteuse", 5*9);
			
			ArrayList<String> lore = new ArrayList<String>();
			
			ItemStack apple = new ItemStack(Material.APPLE);
			ItemMeta appleM = apple.getItemMeta();
			appleM.setDisplayName(ChatColor.RED+"De l'amour !");
			lore.add(ChatColor.DARK_PURPLE+"Plein de jolies petits");
			lore.add(ChatColor.DARK_PURPLE+"coeurs partout.");
			appleM.setLore(lore);
			apple.setItemMeta(appleM);
			
			lore.clear();
			
			ItemStack fc = new ItemStack(Material.FIREBALL);
			ItemMeta fcM = fc.getItemMeta();
			fcM.setDisplayName(ChatColor.RED+"Pas d'humeur");
			lore.add(ChatColor.DARK_PURPLE+"De quoi faire comprendre");
			lore.add(ChatColor.DARK_PURPLE+"à votre entourage que");
			lore.add(ChatColor.DARK_PURPLE+"n'ete pas comtant");
			fcM.setLore(lore);
			fc.setItemMeta(fcM);
			
			lore.clear();
			
			ItemStack magic = new ItemStack(Material.BOOK);
			ItemMeta magicM = magic.getItemMeta();
			magicM.setDisplayName(ChatColor.AQUA+"Magique");
			lore.add(ChatColor.DARK_PURPLE+"Abrakadabra !");
			magicM.setLore(lore);
			magic.setItemMeta(magicM);
			
			lore.clear();
			
			ItemStack cloud = new ItemStack(Material.WEB);
			ItemMeta cloudM = cloud.getItemMeta();
			cloudM.setDisplayName("nuage");
			lore.add(ChatColor.DARK_PURPLE+"Le temps ce couvre, non ?");
			cloudM.setLore(lore);
			cloud.setItemMeta(cloudM);
			
			lore.clear();
			
			ItemStack ender = new ItemStack(Material.ENDER_PEARL);
			ItemMeta enderM = ender.getItemMeta();
			enderM.setDisplayName(ChatColor.DARK_PURPLE+"Ender");
			lore.add(ChatColor.DARK_PURPLE+"J'ai comme une envie");
			lore.add(ChatColor.DARK_PURPLE+"de me téléporter.");
			enderM.setLore(lore);
			ender.setItemMeta(enderM);
			
			lore.clear();
			
			ItemStack green = new ItemStack(Material.EMERALD);
			ItemMeta greenM = green.getItemMeta();
			greenM.setDisplayName(ChatColor.GREEN+"Vert");
			lore.add(ChatColor.DARK_PURPLE+"En soit c'est une");
			lore.add(ChatColor.DARK_PURPLE+"jolie couleur.");
			greenM.setLore(lore);
			green.setItemMeta(greenM);
			
			lore.clear();
			
			ItemStack fire = new ItemStack(Material.FLINT_AND_STEEL);
			ItemMeta fireM = fire.getItemMeta();
			fireM.setDisplayName(ChatColor.GOLD+"feux");
			lore.add(ChatColor.DARK_PURPLE+"Attention ça brule !");
			fireM.setLore(lore);
			fire.setItemMeta(fireM);
			
			lore.clear();
			
			ItemStack snow = new ItemStack(Material.SNOW_BALL);
			ItemMeta snowM = snow.getItemMeta();
			snowM.setDisplayName(ChatColor.BLUE+"Neige");
			lore.add(ChatColor.DARK_PURPLE+"Attention c'est froid !");
			snowM.setLore(lore);
			snow.setItemMeta(snowM);
			
			lore.clear();
			
			ItemStack crit = new ItemStack(Material.IRON_SWORD);
			ItemMeta critM = crit.getItemMeta();
			critM.setDisplayName(ChatColor.GRAY+"Crit");
			lore.add(ChatColor.DARK_PURPLE+"Attention ça brule");
			critM.setLore(lore);
			crit.setItemMeta(critM);
			
			lore.clear();
			
			ItemStack color = new ItemStack(Material.REDSTONE);
			ItemMeta colorM = crit.getItemMeta();
			colorM.setDisplayName("§aC§bO§cL§dO§eR§1E§2R");
			lore.add(ChatColor.DARK_PURPLE+"Parfait pour les licornes");
			colorM.setLore(lore);
			color.setItemMeta(colorM);
			
			lore.clear();
			
			ItemStack witch = new ItemStack(Material.POTION);
			ItemMeta witchM = witch.getItemMeta();
			witchM.setDisplayName(ChatColor.BLACK+""+ChatColor.MAGIC+"|||"+ChatColor.DARK_PURPLE+" Enchanteresse "+ChatColor.BLACK+""+ChatColor.MAGIC+"|||");
			lore.add(ChatColor.DARK_PURPLE+"On verra plus tard...");
			witchM.setLore(lore);
			witch.setItemMeta(witchM);
			
			lore.clear();
			
			ItemStack bar = new ItemStack(Material.BARRIER);
			ItemMeta barM = bar.getItemMeta();
			barM.setDisplayName(ChatColor.RED+"retour");
			lore.add(ChatColor.DARK_PURPLE+"-------------");
			barM.setLore(lore);
			bar.setItemMeta(barM);
			
			lore.clear();
			
			
			
			for(int i = 0 ; i<menu.getSize() ; i++){
				ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
				ItemMeta glassM = glass.getItemMeta();
				glassM.setDisplayName(" ");
				glass.setItemMeta(glassM);
				menu.addItem(glass, i);
			}
			
			menu.addItem(bar, menu.getSize()-1);
			menu.addItem(apple, 0);
			menu.addItem(fc, 1);
			menu.addItem(magic, 2);
			menu.addItem(cloud, 3);
			menu.addItem(green, 4);
			menu.addItem(fire, 5);
			menu.addItem(snow, 6);
			menu.addItem(crit, 7);
			menu.addItem(color, 8);
			menu.addItem(witch, 9);
			menu.addItem(ender, 10);
			
			return menu;
		}
		

}

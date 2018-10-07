package me.mizaki.boussole.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.aypi.utils.Button;
import com.aypi.utils.Menu;
import com.aypi.utils.inter.MenuItemListener;

import me.mizaki.boussole.main.CompassMain;

public class ClickCompassEvent implements Listener
{

	private Menu mainInventory;
	private Menu poleInventory;
	private Player player;
	
	/*
	 * Cette Fonction gere l'ouverture de l'inventaire par l'intermediaire d'une boussole
	 */
	
	@EventHandler
	public void onMakeInventoryClick(PlayerInteractEvent event) 
	{
		final Player player = event.getPlayer();
		this.player = player;
		
		if(player.getInventory().getItemInMainHand() != null || player.getInventory().getItemInOffHand() != null)
		{
			if(player.getInventory().getItemInMainHand().getType() == Material.COMPASS || player.getInventory().getItemInOffHand().getType() == Material.COMPASS)
			{
				if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
				{
					this.mainInventory = new Menu(player, "Boussole de " + player.getName(), 9);
					fillMainInventory();
					this.mainInventory.openMenu();
				}
			}
		}
		
	}
	
	private void fillMainInventory()
	{
		ItemMeta meta;
		
		ItemStack item = new ItemStack(Material.RED_BED);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Lit");
		item.setItemMeta(meta);
		
		this.mainInventory.setButton(0, new Button(item, new MenuItemListener() {
			
			@Override
			public void onItemClick()
			{
				if(player.getBedSpawnLocation() != null)
				{
					CompassMain.sendMessage(player, "Direction le lit !");
					player.setCompassTarget(player.getBedSpawnLocation());
				}
				else
					CompassMain.sendMessage(player, "Vous n'avez pas de lit !");
				
				mainInventory.closePlayerMenu();
				
			}
		}));
		
		
		item = new ItemStack(Material.ENDER_PEARL);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Position enregistree");
		item.setItemMeta(meta);
		
		this.mainInventory.setButton(1, new Button(item, new MenuItemListener() {
			
			@Override
			public void onItemClick()
			{
				// TODO Auto-generated method stub
				poleInventory.closePlayerMenu();
			}
		}));
		
		item = new ItemStack(Material.SKELETON_SKULL);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Suivre un joueur");
		item.setItemMeta(meta);
		
		this.mainInventory.setButton(2, new Button(item, new MenuItemListener() {
			
			@Override
			public void onItemClick()
			{
				// TODO Auto-generated method stub
				poleInventory.closePlayerMenu();
			}
		}));
		
		item = new ItemStack(Material.BLUE_BED);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Spawn par defaut");
		item.setItemMeta(meta);
		
		this.mainInventory.setButton(3, new Button(item, new MenuItemListener() {
			
			@Override
			public void onItemClick()
			{
				// TODO Auto-generated method stub
				poleInventory.closePlayerMenu();
			}
		}));
		
		item = new ItemStack(Material.APPLE);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Pointez le 0 !");
		item.setItemMeta(meta);
		
		this.mainInventory.setButton(4, new Button(item, new MenuItemListener() {
			
			@Override
			public void onItemClick()
			{
				CompassMain.sendMessage(player, "Direction l'origine du monde !");
				player.setCompassTarget(new Location(player.getWorld(), 0.0, 0.0, 0.0));
				mainInventory.closePlayerMenu();
			}
		}));
		
		
		item = new ItemStack(Material.ENDER_PEARL);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Pointez vers un pole");
		item.setItemMeta(meta);
		
		this.mainInventory.setButton(5, new Button(item, new MenuItemListener() {
			
			@Override
			public void onItemClick()
			{
				poleInventory = new Menu(player, "Pole", 9);
				fillPoleInventory();
				mainInventory.closePlayerMenu();
				poleInventory.openMenu();
			}
		}));
		
	}
	
	private void fillPoleInventory() {
		
		ItemMeta meta;
		
		ItemStack item = new ItemStack(Material.GRASS_BLOCK);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "NORD");
		item.setItemMeta(meta);
		
		this.poleInventory.setButton(0, new Button(item, new MenuItemListener() {
			
			@Override
			public void onItemClick()
			{
				// TODO Auto-generated method stub
				poleInventory.closePlayerMenu();
				
			}
		}));
		
		
		item = new ItemStack(Material.GRASS_BLOCK);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "SUD");
		item.setItemMeta(meta);
		
		this.poleInventory.setButton(1, new Button(item, new MenuItemListener() {
			
			@Override
			public void onItemClick()
			{
				// TODO Auto-generated method stub
				poleInventory.closePlayerMenu();
			}
		}));
		
		item = new ItemStack(Material.GRASS_BLOCK);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "EST");
		item.setItemMeta(meta);
		
		this.poleInventory.setButton(2, new Button(item, new MenuItemListener() {
			
			@Override
			public void onItemClick()
			{
				// TODO Auto-generated method stub
				poleInventory.closePlayerMenu();
			}
		}));
		
		item = new ItemStack(Material.GRASS_BLOCK);
		meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "OUEST");
		item.setItemMeta(meta);
		
		this.poleInventory.setButton(3, new Button(item, new MenuItemListener() {
			
			@Override
			public void onItemClick()
			{
				// TODO Auto-generated method stub
				poleInventory.closePlayerMenu();
			}
		}));
		
	}
	
}

package com.aymegike.bvn.utils.objets;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.aymegike.bvn.event.Respawn;

import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;

public class Preview {
	
	Player player;
	int view = 0;
	int task;
	int task2;
	ArrayList<Location> locations = new ArrayList<>();
	Location chose;
	boolean stop = false;
	
	public Preview(Player player){
		
		this.player = player;
		for(int i = 0 ; i<5 ; i++){
			locations.add(CreateLocation());
		}
		for(int i = 0 ; i< 100 ; i++)
			player.sendMessage("");
		PacketPlayOutChat chat = new PacketPlayOutChat(ChatSerializer.a("[\"\",{\"text\":\"Où voulez-vous commencer votre aventure ?\",\"color\":\"yellow\",\"bold\":true,\"underlined\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Vous avez le choix entre \",\"color\":\"light_purple\"},{\"text\":\"5\"},{\"text\":\" propositions. \",\"color\":\"light_purple\"},{\"text\":\"\nChoisissez-en une pour commencer votre aventure.\",\"color\":\"light_purple\"}]}}},{\"text\":\"\n[\",\"color\":\"gray\",\"bold\":false,\"underlined\":false},{\"text\":\"ici\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 4\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Cet endroit m'a l'air fort sympathique.\",\"color\":\"light_purple\"}]}}},{\"text\":\"] [\",\"color\":\"gray\"},{\"text\":\"suivant\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/next 5\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"J'aimerai voir une autre position.\",\"color\":\"light_purple\"}]}}},{\"text\":\"]\",\"color\":\"gray\"}]"));
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(chat);
		
		player.getInventory().setHelmet(new ItemStack(Material.PUMPKIN));
		
	}
	
	public Preview(Player player, Player target){
		this.player = player;
		lockTarget(target);
	}
	
	private void lockTarget(Player target){
		task2 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("WelcomeOnHumineCraft"), new Runnable(){

			@Override
			public void run() {
				Location loc = target.getLocation();
				chose = loc;
				if(stop == true || !target.isOnline() || Bukkit.getServer().getWorlds().get(0) != target.getLocation().getWorld()){
					if(Bukkit.getServer().getWorlds().get(0) != target.getLocation().getWorld())
						if(target.getBedSpawnLocation() != null)
							chose = new Location(Bukkit.getWorlds().get(0), target.getBedSpawnLocation().getX(), target.getBedSpawnLocation().getY(), target.getBedSpawnLocation().getZ());
						else
							chose = Respawn.getPlayerSpawn(target);
					target.sendMessage(ChatColor.DARK_PURPLE+"Position bloquée. Préparez-vous à recevoir votre ami.");
					target.playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 5, 5);
					Bukkit.getScheduler().cancelTask(task2);
				}
				
				if(!player.isOnline()){
					Bukkit.getScheduler().cancelTask(task2);
					target.playSound(loc, Sound.ENTITY_VILLAGER_NO, 5, 1);
					target.sendMessage(ChatColor.RED+"Votre ami(e) s'est déconnecté(e) vous n'allez donc pas le recevoir tout de suite.");
				}
				
				
			}
			
		}, 1, 1);
	}
	
	private Location CreateLocation(){
		Location loc;
		
		Random r = new Random();
		
		int x = r.nextInt(90000)+1 - 45000;
		int y = 100;
		int z = r.nextInt(90000)+1 - 45000;
		
		loc = new Location(Bukkit.getServer().getWorlds().get(0), x, y, z);
		
		return loc;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void passView(){
		
		
		
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("WelcomeOnHumineCraft"), new Runnable(){

			@Override
			public void run() {
				Location loc = locations.get(view);
				player.teleport(new Location(Bukkit.getServer().getWorlds().get(0), loc.getX(), 100, loc.getZ(),(player.getLocation().getYaw()+1), 50));
				if(stop == true){
					chose = loc;
					Bukkit.getScheduler().cancelTask(task);
				}
				
			}
			
		}, 2, 2);
	}
	
	public void stop(){
		stop = true;
	}
	
	public void nextView(){
		view++;
		if(view >= 5)
			view = 0;
		Bukkit.getScheduler().cancelTask(task);
		passView();
	}
	
	public int getView(){
		return view;
	}
	
	public Location getFinalLocation(){
		return chose;
	}
	

}

package com.aymegike.bvn.utils.command;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aymegike.bvn.Main;

public class ForceSpawn implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] arg) {
		
		boolean canPassNext = false;
		
		if(sender instanceof Player){
			Player p = (Player) sender;
			if(p.isOp() || p.hasPermission("woh.forcespawn")){
				if(lbl.equalsIgnoreCase("forcespawn")){
					if(arg.length == 4){
						String name = arg[0];
						int x = Integer.parseInt(arg[1]);
						int y = Integer.parseInt(arg[2]);
						int z = Integer.parseInt(arg[3]);
						
						if(Bukkit.getOfflinePlayer(name) == null){
							p.sendMessage(ChatColor.RED+"Ce joueur n'existe pas !");
							return false;
						}
						
						String uuid = Bukkit.getOfflinePlayer(name).getUniqueId().toString();
						
						ArrayList<String> line = new ArrayList<>();
						
						try(FileInputStream fis = new FileInputStream(Main.getList())){
							@SuppressWarnings("resource")
							Scanner scan = new Scanner(fis);
							while(scan.hasNext()){
								String l = scan.nextLine();
								String[] args = l.split(" ");
								
								if(!args[0].equalsIgnoreCase(uuid)){
									line.add(l);
								}else{
									canPassNext = true;
								}
							}
						}catch(IOException e){
							e.printStackTrace();
						}
						if(canPassNext){
							Main.getList().delete();
							try{
								Main.getList().createNewFile();
							}catch(IOException e){
								e.printStackTrace();
							}
							
							try(PrintWriter print = new PrintWriter(new FileOutputStream(Main.getList(), true))){
								for(String str : line){
									print.println(str);
								}
								print.println(uuid+" "+x+" "+y+" "+z);
							}catch(IOException e){
								e.printStackTrace();
							}
						}else{
							try(PrintWriter print = new PrintWriter(new FileOutputStream(Main.getList(), true))){
								print.println(uuid+" "+x+" "+y+" "+z);
								Bukkit.getPlayer(name).teleport(new Location(Bukkit.getServer().getWorlds().get(0), x, y, z));
								Bukkit.getPlayer(name).getInventory().clear();
								Bukkit.getPlayer(name).setGameMode(GameMode.SURVIVAL);
								Bukkit.getPlayer(name).kickPlayer("Merci");
							}catch(IOException e){
								e.printStackTrace();
							}
						}
						
						p.sendMessage(ChatColor.DARK_PURPLE+"Le spawn de "+name+" a bien été modifié !");
						
					}else{
						p.sendMessage(ChatColor.RED+"/forcespawn <player> <x> <y> <z> ");
					}
				}else if(lbl.equalsIgnoreCase("getuuid")){
					if(p.isOp() || p.hasPermission("woh.uuid")){
						if(arg[0] != null && Bukkit.getOfflinePlayer(arg[0]) != null){
							OfflinePlayer op = Bukkit.getOfflinePlayer(arg[0]);
							String uuid = op.getUniqueId().toString();
							p.sendMessage(ChatColor.DARK_PURPLE+op.getName()+": "+ChatColor.WHITE+uuid);
						}
					}
				}
			}
		}else{
			System.out.println("use this command in game plz !");
		}
		
		return false;
	}
	
	

}

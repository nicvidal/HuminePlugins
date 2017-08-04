package com.aymegike.bvn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.aymegike.bvn.event.EventManager;
import com.aymegike.bvn.utils.RulesCommand;
import com.aymegike.bvn.utils.command.CommandManager;

public class Main extends JavaPlugin implements Listener{
	
	private static File file;
	File book;
	private FileConfiguration config;
	private static String url;
	
	static ItemStack rules;
	
	private static Location spawn;
	private static Location spaceShip;
	
	
	public void onEnable(){
		
		System.out.println("[Bienvenue] is enable ! Aymegike plugin");
		file = new File("./plugins/WelcomeOnHumineCraft/list.yml");
		book = new File("./plugins/WelcomeOnHumineCraft/book.yml");
		EventManager.registerEvents(this);
		CommandManager.registerCommand(this);
		this.getCommand("guide").setExecutor(new RulesCommand(this));
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		config = getConfig();
		
		if(!file.exists()){
			try{
				file.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		if(!book.exists()){
			try{
				book.createNewFile();						
			}catch(IOException e){
				e.printStackTrace();
			}
			
			try(PrintWriter print = new PrintWriter(new FileOutputStream(book, true))){
				
				print.println("title: MyBook");
				print.println("author: TheEwok");
				print.println("##For right in the book, you can write 'line:' in front of all lines.##");
				print.println("##If you want to create a new page write 'titlePage:' and add a title.##");
				print.println("##When your page is finish write 'endPage' for finalise your page.##");
				print.println("page");
				print.println("line:            &4&ntitle");
				print.println("line: &1Ewoks is very cute :3");
				print.println("line: &2and Aymegike is a nice dev !");
				print.println("endPage");
				print.println("page");
				print.println("line: &1new page !");
				print.println("endPage");
				
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		System.out.println(config.getString("URL-PACK"));
		url = config.getString("URL-PACK");
	
		
		spaceShip = new Location(Bukkit.getWorld(config.getString("location.spaceShip.world")), config.getDouble("location.spaceShip.x"), config.getDouble("location.spaceShip.y"), config.getDouble("location.spaceShip.z"));
		spawn = new Location(Bukkit.getWorld(config.getString("location.spawn.world")), config.getDouble("location.spawn.x"), config.getDouble("location.spawn.y"), config.getDouble("location.spawn.z"));
		
		createBook();
	}
	
	public void onDisable(){
		
		System.out.println("[Bienvenue] is disable ! Aymegike plugin");
		
	}
	
	public static String getURL(){
		return url;
	}
	
	public static Location getSpawn(){
		return spawn;
	}
	
	public static Location getSpaceShip(){
		return spaceShip;
	}
	
	
	private void createBook(){
		rules = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bm = (BookMeta) rules.getItemMeta();
		
		try(FileInputStream fis = new FileInputStream(book)){
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(fis);
			
			ArrayList<String> page = null;
			
			boolean rightPage = false;
			
			while(sc.hasNext()){
				String line = sc.nextLine();
				String[] args = line.split(" ");
				if(args[0].equalsIgnoreCase("title:"))
					bm.setTitle(line.replace("title: ", "").replace("&", "§"));
				if(args[0].equalsIgnoreCase("author:"))
					bm.setAuthor(line.replace("author ", "").replace("&", "§"));
				
				if(args[0].equalsIgnoreCase("page")){
					rightPage = true;
					page = new ArrayList<String>();
				}
				
				if(args[0].equalsIgnoreCase("endPage")){
					rightPage = false;
					String pages = "";
					for(String str : page){
						pages = pages+"\n"+str;
					}
					bm.addPage(pages);
					page.clear();
				}
									
				if(rightPage == true){
					if(args[0].equalsIgnoreCase("line:")){
						if(args.length > 1)
							page.add(line.replace("line: ", "").replace("&", "§"));
						else
							page.add("");
					}
				}
				
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
		rules.setItemMeta(bm);
		
	}
	
	public static ItemStack getBook(){
		return rules;
	}
	
	public static File getList(){
		return file;
	}
	
	
	

}

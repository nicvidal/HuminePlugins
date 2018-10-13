package com.aypi.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;





public class FileManagerV2 {

	private File file;
	private FileConfiguration config;
	
	public FileManagerV2() {
		this.file = null;
		this.config = null;
	}
	
	
	
	
	
	
	public FileManagerV2(File file) {
		this.file = file;
		this.config = YamlConfiguration.loadConfiguration(this.file);
	}
	
	
	
	
	
	
	public File getFile() {
		return file;
	}






	public void setFile(File file) {
		this.file = file;
	}






	public FileConfiguration getConfig() {
		return config;
	}






	public void setConfig(FileConfiguration config) {
		this.config = config;
	}






	public void save() {
		try {
			this.config.save(this.file);
		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage("[AYPI] " + ChatColor.RED + ChatColor.BOLD + "Erreur enregistrement du fichier !");
			Bukkit.getServer().getConsoleSender().sendMessage("[AYPI] " + ChatColor.RED + e.getMessage());
		}
	}
	
	
	
	
	
	
	public <T> void setHashMap(String section, HashMap<T, T> liste) {
		try {
			for(Entry<T, T> entry : liste.entrySet()) {
				this.config.set(section + "." + entry.getKey(), entry.getValue());
			}
			
		} catch (Exception e) {
			Bukkit.getServer().getConsoleSender().sendMessage("[AYPI] " + ChatColor.RED + ChatColor.BOLD + "Erreur d'écriture de la HashMap !");
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + e.getMessage());
		}
	}

	
	
	
	
	
	public HashMap<Object, Object> getHashMap(String section) {
		HashMap<Object, Object> liste = new HashMap<Object, Object>();
		
		if(this.config.contains(section)) {
			for(Object key : this.config.getConfigurationSection(section).getKeys(false)) {
				liste.put(key, this.config.get(section + "." + key));
			}
			
			return liste;
		}
		else {
			Bukkit.getServer().getConsoleSender().sendMessage("[AYPI] " + ChatColor.RED + ChatColor.BOLD + "Section " + section + " inexistante !");
			return null;
		}
		
	}
	
	
	
	
	
	
	public <T> void setList(String section, List<T> liste) {
		try {
			for(int i = 0; i < liste.size(); i++) {
				this.config.set(section, liste.get(i));
			}
		} catch (Exception e) {
			
		}
	}
	
	
	
	
	
	public ArrayList<Object> getList(String section) {
		ArrayList<Object> liste = new ArrayList<Object>();
		
		if(this.config.contains(section)) {
			
			for(Object key : this.config.getList(section)) {
				liste.add(key);
			}
			
			return liste;
		}
		else {
			Bukkit.getServer().getConsoleSender().sendMessage("[AYPI] " + ChatColor.RED + ChatColor.BOLD + "Section " + section + " inexistante !");
			return null;
		}
	}
	
	
	
	
	
	
	
	public void set(String s, Object obj) {
		this.config.set(s, obj);
	}
	
	
	
	
	
	
	
	public Object get(String s) {
		Object obj;
		if(this.config.contains(s)) {
			obj = this.config.get(s);
			return obj;
		}
		else {
			Bukkit.getServer().getConsoleSender().sendMessage("[AYPI] " + ChatColor.RED + ChatColor.BOLD + s + " est inexistant !");
			return null;
		}
	}
	
	
	
	
	
	
	public boolean contains(String s) {
		if(this.config.contains(s)) {
			return true;
		}
		else {
			return false;
		}
	}
	
}

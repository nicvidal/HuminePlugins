package com.aymegike.huminekingdom.utils;

import org.bukkit.Bukkit;

import com.aymegike.huminekingdom.HumineKingdom;

public class Clock {
	
	static int task;
	
	public static void clock(){
			
			task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("HumineKingdom"), new Runnable(){
				
				@Override
				public void run() {
					HumineKingdom.isTime();
				}
				
			}, 20, 20);	
			
		}
	
	}

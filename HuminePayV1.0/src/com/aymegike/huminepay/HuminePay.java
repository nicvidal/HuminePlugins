package com.aymegike.huminepay;

import org.bukkit.plugin.java.JavaPlugin;

import com.aymegike.huminepay.command.CommandManager;
import com.aymegike.huminepay.event.EventManager;
import com.aymegike.huminepay.utils.PlayerUpdater;

public class HuminePay extends JavaPlugin {
	
	@Override
	public void onEnable(){
		System.out.println("-------------");
		System.out.println("$ HuminePay $\n");
		System.out.println("-------------");
		
		PlayerUpdater.Updater(this);
		EventManager.registerEvents(this);
		CommandManager.registerCommand(this);
	}
	
	@Override
	public void onDisable(){
		
	}
}






//new BukkitRunnable() {
//	
//	@Override
//	public void run() {
//		if(!pet.isValid()){
//			this.cancel();
//			pet.remove();
//			return;
//		}
//		
//		if(!p.isOnline()){
//			this.cancel();
//			pet.remove();
//			return;
//		}
//		
//		Location loc = p.getLocation();
//		Location petLoc = pet.getLocation();
//		if(loc.distance(petLoc) > 5){
//			pet.setVelocity(new Vector(loc.getX()-petLoc.getX(), loc.getY()-petLoc.getY(), loc.getZ()-petLoc.getZ()).multiply(0.2));
//		}else if(loc.distance(petLoc) <= 3){
//			
//			pet.teleport(pet.getLocation().setDirection(p.getLocation().subtract(pet.getLocation()).toVector()));
//		}
//		int distance = (int) loc.distance(pet.getLocation());
//		
//		if(distance > 10 && !pet.isDead() && p.isOnGround()){
//			pet.teleport(loc);
//		}
//	}
//}.runTaskTimer(this, 0, 1);
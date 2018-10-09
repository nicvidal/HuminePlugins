package com.aypi.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import com.aypi.events.FinishTimerEvent;
import com.aypi.events.StartTimerEvent;

public class Timer implements Runnable
{

	private BukkitTask	task;
	private int			duration;
	private boolean		start;
	private Plugin		plugin;
	private String		name;

	
	
	
	
	
	/*
	 * Class Timer permettant de gerer un compte à rebour à multi-Thread
	 *
	 *
	 *
	 * Class constructeur de Timer
	 * 
	 * @param plugin Plugin dans lequel le constructeur est appeller
	 */
	public Timer(Plugin plugin)
	{
		this.duration = 0;
		this.start = false;
		this.plugin = plugin;
		this.name = "";
	}

	
	
	
	
	
	/*
	 * Class constructeur de Timer
	 * 
	 * @param plugin Plugin dans lequel le constructeur est appeller
	 * 
	 * @param duration Temps du compte à rebours en SECONDE
	 */
	public Timer(Plugin plugin, int duration)
	{
		this.duration = duration;
		this.start = false;
		this.plugin = plugin;
		this.name = "";
	}

	
	
	
	
	
	/*
	 * Class constructeur de Timer
	 * 
	 * @param plugin Plugin dans lequel le constructeur est appeller
	 * 
	 * @param duration Temps du compte à rebours en SECONDE
	 * 
	 * @param name Nommez le timer
	 */
	public Timer(Plugin plugin, int duration, String name)
	{
		this.duration = duration;
		this.start = false;
		this.plugin = plugin;
		this.name = name;
	}

	
	
	
	
	
	/*
	 * Demarrer le compte a rebours
	 */
	public void start()
	{
		this.task = Bukkit.getScheduler().runTaskTimer(this.plugin, this, 0L, 20L);
		this.start = true;
		Bukkit.getPluginManager().callEvent(new StartTimerEvent(this));
	}

	
	
	
	
	
	/*
	 * arreter le compte a rebours
	 */
	public void finish()
	{
		this.task.cancel();
		this.start = false;
		Bukkit.getPluginManager().callEvent(new FinishTimerEvent(this));
	}

	
	
	
	
	
	@Override
	public void run()
	{
		if (this.duration > 0)
		{
			this.duration--;
		}
		else
		{
			finish();
		}
	}

	
	
	
	
	
	/*
	 * recupere le nombre de secondes restants
	 */
	public int getDuration()
	{
		return duration;
	}

	
	
	
	
	
	/*
	 * verifie si le Timer est lancé
	 */
	public boolean isStart()
	{
		return start;
	}

	
	
	
	
	
	/*
	 * récupère la tache en cours
	 */
	public BukkitTask getTask()
	{
		return task;
	}

	
	
	
	
	
	/*
	 * récupère le Plugin
	 */
	public Plugin getPlugin()
	{
		return plugin;
	}

	
	
	
	
	
	/*
	 * récupère le nom du Timer
	 */
	public String getName()
	{
		return name;
	}

}

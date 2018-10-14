package com.aypi.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import com.aypi.Aypi;
import com.aypi.utils.events.handlers.FinishTimerEvent;
import com.aypi.utils.events.handlers.StartTimerEvent;
import com.aypi.utils.inter.TimerFinishListener;


public class Timer implements Runnable
{

	private BukkitTask	task;
	private int			duration;
	private boolean		start;
	private Plugin		plugin;
	private String		name;
	private TimerFinishListener timerListener;

	
	
	
	
	
	/*
	 * Class Timer permettant de gerer un compte � rebour � multi-Thread
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
		this.timerListener = null;
		Aypi.getTimerManager().addTimer(this);
	}

	
	
	
	
	
	/*
	 * Class constructeur de Timer
	 * 
	 * @param plugin Plugin dans lequel le constructeur est appeller
	 * 
	 * @param duration Temps du compte � rebours en SECONDE
	 */
	public Timer(Plugin plugin, int duration)
	{
		this.duration = duration;
		this.start = false;
		this.plugin = plugin;
		this.name = "";
		this.timerListener = null;
		Aypi.getTimerManager().addTimer(this);
	}

	
	
	
	
	
	/*
	 * Class constructeur de Timer
	 * 
	 * @param plugin Plugin dans lequel le constructeur est appeller
	 * 
	 * @param duration Temps du compte � rebours en SECONDE
	 * 
	 * @param name Nommez le timer
	 */
	public Timer(Plugin plugin, int duration, String name)
	{
		this.duration = duration;
		this.start = false;
		this.plugin = plugin;
		this.name = name;
		this.timerListener = null;
		Aypi.getTimerManager().addTimer(this);
	}

	
	
	
	
	/*
	 * Class constructeur de Timer
	 * 
	 * @param plugin Plugin dans lequel le constructeur est appeller
	 * 
	 * @param duration Temps du compte � rebours en SECONDE
	 * 
	 * @param listener Action que doit faire le timer une fois terminer
	 */
	public Timer(Plugin plugin, int duration, TimerFinishListener listener)
	{
		this.duration = duration;
		this.start = false;
		this.plugin = plugin;
		this.name = "";
		this.timerListener = listener;
		Aypi.getTimerManager().addTimer(this);
	}
	
	
	
	
	
	/*
	 * Class constructeur de Timer
	 * 
	 * @param plugin Plugin dans lequel le constructeur est appeller
	 * 
	 * @param duration Temps du compte � rebours en SECONDE
	 * 
	 * @param listener Action que doit faire le timer une fois terminer
	 */
	public Timer(Plugin plugin, int duration, String name, TimerFinishListener listener)
	{
		this.duration = duration;
		this.start = false;
		this.plugin = plugin;
		this.name = name;
		this.timerListener = listener;
		Aypi.getTimerManager().addTimer(this);
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
	 * verifie si le Timer est lanc�
	 */
	public boolean isStart()
	{
		return start;
	}

	
	
	
	
	
	/*
	 * r�cup�re la tache en cours
	 */
	public BukkitTask getTask()
	{
		return task;
	}

	
	
	
	
	
	/*
	 * r�cup�re le Plugin
	 */
	public Plugin getPlugin()
	{
		return plugin;
	}

	
	
	
	
	
	/*
	 * r�cup�re le nom du Timer
	 */
	public String getName()
	{
		return name;
	}






	public TimerFinishListener getFinishAction() {
		return timerListener;
	}






	public void setFinishAction(TimerFinishListener timerListener) {
		this.timerListener = timerListener;
	}
	
	
	
	public void execute() {
		this.timerListener.execute();
	}

}

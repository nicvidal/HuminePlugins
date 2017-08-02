package com.aymegike.bvn.utils.command;

import com.aymegike.bvn.Main;

public class CommandManager {
	
	public static void registerCommand(Main pl){
		
		pl.getCommand("downloadpack").setExecutor(new CommandDownLoadPack());
		pl.getCommand("next").setExecutor(new CommandNext());
		
	}

}



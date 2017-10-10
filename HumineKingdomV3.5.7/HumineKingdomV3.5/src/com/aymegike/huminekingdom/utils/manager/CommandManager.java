package com.aymegike.huminekingdom.utils.manager;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.command.commandKingdom;

public class CommandManager {
	
public static void registerCommand(HumineKingdom pl){
		
		pl.getCommand("kingdom").setExecutor(new commandKingdom());
		pl.getCommand("ranking").setExecutor(new commandKingdom());
		
	}

}

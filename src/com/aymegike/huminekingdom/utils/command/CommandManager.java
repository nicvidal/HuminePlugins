package com.aymegike.huminekingdom.utils.command;

import com.aymegike.huminekingdom.HumineKingdom;

public class CommandManager {
	
public static void registerCommand(HumineKingdom pl){
		
		pl.getCommand("kingdom").setExecutor(new commandKingdom());
		
	}

}

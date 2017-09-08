package com.aymegike.huminepay.command;

import com.aymegike.huminepay.HuminePay;

public class CommandManager {
	
	public static void registerCommand(HuminePay pl){
		
		pl.getCommand("humineclub").setExecutor(new HumineClubCommand());
		
	}

}

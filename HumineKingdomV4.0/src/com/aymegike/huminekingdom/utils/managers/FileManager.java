package com.aymegike.huminekingdom.utils.managers;

import java.io.File;

public class FileManager {
	
	public final static String KINGDOM_FILE = "./plugins/HumineKingdom/kingdoms";
	
	public FileManager() {
		new File(KINGDOM_FILE).mkdirs();
	}

}

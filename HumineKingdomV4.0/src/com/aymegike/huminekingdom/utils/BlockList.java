package com.aymegike.huminekingdom.utils;

import org.bukkit.Material;

public class BlockList {
	
	public static Material[] blackList = {Material.AIR, Material.VOID_AIR, Material.BEDROCK, Material.WATER, Material.CHEST, Material.FURNACE, Material.LAVA};
	
	public static Material[] whiteList = {Material.AIR, Material.VOID_AIR, Material.WATER, Material.LAVA, Material.FIRE};
	
	public static boolean getBlackList(Material m) {
		for (Material material : blackList) {
			if (material == m) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean getWhitList(Material m) {
		for (Material material : whiteList) {
			if (material == m) {
				return true;
			}
		}
		return false;
	}

}

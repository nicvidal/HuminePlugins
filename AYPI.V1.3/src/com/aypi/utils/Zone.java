package com.aypi.utils;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import com.aypi.Aypi;
import com.aypi.utils.inter.ZoneListener;

public class Zone {
	
	private Square square;
	private ZoneListener zoneListener;
	
	private boolean asWhiteList = false;
	
	private ArrayList<OfflinePlayer> whiteList = new ArrayList<OfflinePlayer>();
	
	public Zone(Square square, ZoneListener zoneListener) {
		Aypi.getZoneManager().addZone(this);
		this.square = square;
		this.zoneListener = zoneListener;
		
	}
	
	public void removeZone() {
		Aypi.getZoneManager().removeZone(this);
	}
	
	public Square getSquare() {
		return square;
	}
	
	public void setWhiteList(boolean value) {
		this.asWhiteList = value;
	}
	
	public boolean asWhiteList() {
		return asWhiteList;
	}
	
	public void addPlayerToWhiteList(OfflinePlayer player) {
		whiteList.add(player);
	}
	
	public void removePlayerToWhiteList(OfflinePlayer player) {
		whiteList.remove(player);
	}
	
	public ZoneListener getZoneListener () {
		return zoneListener;
	}
	
	public boolean containLocation(Location loc) {
		if (square.getPos1().getBlockX() <= loc.getBlockX()
		&& square.getPos1().getBlockY() <= loc.getBlockY()
		&& square.getPos1().getBlockZ() <= loc.getBlockZ()
		&& square.getPos2().getBlockX() >= loc.getBlockX()
		&& square.getPos2().getBlockY() >= loc.getBlockY()
		&& square.getPos2().getBlockZ() >= loc.getBlockZ()) {
			return true;
		} else {
			return false;
		}
	}

}

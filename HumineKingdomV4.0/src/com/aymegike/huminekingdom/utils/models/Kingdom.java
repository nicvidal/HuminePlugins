package com.aymegike.huminekingdom.utils.models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;

import com.aymegike.huminekingdom.HumineKingdom;
import com.aymegike.huminekingdom.utils.MenuList;
import com.aymegike.huminekingdom.utils.Permissions;
import com.aymegike.huminekingdom.utils.managers.FileManager;
import com.aypi.Aypi;
import com.aypi.utils.Square;
import com.aypi.utils.Zone;

public class Kingdom {
	
	private File gradeListFile;
	private ArrayList<Grade> grades = new ArrayList<Grade>();
	
	private File membersFile;
	private ArrayList<OfflinePlayer> members = new ArrayList<OfflinePlayer>();
	
	private File shieldFile;
	private ArrayList<ShieldGenerator> shieldGenerators = new ArrayList<ShieldGenerator>();
	
	private File shemaFile;
	private ArrayList<Shematic> shematics = new ArrayList<Shematic>();
	
	private File KingdomId;
	private String name;
	private int glory;
	private String kingGradeName;
	
	public Kingdom(String name) {
		
		System.out.println("Kingdom: "+name);
		
		this.name = name;
		gradeListFile = new File(FileManager.KINGDOM_FILE+"/"+name+"/grades");
		
		if (gradeListFile.exists()) {
			File[] list = gradeListFile.listFiles();
			for (int i = 0 ; i<list.length ; i++) {
				grades.add(new Grade(this, list[i].getName()));
			}
		}
		
		
		membersFile = new File(FileManager.KINGDOM_FILE+"/"+name+"/members.craft");
		if (membersFile.exists()) {
			for (int i = 0 ; i<new com.aypi.manager.FileManager(membersFile).getTextFile().size() ; i++) {
				System.out.println(new com.aypi.manager.FileManager(membersFile).getTextFile().get(i) + " -> "+Bukkit.getOfflinePlayer(UUID.fromString(new com.aypi.manager.FileManager(membersFile).getTextFile().get(i))).getName());
				members.add(Bukkit.getOfflinePlayer(UUID.fromString(new com.aypi.manager.FileManager(membersFile).getTextFile().get(i))));
			}
		}
		
		shieldFile = new File(FileManager.KINGDOM_FILE+"/"+name+"/shield.craft");
		if (shieldFile.exists()) {
			for (int i = 0 ; i<new com.aypi.manager.FileManager(shieldFile).getTextFile().size() ; i++) {
				String[] line = new com.aypi.manager.FileManager(shieldFile).getTextFile().get(i).split(" ");
				shieldGenerators.add(new ShieldGenerator(this, new Location(Bukkit.getWorld(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3])), new Zone(new Square(new Location(Bukkit.getWorld(line[4]), Integer.parseInt(line[5]), Integer.parseInt(line[6]), Integer.parseInt(line[7])), new Location(Bukkit.getWorld(line[8]), Integer.parseInt(line[9]), Integer.parseInt(line[10]), Integer.parseInt(line[11]))), HumineKingdom.getZoneListener(this)), Boolean.getBoolean(line[12])));
			}
		}
		
		shemaFile = new File(FileManager.KINGDOM_FILE+"/"+name+"/shematics");
		if (shemaFile.exists()) {
			for (int i = 0 ; i< shemaFile.listFiles().length ; i++) {
				String[] line = shemaFile.listFiles()[i].getName().replace(".craft", "").split("_");
				System.out.println(line[0]+" "+line[1]+" "+line[2]+" "+line[3]+" "+line[4]);
				shematics.add(new Shematic(this, line[0], getShieldGenerator(new Location(Bukkit.getWorld(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3]), Integer.parseInt(line[4])))));
			}
		}
		
		System.out.println("Members: "+members.size());
		System.out.println("Grades: "+grades.size());
		System.out.println("ShieldGenerator: "+shieldGenerators.size());
		
		setUpKingdomId();
	}
	
	public Kingdom(String name, OfflinePlayer king, String kingGradeName) {
		
		this.name = name;
		this.kingGradeName = kingGradeName;
		
		
		membersFile = new File(FileManager.KINGDOM_FILE+"/"+name+"/members.craft");
		gradeListFile = new File(FileManager.KINGDOM_FILE+"/"+name+"/grades");
		shieldFile = new File(FileManager.KINGDOM_FILE+"/"+name+"/shield.craft");
		shemaFile = new File(FileManager.KINGDOM_FILE+"/"+name+"/shematics");
		
		addMember(king);
		
		Grade kingGrade = new Grade(this, kingGradeName);
		kingGrade.addMember(king);
		
		for (PermData perm : Permissions.getAllPermissions())
			kingGrade.addPermission(perm.getPermission());
		
		addGrade(kingGrade);
		
		setUpKingdomId();
		
		HumineKingdom.getKingdomManager().addKingdom(this);
	}
	
	public void delet() {
		for (int i = 0 ; i < grades.size() ; i++) {
			removeGrade(grades.get(i));
			i--;
		}
		for (int i = 0 ; i < members.size() ; i++) {
			removeMember(members.get(i));
			i--;
		}
		for (int i = 0 ; i < shieldGenerators.size() ; i++) {
			removeShield(shieldGenerators.get(i));
			i--;
		}
		
		removeFile(new File(FileManager.KINGDOM_FILE+"/"+name));
		HumineKingdom.getKingdomManager().removeKingdom(this);
	}
	
	private void setUpKingdomId() {
		KingdomId = new File(FileManager.KINGDOM_FILE+"/"+name+"/kingdomId.craft");
		if (!KingdomId.exists()) {
			
			glory = 0;
			
			com.aypi.manager.FileManager kingdomIdManager = new com.aypi.manager.FileManager(KingdomId);
			kingdomIdManager.printLine("name: "+name);
			kingdomIdManager.printLine("glory: "+glory);
			kingdomIdManager.printLine("kingGradeName: "+kingGradeName);
			
			
		} else {
			
			com.aypi.manager.FileManager kingdomIdManager = new com.aypi.manager.FileManager(KingdomId);
			for (String str : kingdomIdManager.getTextFile()) {
				if (str.contains("golry: ")) {
					glory = Integer.parseInt(str.replace("glory: ", ""));
				}
				if (str.contains("kingGradeName: ")) {
					kingGradeName = str.replace("kingGradeName: ", "");
				}
			}
			
		}
		
	}
	
	public void addGrade(Grade grade) {
		grades.add(grade);
	}
	
	public void removeGrade(Grade grade) {
		grade.delet();
		grades.remove(grade);
	}
	
	public void addMember(OfflinePlayer player) {
		members.add(player);
		updateMemberMenu();
		new com.aypi.manager.FileManager(membersFile).printLine(player.getUniqueId().toString());
		if (player.isOnline())
			MenuList.closePlayerMenu(player.getPlayer());
	}
	
	public void removeMember(OfflinePlayer player) {
		if (HumineKingdom.getPlayerGrade(player) != null)
			HumineKingdom.getPlayerGrade(player).removeMember(player);
		members.remove(player);
		updateMemberMenu();
		new com.aypi.manager.FileManager(membersFile).removeAllLine(player.getUniqueId().toString());
	}
	
	public void addShield(ShieldGenerator shieldGenerator) {
		shieldGenerators.add(shieldGenerator);
		updateShieldGeneratorMenu();
		new com.aypi.manager.FileManager(shieldFile).printLine(shieldGenerator.getLocation().getWorld().getName()+" "+shieldGenerator.getLocation().getBlockX()+" "+shieldGenerator.getLocation().getBlockY()+" "+shieldGenerator.getLocation().getBlockZ()
				+" "+shieldGenerator.getZone().getSquare().getPos1().getWorld().getName()+" "+shieldGenerator.getZone().getSquare().getPos1().getBlockX()+" "+shieldGenerator.getZone().getSquare().getPos1().getBlockY()+" "+shieldGenerator.getZone().getSquare().getPos1().getBlockZ()
				+" "+shieldGenerator.getZone().getSquare().getPos2().getWorld().getName()+" "+shieldGenerator.getZone().getSquare().getPos2().getBlockX()+" "+shieldGenerator.getZone().getSquare().getPos2().getBlockY()+" "+shieldGenerator.getZone().getSquare().getPos2().getBlockZ()+" "+shieldGenerator.isActive());
		
	}
	
	public void removeShield(ShieldGenerator shieldGenerator) {
		String line = "";
		
		for (String str : new com.aypi.manager.FileManager(shieldFile).getTextFile()) {
			String[] arg = str.split(" ");
			if (arg[0].equalsIgnoreCase(shieldGenerator.getLocation().getWorld().getName())
			&& arg[1].equalsIgnoreCase(shieldGenerator.getLocation().getBlockX()+"")
			&& arg[2].equalsIgnoreCase(shieldGenerator.getLocation().getBlockY()+"")
			&& arg[3].equalsIgnoreCase(shieldGenerator.getLocation().getBlockZ()+"")) {
				line = str;
				Aypi.getZoneManager().removeZone(shieldGenerator.getZone());
			}
		}
		
		new com.aypi.manager.FileManager(shieldFile).removeAllLine(line);
		shieldGenerators.remove(shieldGenerator);
	}
	
	public void breakShield(ShieldGenerator shieldGenerator) {
		shieldGenerator.getLocation().getBlock().setType(Material.BEDROCK);
		
		shieldGenerator.setActive(false);
		updateShield(shieldGenerator);
		shieldGenerator.getLocation().getWorld().createExplosion(shieldGenerator.getLocation(), 4.0f);
		for (OfflinePlayer pls : members) {
			if (pls.isOnline()) {
				pls.getPlayer().sendMessage(ChatColor.RED+"Un générateur de bouclier a été désactivé.");
			}
		}
	}
	
	public void regeneShield(ShieldGenerator shieldGenerator) {
		shieldGenerator.getLocation().getBlock().setType(Material.BEACON);
		shieldGenerator.setActive(true);
		updateShield(shieldGenerator);
		for (OfflinePlayer pls : members) {
			if (pls.isOnline()) {
				pls.getPlayer().sendMessage(ChatColor.GREEN+"Un générateur de bouclier a été réactivé.");
			}
		}
	}
	
	private void updateShield(ShieldGenerator shieldGenerator) {
		
		String line = "";
		
		for (String str : new com.aypi.manager.FileManager(shieldFile).getTextFile()) {
			String[] arg = str.split(" ");
			if (arg[0].equalsIgnoreCase(shieldGenerator.getLocation().getWorld().getName())
			&& arg[1].equalsIgnoreCase(shieldGenerator.getLocation().getBlockX()+"")
			&& arg[2].equalsIgnoreCase(shieldGenerator.getLocation().getBlockY()+"")
			&& arg[3].equalsIgnoreCase(shieldGenerator.getLocation().getBlockZ()+"")) {
				line = str;
				Aypi.getZoneManager().removeZone(shieldGenerator.getZone());
			}
		}
		
		new com.aypi.manager.FileManager(shieldFile).removeAllLine(line);
		
		new com.aypi.manager.FileManager(shieldFile).printLine(shieldGenerator.getLocation().getWorld().getName()+" "+shieldGenerator.getLocation().getBlockX()+" "+shieldGenerator.getLocation().getBlockY()+" "+shieldGenerator.getLocation().getBlockZ()
				+" "+shieldGenerator.getZone().getSquare().getPos1().getWorld().getName()+" "+shieldGenerator.getZone().getSquare().getPos1().getBlockX()+" "+shieldGenerator.getZone().getSquare().getPos1().getBlockY()+" "+shieldGenerator.getZone().getSquare().getPos1().getBlockZ()
				+" "+shieldGenerator.getZone().getSquare().getPos2().getWorld().getName()+" "+shieldGenerator.getZone().getSquare().getPos2().getBlockX()+" "+shieldGenerator.getZone().getSquare().getPos2().getBlockY()+" "+shieldGenerator.getZone().getSquare().getPos2().getBlockZ()+" "+shieldGenerator.isActive());
		
	}
	
	public void addShematic(Shematic s) {
		shematics.add(s);
	}
	
	public void removeShematic(Shematic s) {
		removeFile(s.getFile());
		shematics.remove(s);
	}
	
	public Shematic getShematic(String name) {
		for (Shematic s : shematics) {
			if (s.getName().equalsIgnoreCase(name)) {
				return s;
			}
		}
		
		return null;
	}
	
	public Grade getGrade(String name) {
		
		for (Grade grade : grades) {
			if (grade.getName().equalsIgnoreCase(name)) {
				return grade;
			}
		}
		
		return null;		
	}
	
	public Grade getPlayerGrade(OfflinePlayer player) {
		
		for (Grade grade : grades) {
			for (OfflinePlayer op : grade.getMembers()) {
				if (op == player) {
					return grade;
				}
			}
		}
		
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	public int getGlory() {
		return glory;
	}
	
	public void setGlory(int value) {
		glory = value;
		new com.aypi.manager.FileManager(KingdomId).clearFile();
		List<String> list = new ArrayList<String>();
		list.add("name: "+name);
		list.add("glory: "+glory);
		list.add("kingGradeName: "+kingGradeName);
		new com.aypi.manager.FileManager(KingdomId).printList(list);
	}
	
	public ArrayList<OfflinePlayer> getMembers() {
		return members;
	}
	
	public ArrayList<Grade> getGrades() {
		return grades;
	}
	
	public ArrayList<ShieldGenerator> getShieldGenerators() {
		return shieldGenerators;
	}
	
	public ShieldGenerator getShieldGenerator(Location loc) {
		
		for (ShieldGenerator sg : shieldGenerators) {
			System.out.println(sg.getLocation().getWorld().getName().equalsIgnoreCase(loc.getWorld().getName()) +" "+ sg.getLocation().getBlockX() +" "+ loc.getBlockX() +" "+ sg.getLocation().getBlockY() +" "+ loc.getBlockY() +" "+ sg.getLocation().getBlockZ() +" "+ loc.getBlockZ());
			if (sg.getLocation().getWorld().getName().equalsIgnoreCase(loc.getWorld().getName()) && sg.getLocation().getBlockX() == loc.getBlockX() && sg.getLocation().getBlockY() == loc.getBlockY() && sg.getLocation().getBlockZ() == loc.getBlockZ()) {
				return sg;
			}
		}
		
		return null;
	}
	
	public String getKingGradeName() {
		return kingGradeName;
	}
	
	public File getShematicFile() {
		return shemaFile;
	}
	
	public ArrayList<Shematic> getShematics() {
		return shematics;
	}
	
	private void updateMemberMenu() {
		for (OfflinePlayer op : members) {
			if(op.isOnline()) {
				if (op.getPlayer().getOpenInventory().getTitle().contains("Membres")) {
					for (int i = 0 ; i < MenuList.user.size() ; i++) {
						if (MenuList.user.get(i).getPlayer() == op.getPlayer()) {
							MenuList.user.get(i).closePlayerMenu();
							MenuList.user.remove(MenuList.user.get(i));
							i--;
						}
					}
					MenuList.membersMenu(op.getPlayer()).openMenu();
				}
			}
		}
	}
	
	private void updateShieldGeneratorMenu() {
		for (OfflinePlayer op : members) {
			if(op.isOnline()) {
				if (op.getPlayer().getOpenInventory().getTitle().contains("mes generateurs")) {
					for (int i = 0 ; i < MenuList.user.size() ; i++) {
						if (MenuList.user.get(i).getPlayer() == op.getPlayer()) {
							MenuList.user.get(i).closePlayerMenu();
							MenuList.user.remove(MenuList.user.get(i));
							i--;
						}
					}
					MenuList.GestionMenu(op.getPlayer()).openMenu();
				}
			}
		}
	}
	
	private void removeFile(File file) {
		
		if (file.isDirectory()) {
			File[] list = file.listFiles();
			for (File f : list) {
				removeFile(f);
			}
		}
		
		file.delete();
		
	}

}



package com.aymegike.huminekingdom.utils;

import java.util.ArrayList;

import org.bukkit.ChatColor;

import com.aymegike.huminekingdom.utils.models.FillList;
import com.aymegike.huminekingdom.utils.models.PermData;

public class Permissions {
	
	public static final PermData BUILD = new PermData("BUILD", "kingdom.build", new FillList() {
		
		@Override
		public ArrayList<String> fillList() {
			ArrayList<String> list = new ArrayList<String>();
			list.add("Donne l'autorisation");
			list.add("de construire dans les zones protegées");
			list.add("par vos balises.");
			return list;
		}
	});
	
	public final static PermData BREAK = new PermData("BREAK", "kingdom.break", new FillList() {
		
		@Override
		public ArrayList<String> fillList() {
			ArrayList<String> list = new ArrayList<String>();
			list.add("Donne l'autorisation");
			list.add("de détruire dans les zones protegées");
			list.add("par vos balises.");
			return list;
		}
	});
	
	public final static PermData INVITE = new PermData("INVITE", "kingdom.gestion.invite", new FillList() {
		
		@Override
		public ArrayList<String> fillList() {
			ArrayList<String> list = new ArrayList<String>();
			list.add("Donne l'autorisation");
			list.add("d'inviter d'autres personnes à rejoindre");
			list.add("votre royaume.");
			return list;
		}
	});
	
	public final static PermData GRADE = new PermData("GRADE", "kingdom.gestion.grade", new FillList() {
		
		@Override
		public ArrayList<String> fillList() {
			ArrayList<String> list = new ArrayList<String>();
			list.add("Donne l'autorisation");
			list.add("de modifier les grades et");
			list.add("de modifier le grade des autres");
			list.add("personnes de votre royaume.");
			return list;
		}
	});
	
	public final static PermData CHAT = new PermData("CHAT", "kingdom.chat", new FillList() {
		
		@Override
		public ArrayList<String> fillList() {
			ArrayList<String> list = new ArrayList<String>();
			list.add("Donne l'autorisation");
			list.add("de lire et de publier des messages");
			list.add("lisible uniquement par les membres");
			list.add("de votre Royaume. Pour ce faire il");
			list.add("doit placer "+ChatColor.WHITE+"*"+ChatColor.DARK_PURPLE+" devant le message");
			list.add("en question.");
			return list;
		}
	});
	
	public final static PermData INVITE_NEW_PLAYER = new PermData("INVITE_NEW_PLAYER", "kingdom.gestion.invite.newplayer", new FillList() {
		
		@Override
		public ArrayList<String> fillList() {
			ArrayList<String> list = new ArrayList<String>();
			list.add("Donne l'autorisation");
			list.add("de recevoir de nouvelles personnes");
			list.add("sur vos terres.");
			return list;	
		}
	});
	
	public static ArrayList<PermData> getAllPermissions(){
		ArrayList<PermData> perms = new ArrayList<PermData>();
		perms.add(BUILD);
		perms.add(BREAK);
		perms.add(INVITE);
		perms.add(GRADE);
		perms.add(CHAT);
		perms.add(INVITE_NEW_PLAYER);
		return perms;
	}

}

package skript.freecam.general;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import skript.freecam.utils.SLib;

public class Main extends JavaPlugin implements Listener {
	
	public void onEnable() {
		loadConfig();
		new Commands(this);
		new Handler(this);
		Bukkit.getConsoleSender().sendMessage(SLib.colorCode("&aFreeCam has been enabled successfully."));
	}
	
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(SLib.colorCode("&cFreeCam has been disabled successfully."));
	}
	
	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
}

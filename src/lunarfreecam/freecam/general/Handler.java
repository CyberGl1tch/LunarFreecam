package skript.freecam.general;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Handler implements Listener {
	
	private HashMap<String, Boolean> freeCamPlayers = new HashMap<String, Boolean>();
	
	private Main plugin;
	
	public Handler(Main plugin) {
		this.plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		
	}
	
	@EventHandler
	public void onPlayerDisconnect(PlayerQuitEvent e) {
		
	}
	
	@EventHandler
	public void onPlayerDisconnect(PlayerKickEvent e) {
		
	}
}

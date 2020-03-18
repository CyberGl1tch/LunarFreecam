package lunarfreecam.freecam.general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import FreecamUtils.npcManager;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Handler implements Listener {


	private Main plugin;
	npcManager npcmngr = new npcManager(plugin);

	
	public Handler(Main plugin) {

		this.plugin = plugin;

		
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		Player player = e.getPlayer();
		String message = e.getMessage();
		ArrayList<String> whitelistedcmds = new ArrayList<String>();
		for(String s : plugin.getConfig().getStringList("freecam-whitelisted-commands")) {
			whitelistedcmds.add(s);
		}
		if(Main.npcalive.containsKey(player.getUniqueId())) {
			if(!whitelistedcmds.contains(message)){
				player.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("on-freecam-cmd")));
				e.setCancelled(true);
			}


		}
		
	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		Player player = e.getPlayer();
		if(e.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE) && Main.npcalive.containsKey(player.getUniqueId())){
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("freecam-spectate-teleport")));
			e.setCancelled(true);
		}

	}

	@EventHandler
	public void onPlayerDisconnect(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		//System.out.println("mphka1");
		//System.out.println(Main.npcalive.containsKey(player));
		if(Main.npcalive.containsKey(player.getUniqueId())){
			//	System.out.println("mphka2");
			player.setGameMode(GameMode.SURVIVAL);
			player.teleport(Main.npcalive.get(player.getUniqueId()).getLocation());

			npcmngr.deleteNpc(player);
			Main.npcalive.remove(player.getUniqueId());

		}

	}
	@EventHandler
	public void onFreecamDamage(EntityDamageEvent e){

		Entity victim = e.getEntity();
		if(Main.npcalive.containsValue(victim)){
			e.setCancelled(true);
			Player player = Bukkit.getPlayer(getKey(Main.npcalive,(LivingEntity) victim));
			npcmngr.goBack(player);
		}
	}


	@EventHandler
	public void onPlayerDisconnect(PlayerKickEvent e) {
		Player player = e.getPlayer();

		if(Main.npcalive.containsKey(player.getUniqueId())){
			player.setGameMode(GameMode.SURVIVAL);
			player.teleport(Main.npcalive.get(player.getUniqueId()).getLocation());
			npcmngr.deleteNpc(player);
			Main.npcalive.remove(player.getUniqueId());

		}
	}

	public <K, V> K getKey(Map<K, V> map, V value) {
		for (Map.Entry<K, V> entry : map.entrySet()) {
			if (entry.getValue().equals(value)) {
				return entry.getKey();
			}
		}
		return null;
	}
}

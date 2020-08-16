package lunarfreecam.freecam;

import java.util.ArrayList;
import java.util.Map;

import FreecamUtils.UpdateChecker;
import FreecamUtils.npcManager;
import FreecamUtils.utils;
import com.cryptomorin.xseries.XSound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.world.ChunkUnloadEvent;

public class Handler implements Listener {


	private Main plugin;
	npcManager npcmngr;

	
	public Handler(Main plugin) {
		this.plugin = plugin;
		this.npcmngr = new npcManager(plugin);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Player player = e.getPlayer();
		if(player.hasPermission("lunarfreecam.updates") || player.isOp()){
			if(Main.updateResult!=null && Main.updateResult.equals(UpdateChecker.UpdateReason.NEW_UPDATE)){
				plugin.getServer().getScheduler().runTaskLater(plugin,()->{
					player.sendMessage(utils.Color("&8&l[&c&l!&8&l] &7LunarFreecam is outdated current version is &c"+plugin.getDescription().getVersion()+"&7 Newest version is &a"+Main.version));
					player.sendMessage(utils.Color("&8&l[&c&l!&8&l] &7Download latest update at\n"+"&ahttps://www.spigotmc.org/resources/81104"));
				},20);
			}
		}
	}

	/**
	 * Event for commands when player is on freecam!
	 * @param e event
	 */
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		Player player = e.getPlayer();
		String message = e.getMessage();
		ArrayList<String> whitelistedcmds = new ArrayList<String>(plugin.getConfig().getStringList("freecam-whitelisted-commands"));
		if(Main.npcalive.containsKey(player.getUniqueId())) {
			if(!whitelistedcmds.contains(message)){
				player.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("on-freecam-cmd")));
				player.playSound(player.getLocation(), XSound.BLOCK_NOTE_BLOCK_BASS.parseSound(),100,0);
				e.setCancelled(true);
			}
		}
	}

	/**
	 * Stop Spectator Teleport
	 * @param e event
	 */
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		Player player = e.getPlayer();
		if(e.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE) && Main.npcalive.containsKey(player.getUniqueId())){
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("freecam-spectate-teleport")));
			e.setCancelled(true);
		}

	}

	/**
	 * Return player to the last location if he leaves the game!
	 * @param e
	 */
	@EventHandler
	public void onPlayerDisconnect(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		if(Main.npcalive.containsKey(player.getUniqueId())){
			player.setGameMode(GameMode.SURVIVAL);
			player.teleport(Main.npcalive.get(player.getUniqueId()).getLocation());
			npcmngr.deleteNpc(player);
			Main.npcalive.remove(player.getUniqueId());
		}
	}

	/**
	 * Stop Freecam on any damage!
	 * @param e
	 */
	@EventHandler
	public void onFreecamDamage(EntityDamageEvent e){

		Entity victim = e.getEntity();
		if(Main.npcalive.containsValue(victim)){
			e.setCancelled(true);
			Player player = Bukkit.getPlayer(getKey(Main.npcalive,(LivingEntity) victim));
			npcmngr.goBack(player);
		}
	}

	/**
	 * Return player to the last location if he leaves the game!
	 * @param e
	 */
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

	/**
	 * Stop chunks from unloading if freecam npc is there!
	 * @param e
	 */
	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent e){
		Chunk chunk = e.getChunk();
		for(Entity ent : chunk.getEntities()){
			if(Main.npcalive.containsValue(ent)){
				e.setCancelled(true);
			}
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

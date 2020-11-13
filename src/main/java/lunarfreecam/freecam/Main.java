package lunarfreecam.freecam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import FreecamUtils.UpdateChecker;
import FreecamUtils.npcManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;



public class Main extends JavaPlugin implements Listener {
	public static HashMap<UUID,Long> freecamcd = new HashMap<UUID, Long>();
	public static HashMap<UUID, LivingEntity> npcalive = new HashMap<UUID, LivingEntity>();
	private boolean isVaultEnabled;
	public static ArrayList<Player> playersInFreecam = new ArrayList<>();
	public static UpdateChecker.UpdateReason updateResult = null;
	public static String version = null;
	public static Integer pluginID = 81104;

	public void onEnable() {
		loadConfig();
		hook();
		UpdateChecker.init(this,pluginID);
		UpdateCheck();
		new Commands(this);
		new Handler(this);
		new npcManager(this);
	}

	/**
	 * Return all players to their last locations if server stops or restarts!
	 */
	public void onDisable() {
		npcalive.forEach((key, value) -> {
			Player player = Bukkit.getPlayer(key);
			player.setGameMode(GameMode.SURVIVAL);
			player.teleport(Main.npcalive.get(player.getUniqueId()).getLocation());
			value.remove();
		});
	}
	
	public void loadConfig() {
		this.getConfig().options().copyDefaults(true);
		saveDefaultConfig();
	}
	public void hook(){
		isVaultEnabled = Bukkit.getServer().getPluginManager().isPluginEnabled("Vault");
	}
	public boolean isVaultEnabled(){
		return isVaultEnabled;
	}
	private void UpdateCheck(){
		CompletableFuture<UpdateChecker.UpdateResult> task = UpdateChecker.get().requestUpdateCheck();
		task.thenAccept(this::make);
	}

	private void make(UpdateChecker.UpdateResult updateResulttmp) {
		updateResult = updateResulttmp.getReason();
		version= updateResulttmp.getNewestVersion();
	}

}

package lunarfreecam.freecam.general;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import FreecamUtils.CooldownManager;
import FreecamUtils.npcManager;

import actionbar.ActionBarApi;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;


public class Main extends JavaPlugin implements Listener {
	private static Main instance;
	public  static HashMap<UUID,Integer> freecamcd = new HashMap<UUID, Integer>();
	public  static HashMap<UUID, LivingEntity> npcalive = new HashMap<UUID, LivingEntity>();

	npcManager npcmngr = new npcManager(this);

	public void onEnable() {

		new ActionBarApi(this);
		instance =this;
		loadConfig();
		new Commands(this);
		new Handler(this);
		new npcManager(this);
		BukkitTask cdtask = new CooldownManager().runTaskTimer(this,0,20);

	}
	
	public void onDisable() {
		for (Map.Entry<UUID,LivingEntity> entry : npcalive.entrySet()) {
			UUID key = entry.getKey();
			//LivingEntity entity = entry.getValue();
			npcmngr.goBack(Bukkit.getPlayer(key));
			// do stuff
		}

	}
	
	public void loadConfig() {
		this.getConfig().options().copyDefaults(true);
		saveDefaultConfig();
	}
	public static Main getInstance(){
		return instance;
	}


}

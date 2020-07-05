package lunarfreecam.freecam;

import FreecamUtils.FreecamCountDown;
import FreecamUtils.npcManager;

import FreecamUtils.utils;
import VaultUtils.VaultUtils;
import com.cryptomorin.xseries.XSound;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;


import org.bukkit.scheduler.BukkitTask;


public class Commands implements CommandExecutor,Listener {

	private String freecam = "freecam";
	private String roam = "roam";
	private Main plugin;
	private VaultUtils vaultUtils;
    npcManager npcmngr = new npcManager(plugin);


	public Commands(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand(freecam).setExecutor(this);
		plugin.getCommand(roam).setExecutor(this);
		vaultUtils = new VaultUtils(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase(freecam) || cmd.getName().equalsIgnoreCase(roam)){
				if(player.hasPermission("freecam.use") || player.isOp()){
					if(args.length <1){
						if(utils.isPlayerOnCooldown(player,Main.freecamcd,plugin.getConfig().getInt("freecam-cooldown-seconds"))){
							player.sendMessage(utils.Color(plugin.getConfig().getString("freecam-cooldown").replace("%seconds%",String.valueOf(utils.getCoolDownTimeRemaining(player,Main.freecamcd,plugin.getConfig().getInt("freecam-cooldown-seconds"))))));
							return true;
						}
						//Activate freecam
						if(plugin.getConfig().getBoolean("freecam-charge-money")){
							if(plugin.isVaultEnabled()){
								if(!vaultUtils.canPayForFreecam(player)){
									return true;
								}
							}else{
								player.sendMessage(utils.Color("&8[&5LunarFreecam&8] &cVault is needed because economy is enabled from the config!"));
								plugin.getServer().getConsoleSender().sendMessage(utils.Color("&8[&5LunarFreecam&8] &4&lVault is needed because economy is enabled from the config!"));
								return true;
							}
						}
						player.playSound(player.getLocation(), XSound.ENTITY_PLAYER_LEVELUP.parseSound(),100,2);
						player.setGameMode(GameMode.SPECTATOR);
						npcmngr.createtmpNPC(player);
						Main.freecamcd.put(player.getUniqueId(),System.currentTimeMillis());
						BukkitTask task = new FreecamCountDown(player,plugin.getConfig().getInt("freecam-period"),plugin).runTaskTimer(plugin,0,20);
						return true;

					}else{
						if(args[0].equalsIgnoreCase("stop")){
							if(Main.npcalive.containsKey(player.getUniqueId())) {
								npcmngr.goBack(player);
								return true;
							}else{
								player.sendMessage(utils.Color(plugin.getConfig().getString("freecam-no-use")));
								return true;
							}
						}else if(args[0].equalsIgnoreCase("reload")){
							if(player.hasPermission("lunarfreecam.reload")){
								plugin.reloadConfig();
								player.sendMessage(utils.Color(plugin.getConfig().getString("freecam-reload")));
								return true;
							}

						}
						player.sendMessage(utils.Color(plugin.getConfig().getString("too-many-arguments-message")));
						return true;

					}

				}else {
					player.sendMessage(utils.Color(plugin.getConfig().getString("no-permission-message")));
					return true;
				}

			}

		}else {
			sender.sendMessage("&8&l[&c&l!&8&l] &7Only players can use this command!");
		}
		return true;
	}

}

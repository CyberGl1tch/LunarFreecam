package	lunarfreecam.freecam.general;

import FreecamUtils.CooldownManager;
import FreecamUtils.FreecamCountDown;
import FreecamUtils.npcManager;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;


import org.bukkit.scheduler.BukkitTask;


public class Commands implements CommandExecutor,Listener {

	private String freecam = "freecam";
	private Main plugin;
    npcManager npcmngr = new npcManager(plugin);


	public Commands(Main plugin) {
		this.plugin = plugin;
		
		plugin.getCommand(freecam).setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase(freecam)) {
				if(player.hasPermission("freecam.usecam") || player.isOp()) {
					if(args.length == 0) {
						if(plugin.freecamcd.containsKey(player.getUniqueId())){
							player.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("freecam-cooldown").replace("%seconds%",Main.freecamcd.get(player.getUniqueId()).toString())));
							return true;
						}
						player.setGameMode(GameMode.SPECTATOR);

                        npcmngr.createtmpNPC(player);
						plugin.freecamcd.put(player.getUniqueId(),plugin.getConfig().getInt("freecam-cooldown-seconds"));
                        BukkitTask task = new FreecamCountDown(player,plugin.getConfig().getInt("freecam-period")).runTaskTimer(plugin,0,20);
						//player.sendMessage(SLib.colorCode(plugin.getConfig().getString("freecam-mode-on-success")));



						return true;
					}
					else if(args.length > 0) {
					    if(args[0].equalsIgnoreCase("stop")){
					        if(Main.npcalive.containsKey(player.getUniqueId())) {
                                npcmngr.goBack(player);
                                return true;
                            }else{
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("freecam-no-use")));
                                return true;
                            }
                        }else if(args[0].equalsIgnoreCase("reload")){
					        if(player.hasPermission("lunarfreecam.reload")){
                                plugin.reloadConfig();
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("freecam-reload")));
                                return true;
                            }

                        }
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("too-many-arguments-message")));
						return true;
					}
				}
				else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("no-permission-message")) );
					return true;
				}
			}
		}
		else {
			sender.sendMessage("&8&l[&c&l!&8&l] &7Only players can use this command!");
		}
		return true;
	}
	
	
	
}

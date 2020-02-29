package skript.freecam.general;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.minecraft.server.v1_8_R3.CommandExecute;
import skript.freecam.utils.SLib;

public class Commands extends CommandExecute implements CommandExecutor,Listener {

	private String freecam = "freecam";
	private Main plugin;
	
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
						player.setGameMode(GameMode.SPECTATOR);
						player.sendMessage(SLib.colorCode(plugin.getConfig().getString("freecam-mode-on-success")));
						return true;
					}
					else if(args.length > 0) {
						player.sendMessage(SLib.colorCode(plugin.getConfig().getString("too-many-arguments-message")));
						return true;
					}
				}
				else {
					player.sendMessage(SLib.colorCode(plugin.getConfig().getString("no-permission-message")));
					return true;
				}
			}
		}
		else {
			sender.sendMessage(SLib.colorCode("&8&l[&c&l!&8&l] &7Only players can use this command!"));
		}
		return false;
	}
	
	
	
}

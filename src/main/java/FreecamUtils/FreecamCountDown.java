package FreecamUtils;

import com.cryptomorin.xseries.messages.ActionBar;
import lunarfreecam.freecam.Commands;
import lunarfreecam.freecam.Main;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.stream.Collectors;


public class FreecamCountDown extends BukkitRunnable {
    private Player player;
    private Integer seconds;
    private double maxDistance;
    private Main plugin;
    private GameMode mode;
    npcManager npcmngr;

    public FreecamCountDown(Player player, Integer seconds, Main main) {
        this.player = player;
        this.plugin = main;
        this.mode = Commands.prevGamemode.get(player);
        this.npcmngr = new npcManager(plugin);

        // Check for seconds permission override
        int permissionSeconds = getPermissionSeconds(player);
        if (permissionSeconds != -1) {
            this.seconds = permissionSeconds;
        } else {
            this.seconds = seconds;
        }

        // Check for distance permission override
        double permissionDistance = getPermissionDistance(player);
        if (permissionDistance != -1) {
            this.maxDistance = permissionDistance;
        } else {
            this.maxDistance = plugin.getConfig().getDouble("freecam-max-distance");
        }
    }

    private int getPermissionSeconds(Player player) {
        int highestSeconds = -1;

        for (String permission : player.getEffectivePermissions().stream()
                .map(perm -> perm.getPermission())
                .filter(perm -> perm.startsWith("freecam.use.secconds."))
                .collect(Collectors.toSet())) {
            try {
                String numberStr = permission.substring(permission.lastIndexOf('.') + 1);
                int seconds = Integer.parseInt(numberStr);
                if (seconds > highestSeconds) {
                    highestSeconds = seconds;
                }
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                continue;
            }
        }

        return highestSeconds;
    }

    private double getPermissionDistance(Player player) {
        double highestDistance = -1;

        for (String permission : player.getEffectivePermissions().stream()
                .map(perm -> perm.getPermission())
                .filter(perm -> perm.startsWith("freecam.use.distance."))
                .collect(Collectors.toSet())) {
            try {
                String numberStr = permission.substring(permission.lastIndexOf('.') + 1);
                double distance = Double.parseDouble(numberStr);
                if (distance > highestDistance) {
                    highestDistance = distance;
                }
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                continue;
            }
        }

        return highestDistance;
    }

    @Override
    public void run() {
        if (!player.isOnline() || !Main.npcalive.containsKey(player.getUniqueId()) || Main.npcalive.get(player.getUniqueId()).isDead() || player.isDead()) {
            player.sendMessage(utils.Color(plugin.getConfig().getString("freecam-canceled")));
            ActionBar.sendActionBar(player, utils.Color(plugin.getConfig().getString("freecam-canceled")));
            this.cancel();
            return;
        }
        if (maxDistance > 0 || seconds > 0) {
            if (!((Entity) Main.npcalive.get(player.getUniqueId())).getWorld().equals(player.getWorld())
                    || getDistanceBetweenEntities(player, (Entity) Main.npcalive.get(player.getUniqueId())) > maxDistance) {
                this.cancel();
                npcmngr.goBack(player, mode);
                player.sendMessage(utils.Color(plugin.getConfig().getString("freecam-max-distance-reach")));
                ActionBar.sendActionBar(player, utils.Color(plugin.getConfig().getString("freecam-max-distance-reach")));
            } else if (seconds >= 0) {
                ActionBar.sendActionBar(player, utils.Color(plugin.getConfig().getString("freecam-action-bar").replace("%seconds%", seconds.toString())));
                seconds -= 1;
            } else {
                this.cancel();
                npcmngr.goBack(player, mode);
            }
        } else {
            ActionBar.sendActionBar(player, utils.Color(plugin.getConfig().getString("freecam-action-bar").replace("%seconds%", "")));
        }
    }

    public double getDistanceBetweenEntities(Entity e1, Entity e2) {
        return e1.getLocation().distance(e2.getLocation());
    }
}
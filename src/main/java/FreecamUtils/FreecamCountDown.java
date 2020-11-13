package FreecamUtils;

import com.cryptomorin.xseries.messages.ActionBar;
import lunarfreecam.freecam.Commands;
import lunarfreecam.freecam.Main;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FreecamCountDown extends BukkitRunnable {
    private Player player;
    private Integer seconds;
    private Main plugin;
    private GameMode mode;
    npcManager npcmngr;
    public FreecamCountDown(Player player, Integer seconds,Main main){
        this.player= player;
        this.seconds = seconds;
        this.plugin = main;
        this.mode= Commands.prevGamemode.get(player);
        this.npcmngr = new npcManager(plugin);
    }
    @Override
    public void run() {
        if(!player.isOnline() || !Main.npcalive.containsKey(player.getUniqueId()) || Main.npcalive.get(player.getUniqueId()).isDead() || player.isDead()){
            player.sendMessage(utils.Color(plugin.getConfig().getString("freecam-canceled")));
            ActionBar.sendActionBar(player,utils.Color(plugin.getConfig().getString("freecam-canceled")));
            this.cancel();
            return;
        }
        if(plugin.getConfig().getDouble("freecam-max-distance")>0 || seconds>0) {
            if (getDistanceBetweenEntities(player, (Entity) Main.npcalive.get(player.getUniqueId())) > plugin.getConfig().getDouble("freecam-max-distance")) {
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
        }else{
            ActionBar.sendActionBar(player, utils.Color(plugin.getConfig().getString("freecam-action-bar").replace("%seconds%", "")));
        }


    }
    public double getDistanceBetweenEntities(Entity e1, Entity e2){
        return e1.getLocation().distance(e2.getLocation());
    }
}

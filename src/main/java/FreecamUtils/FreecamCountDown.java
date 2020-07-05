package FreecamUtils;

import com.cryptomorin.xseries.messages.ActionBar;
import lunarfreecam.freecam.Main;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FreecamCountDown extends BukkitRunnable {
    private Player player;
    private Integer seconds;
    private Main plugin;
    npcManager npcmngr;
    public FreecamCountDown(Player player, Integer seconds,Main main){
        this.player= player;
        this.seconds = seconds;
        this.plugin = main;
        this.npcmngr = new npcManager(plugin);
    }
    @Override
    public void run() {
        if(!player.isOnline() || !Main.npcalive.containsKey(player.getUniqueId())){
            this.cancel();
            player.sendMessage(utils.Color(plugin.getConfig().getString("freecam-canceled")));
            ActionBar.sendActionBar(player,utils.Color(plugin.getConfig().getString("freecam-canceled")));
        }
        else if(getDistanceBetweenEntities(player,(Entity) Main.npcalive.get(player.getUniqueId())) > plugin.getConfig().getDouble("freecam-max-distance")){
            this.cancel();
            npcmngr.goBack(player);
            player.sendMessage(utils.Color(plugin.getConfig().getString("freecam-max-distance-reach")));
            ActionBar.sendActionBar(player,utils.Color(plugin.getConfig().getString("freecam-max-distance-reach")));
        }
        else if(seconds >=0){
            ActionBar.sendActionBar(player,utils.Color(plugin.getConfig().getString("freecam-action-bar").replace("%seconds%",seconds.toString())));
            seconds -=1;
        }else{
            this.cancel();
            npcmngr.goBack(player);
        }


    }
    public double getDistanceBetweenEntities(Entity e1, Entity e2){
        return e1.getLocation().distance(e2.getLocation());
    }
}

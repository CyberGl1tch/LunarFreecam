package FreecamUtils;



import actionbar.ActionBarApi;
import lunarfreecam.freecam.general.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FreecamCountDown extends BukkitRunnable {
    private Player player;
    private Integer seconds;
    npcManager npcmngr = new npcManager(Main.getInstance());
    public FreecamCountDown(Player player, Integer seconds){
        this.player= player;
        this.seconds = seconds;

    }
    @Override
    public void run() {
        if(!player.isOnline() || !Main.npcalive.containsKey(player.getUniqueId())){
            this.cancel();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.getInstance().getConfig().getString("freecam-canceled")));
           // Main.getInstance().title.sendActionBar(player, ChatColor.translateAlternateColorCodes('&',Main.getInstance().getConfig().getString("freecam-canceled")));
            //ActionBarAPI.sendActionBar(player,ChatColor.translateAlternateColorCodes('&',Main.getInstance().getConfig().getString("freecam-canceled")));
            ActionBarApi.sendActionBar(player,ChatColor.translateAlternateColorCodes('&',Main.getInstance().getConfig().getString("freecam-canceled")));
        }
        else if(getDistanceBetweenEntities(player,(Entity) Main.npcalive.get(player.getUniqueId())) > Main.getInstance().getConfig().getDouble("freecam-max-distance")){
            this.cancel();
            npcmngr.goBack(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("freecam-max-distance-reach")));
            //Main.getInstance().title.sendActionBar(player, ChatColor.translateAlternateColorCodes('&',Main.getInstance().getConfig().getString("freecam-max-distance-reach")));
           ActionBarApi.sendActionBar(player,ChatColor.translateAlternateColorCodes('&',Main.getInstance().getConfig().getString("freecam-max-distance-reach")));
        }
        else if(seconds >=0){

            //Main.getInstance().title.sendActionBar(player, ChatColor.translateAlternateColorCodes('&',Main.getInstance().getConfig().getString("freecam-action-bar").replace("%seconds%",seconds.toString())) );

            ActionBarApi.sendActionBar(player,ChatColor.translateAlternateColorCodes('&',Main.getInstance().getConfig().getString("freecam-action-bar").replace("%seconds%",seconds.toString())));
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

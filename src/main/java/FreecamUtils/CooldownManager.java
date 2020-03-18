package FreecamUtils;

import lunarfreecam.freecam.general.Main;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class CooldownManager extends BukkitRunnable {


    @Override
    public void run() {
        if(Main.freecamcd.isEmpty()){
            return;
        }
        for(UUID uuid : Main.freecamcd.keySet()){
            int timeleft = Main.freecamcd.get(uuid);
            if(timeleft == 0){
                Main.freecamcd.remove(uuid);
            }else{
                Main.freecamcd.put(uuid,timeleft-1);
            }
        }
    }
}

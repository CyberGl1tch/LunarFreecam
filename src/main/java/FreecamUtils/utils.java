package FreecamUtils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class utils {
    public static String Color(String s){
        s = ChatColor.translateAlternateColorCodes('&',s);
        return s;
    }

    /**
     * Cool down checker !
     */
    public static boolean isPlayerOnCooldown(Player player, HashMap<UUID,Long> cooldownMap, Integer cooldownSeconds){
        if(!cooldownMap.containsKey(player.getUniqueId())){
            return false;
        }
        long secondsleft = ((cooldownMap.get(player.getUniqueId())/1000 + cooldownSeconds) - (System.currentTimeMillis()/1000));
        if(secondsleft>0){
            return true;
        }else{
            cooldownMap.remove(player.getUniqueId());
            return false;
        }
    }
    /**
     * Get time remaining!
     */
    public static Integer getCoolDownTimeRemaining(Player player, HashMap<UUID,Long> cooldownMap,Integer cooldownSeconds){
        if(cooldownMap.containsKey(player.getUniqueId())){
            long secondsleft = ((cooldownMap.get(player.getUniqueId())/1000 + cooldownSeconds) - (System.currentTimeMillis()/1000));
            return Math.toIntExact(secondsleft);
        }
        return 0;
    }

}

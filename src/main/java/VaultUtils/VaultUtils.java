package VaultUtils;

import FreecamUtils.utils;
import com.cryptomorin.xseries.XSound;
import lunarfreecam.freecam.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultUtils {
    private Main plugin;
    private static Economy econ;
    public VaultUtils(Main main){
        this.plugin=main;
        setupEconomy();
    }

    public boolean setupEconomy() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public boolean canPayForFreecam(Player player){
        if(econ.getBalance(player) >= plugin.getConfig().getInt("freecam-cost")){
            econ.withdrawPlayer(player,plugin.getConfig().getInt("freecam-cost"));
            player.sendMessage(utils.Color(plugin.getConfig().getString("freecam-money-charge").replace("%money%",String.valueOf(plugin.getConfig().getInt("freecam-cost")))));
            return true;
        }else{
            player.playSound(player.getLocation(), XSound.BLOCK_NOTE_BLOCK_BASS.parseSound(),100,0);
            player.sendMessage(utils.Color(plugin.getConfig().getString("freecam-no-money")));
            return false;
        }
    }


}

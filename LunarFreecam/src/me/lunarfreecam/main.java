package me.lunarfreecam;

import org.bukkit.plugin.java.JavaPlugin;
import lunarfreecame.handlers.Handlers;

public class main extends JavaPlugin {
    @Override
    public void onEnable(){
        new Handlers(this);
    }

    public void onDisable(){
        //Main

    }
}

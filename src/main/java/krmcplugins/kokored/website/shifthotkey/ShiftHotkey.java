package krmcplugins.kokored.website.shifthotkey;

import org.bukkit.plugin.java.JavaPlugin;

import krmcplugins.kokored.website.shifthotkey.hotkey.ShiftF;
import krmcplugins.kokored.website.shifthotkey.hotkey.ShiftQ;

public final class ShiftHotkey extends JavaPlugin {

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        Metrics metrics = new Metrics(this, 14329);
        
        new ShiftF();
        new ShiftQ();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

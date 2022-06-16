package krmcplugins.kokored.website.shifthotkey;

import org.bukkit.plugin.java.JavaPlugin;

import krmcplugins.kokored.website.shifthotkey.hotkey.ShiftF;
import krmcplugins.kokored.website.shifthotkey.hotkey.ShiftQ;
import krmcplugins.kokored.website.shifthotkey.util.Config;

public final class ShiftHotkey extends JavaPlugin {

    private static String version = "1.0.3";

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        new Config(this);
        Config.check();

        Metrics metrics = new Metrics(this, 14329);
        
        new ShiftF();
        new ShiftQ();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public String getVersion() {
        return version;
    }
}

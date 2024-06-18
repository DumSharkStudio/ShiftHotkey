package com.sharkylab.sharkycraft.shifthotkey;

import com.sharkylab.sharkycraft.shifthotkey.listener.PlayerDropItemListener;
import com.sharkylab.sharkycraft.shifthotkey.listener.PlayerSwapItemListener;
import com.sharkylab.sharkycraft.shifthotkey.manager.ConfigManager;
import com.sharkylab.sharkycraft.shifthotkey.utils.UpdateChecker;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShiftHotkey extends JavaPlugin {

    @Override
    public void onEnable() {

        new Metrics(this, 14329);
        ConfigManager.getInstance().setPlugin(this);
        ConfigManager.Loader.loadConfigs();

        new UpdateChecker(this, 100043).getVersion(version -> {
            if (!getDescription().getVersion().equals(version)) {
                getLogger().info("There is a new update of ShiftHotkey available.");
                getLogger().info("Current: " + getDescription().getVersion());
                getLogger().info("Latest: " + version);
                getLogger().info("");
                getLogger().info("Download at https://www.spigotmc.org/resources/shifthotkey.100043/");
            }
        });

        new PlayerDropItemListener();
        new PlayerSwapItemListener();

    }

    @Override
    public void onDisable() {

    }
}

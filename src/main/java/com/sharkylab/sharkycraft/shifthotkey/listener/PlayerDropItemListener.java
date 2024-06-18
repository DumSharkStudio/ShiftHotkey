package com.sharkylab.sharkycraft.shifthotkey.listener;

import com.sharkylab.sharkycraft.shifthotkey.ShiftHotkey;
import com.sharkylab.sharkycraft.shifthotkey.manager.ActionManager;
import com.sharkylab.sharkycraft.shifthotkey.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class PlayerDropItemListener implements Listener {

    private final Plugin plugin = ShiftHotkey.getPlugin(ShiftHotkey.class);
    private final Boolean enable = ConfigManager.Loader.getConfig(ConfigManager.Type.KeyMap).getBoolean("drop.enable");
    private final Boolean cancel = ConfigManager.Loader.getConfig(ConfigManager.Type.KeyMap).getBoolean("drop.cancel");
    private final Boolean shift = ConfigManager.Loader.getConfig(ConfigManager.Type.KeyMap).getBoolean("drop.shift");
    private final Boolean reqPerms = ConfigManager.Loader.getConfig(ConfigManager.Type.KeyMap).getBoolean("drop.perms.enable");
    private final String permsName = ConfigManager.Loader.getConfig(ConfigManager.Type.KeyMap).getString("drop.perms.name");
    private final List<ActionManager.Action> actions = ActionManager.convertAction(ConfigManager.Loader.getConfig(ConfigManager.Type.KeyMap).getStringList("drop.actions"));

    public PlayerDropItemListener() {
        if (enable) {
            Bukkit.getPluginManager().registerEvents(this, plugin);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {

        Player player = event.getPlayer();

        if (player.isSneaking() != shift) {
            return;
        }

        if (reqPerms && permsName != null && !player.hasPermission(permsName)) {
            return;
        }

        event.setCancelled(cancel);

        ActionManager.executeActions(player, actions);

    }

}

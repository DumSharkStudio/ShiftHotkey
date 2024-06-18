package com.sharkylab.sharkycraft.shifthotkey.listener;

import com.sharkylab.sharkycraft.shifthotkey.ShiftHotkey;
import com.sharkylab.sharkycraft.shifthotkey.manager.ActionManager;
import com.sharkylab.sharkycraft.shifthotkey.manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class PlayerSwapItemListener implements Listener {

    private final Plugin plugin = ShiftHotkey.getPlugin(ShiftHotkey.class);
    private final Boolean enable = ConfigManager.Loader.getConfig(ConfigManager.Type.KeyMap).getBoolean("swap.enable");
    private final Boolean cancel = ConfigManager.Loader.getConfig(ConfigManager.Type.KeyMap).getBoolean("swap.cancel");
    private final Boolean shift = ConfigManager.Loader.getConfig(ConfigManager.Type.KeyMap).getBoolean("swap.shift");
    private final Boolean reqPerms = ConfigManager.Loader.getConfig(ConfigManager.Type.KeyMap).getBoolean("swap.perms.enable");
    private final String permsName = ConfigManager.Loader.getConfig(ConfigManager.Type.KeyMap).getString("swap.perms.name");
    private final List<ActionManager.Action> actions = ActionManager.convertAction(ConfigManager.Loader.getConfig(ConfigManager.Type.KeyMap).getStringList("swap.actions"));

    public PlayerSwapItemListener() {
        if (enable) {
            Bukkit.getPluginManager().registerEvents(this, plugin);
        }
    }

    @EventHandler
    public void onPlayerSwapItem(PlayerSwapHandItemsEvent event) {

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

package krmcplugins.kokored.website.shifthotkey.hotkey;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.Plugin;

import krmcplugins.kokored.website.shifthotkey.ShiftHotkey;
import krmcplugins.kokored.website.shifthotkey.util.Cooldown;

public class ShiftQ implements Listener {

    Plugin plugin = ShiftHotkey.getPlugin(ShiftHotkey.class);

    public ShiftQ() {
        if (!(plugin.getConfig().getBoolean("hotkeys.shift-q.enable"))) {
            return;
        }
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (plugin.getConfig().getBoolean("hotkeys.shift-q.shift") == true) {
            if (!(player.isSneaking())) {
                return;
            }
        }
        
        event.setCancelled(plugin.getConfig().getBoolean("hotkeys.shift-q.cancel-event"));

        event.setCancelled(true);

        if (Cooldown.isCooldown(player) != 0) {
            Cooldown.sendError(player);
            return;
        }

        List<String> commands = plugin.getConfig().getStringList("hotkeys.shift-q.commands");
        Cooldown.add(player);

        for (String command : commands) {
            Bukkit.dispatchCommand(player, command);
        }
    }
    
}

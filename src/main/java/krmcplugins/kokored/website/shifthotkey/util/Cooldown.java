package krmcplugins.kokored.website.shifthotkey.util;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import krmcplugins.kokored.website.shifthotkey.ShiftHotkey;

public class Cooldown {
    
    private static HashMap<UUID, Long> coolDown = new HashMap<UUID, Long>();
    private static Plugin plugin = ShiftHotkey.getPlugin(ShiftHotkey.class);

    public static Integer isCooldown(Player player) {
        if (coolDown.containsKey(player.getUniqueId())) {
            Long interval = System.currentTimeMillis() - coolDown.get(player.getUniqueId());
            Integer cdTime = plugin.getConfig().getInt("cooldown.time");
            if (interval <= 1000 * cdTime) {
                return (cdTime - (int) (interval / 1000));
            }else {
                coolDown.remove(player.getUniqueId());
                return 0;
            }
        }
        return 0;
    }

    public static void add(Player player) {
        coolDown.put(player.getUniqueId(), System.currentTimeMillis());
    }

    public static void sendError(Player player) {
        String message = ChatColor.translateAlternateColorCodes('&', 
        plugin.getConfig().getString("cooldown.message")
            .replace("%player%", player.getName())
            .replace("%cdtime%", String.valueOf(isCooldown(player))));

        player.sendMessage(message);
    }
    
}

package com.sharkylab.sharkycraft.shifthotkey.manager;

import com.sharkylab.sharkycraft.shifthotkey.ShiftHotkey;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActionManager {

    private static Plugin plugin = ShiftHotkey.getPlugin(ShiftHotkey.class);

    public static List<Action> convertAction(List<String> actions) {
        List<Action> actionList = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        for (String s : actions) {

            Matcher matcher = pattern.matcher(s);
            if (!matcher.find()) {
                plugin.getLogger().log(Level.WARNING, "An action \"" + s + "\" could not be converted: Missing action key.");
                continue;
            }

            Type actionType;
            try {
                actionType = Type.valueOf(matcher.group(1).toUpperCase());
            } catch (IllegalArgumentException e) {
                plugin.getLogger().log(Level.WARNING, "An action \"" + s + "\" could not be converted: Unknown action key.");
                continue;
            }

            if (s.replace(matcher.group(1), "").replace(" ", "").isEmpty()) {
                plugin.getLogger().log(Level.WARNING, "An action \"" + s + "\" could not be converted: Missing actions.");
                continue;
            }

            s = s.replace(matcher.group(1), "");
            s = s.substring(3);
            actionList.add(new Action(actionType, s));
        }

        return actionList;
    }

    public static void executeActions(Player player, List<Action> actions) {
        for (Action action : actions) {
            String actionString = action.action;
            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                actionString = PlaceholderAPI.setPlaceholders(player, actionString);
            }
            if (action.type == Type.COMMAND) {
                Bukkit.dispatchCommand(player, actionString);
            }
            if (action.type == Type.CONSOLE) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), actionString);
            }
            if (action.type == Type.CHAT) {
                player.chat(actionString);
            }
        }
    }

    public enum Type {
        COMMAND,
        CONSOLE,
        CHAT
    }

    public static class Action {

        private Type type;
        private String action;

        public Action(Type type, String action) {
            this.type = type;
            this.action = action;
        }

    }

}

package krmcplugins.kokored.website.shifthotkey.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import krmcplugins.kokored.website.shifthotkey.ShiftHotkey;

public class Config {

    private static ShiftHotkey shiftHotkey;
    private static Plugin plugin = ShiftHotkey.getPlugin(ShiftHotkey.class);

    public Config(ShiftHotkey shkey) {
        Config.shiftHotkey = shkey;
    }

    public static void check() {
        if (!(shiftHotkey.getVersion().equals(plugin.getConfig().getString("version")))) {
            setNew();
        }
    }

    private static void setNew() {
        try {
            if (!new File(plugin.getDataFolder() + "/config.yml").exists()) {
                plugin.getConfig().options().copyDefaults(true);
                plugin.saveDefaultConfig();
            }

            File configFile = new File(plugin.getDataFolder() + "/config.yml");
            YamlConfiguration externalYamlConfig = YamlConfiguration.loadConfiguration(configFile);

            InputStreamReader internalConfigFileStream = new InputStreamReader(plugin.getResource("config.yml"),
                    StandardCharsets.UTF_8);
            YamlConfiguration internalYamlConfig = YamlConfiguration.loadConfiguration(internalConfigFileStream);

            for (String string : internalYamlConfig.getKeys(true)) {
                if (!externalYamlConfig.contains(string)) {
                    externalYamlConfig.set(string, internalYamlConfig.get(string));
                }
            }

            try {
                externalYamlConfig.save(configFile);
            } catch (IOException io) {
                io.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.company;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class ConfigManager {

    private File file;
    private YamlConfiguration config;
    public ConfigManager(){
        load();
    }

    private void load(){
        Plugin.getIstance().saveDefaultConfig();
        file = new File(Plugin.getIstance().getDataFolder(), "config.yml");
        System.out.println("Config: " + file.getAbsolutePath());
        config = YamlConfiguration.loadConfiguration(file);
        System.out.println("Keys: " + config.getKeys(false));
    }
    public Set<String> getGroups(){
        return Objects.requireNonNull(config.getConfigurationSection("groups")).getKeys(false);
    }
    public String getMessage(String group){
        ConfigurationSection configurationSection = config.getConfigurationSection("groups." + group);
        assert configurationSection != null;
        return configurationSection.getString("message");
    }

}

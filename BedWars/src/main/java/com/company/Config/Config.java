package com.company.Config;

import com.company.BedWars;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public abstract class Config {

    protected File file;
    protected YamlConfiguration config;

    public abstract void load(BedWars bw);

}

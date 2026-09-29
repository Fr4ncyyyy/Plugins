package com.company;

import com.company.Arenas.ArenaManager;
import com.company.Config.ConfigManager;
import com.company.Listeners.*;
import com.company.Match.MatchManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class BedWars extends JavaPlugin {

    public NamespacedKey GENERATOR_KEY;

    public void onEnable(){

        getLogger().info("Plugin BedWars abilitato!");

        NPCRegistry registry = CitizensAPI.getNPCRegistry();

        for (NPC npc : registry) {
            npc.destroy();
        }



         GENERATOR_KEY = new NamespacedKey(this,"generator_id");

        ConfigManager configManager = new ConfigManager(this);
        configManager.loadAll();

        ArenaManager arenaManager = new ArenaManager(configManager.getArenaConfig()
                ,configManager.getShopConfig());
        arenaManager.loadAll();

        MatchManager matchManager = new MatchManager(this,configManager.getMatchConfig(),arenaManager);
        matchManager.loadMatches(
                configManager.getScoreboardConfig(),
                configManager.getGeneratorConfig(),
                GENERATOR_KEY);

        Objects.requireNonNull(getServer()
                .getPluginCommand("bw")).setExecutor(new CommandTest(matchManager,arenaManager));

        getServer().getPluginManager().registerEvents(new ChangeWorld(matchManager,
                arenaManager.getLobby(),configManager.getScoreboardConfig()),this);

        getServer().getPluginManager().registerEvents(
                new OnJoin(arenaManager.getLobby(),
                configManager.getArenaConfig(), configManager.getScoreboardConfig()),this);

        getServer().getPluginManager().registerEvents(new Damage(arenaManager.getLobby()),this);

        getServer().getPluginManager().registerEvents(new Disconnect(matchManager),this);

        getServer().getPluginManager().registerEvents(new Blocks(matchManager),this);

        matchManager.registerNPCEvent(this);

    }


}

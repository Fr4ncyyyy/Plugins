package com.company.Arenas;

import com.company.Color;
import com.company.Config.ArenaConfig;
import com.company.Config.ShopConfig;
import com.company.Lobby;
import com.company.Match.Team.Bed;
import com.company.Match.Team.Shop.ShopManager;
import com.company.Match.Team.Team;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ArenaManager {

    private final ArenaConfig arenaConfig;
    private final ShopConfig shopConfig;
    private Lobby lobby;
    private final ArrayList<Arena> arenas;

    public ArenaManager(ArenaConfig arenaConfig,ShopConfig shopConfig){
        this.arenaConfig = arenaConfig;
        this.shopConfig = shopConfig;
        arenas = new ArrayList<>();
    }

    public void loadAll(){

        Set<String> arenas = new HashSet<>(arenaConfig.getArenas());

        try{

            World lobbyWorld = arenaConfig.getWorldLobby();
            Location spawnLobby = arenaConfig.getSpawnLobby();
            lobby = new Lobby(lobbyWorld,spawnLobby);

            for(String id : arenas){

                World world = arenaConfig.getWorld(id);
                Location waitingSpawn = arenaConfig.getWaitingSpawn(id);
                Location spectatorSpawn = arenaConfig.getSpectatorSpawn(id);

                ArrayList<Team> teams = createTeams(id);

                addArena(new Arena(id,world,waitingSpawn,spectatorSpawn,teams));

                for(Team team : teams){
                    team.getShopManager().initNPCs(arenaConfig.getNormalNPCLocation(id,team.getColor())
                            ,arenaConfig.getUpgradeNPCLocation(id,team.getColor()));
                }

            }



        }catch(Exception exception){
            exception.printStackTrace();
        }

    }

    private ArrayList<Team> createTeams(String arenaId){

        Set<String> set = new HashSet<>(arenaConfig.getTeams(arenaId));
        ArrayList<Team> teams = new ArrayList<>();

        for(String s : set){

            int id = arenaConfig.getIdTeam(arenaId,s);
            Color color = Color.valueOf(s.toUpperCase());
            Location location = arenaConfig.getSpawnTeam(arenaId,color);

            teams.add(new Team(id,color,location,
                    new Bed(arenaId,color,new ItemStack(Material.RED_BED)),
                    new ShopManager(shopConfig)));
        }

        return teams;

    }

    public ArrayList<Arena> getArenas(){
        return arenas;
    }
    private void addArena(Arena arena){
        arenas.add(arena);
    }
    public Lobby getLobby(){
        return lobby;
    }


}

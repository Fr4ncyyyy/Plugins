package com.company.Match.Generators;

import com.company.BedWars;
import com.company.Config.GeneratorConfig;
import com.company.Match.Generators.CenterGenerators.DiamondGenerator;
import com.company.Match.Generators.CenterGenerators.Enums.DiamondPhases;
import com.company.Match.Generators.CenterGenerators.EmeraldGenerator;
import com.company.Match.Generators.CenterGenerators.Enums.EmeraldPhases;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.TextDisplay;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GeneratorManager {

    private final NamespacedKey GENERATOR_KEY;

    private final GeneratorConfig generatorConfig;
    private final ArrayList<Generator> generators;

    public GeneratorManager(GeneratorConfig generatorConfig, NamespacedKey GENERATOR_KEY){
        this.generatorConfig = generatorConfig;
        this.GENERATOR_KEY = GENERATOR_KEY;
        generators = new ArrayList<>();
    }

    public void loadAll(String arenaID,EmeraldPhases emeraldPhase,DiamondPhases diamondPhase){

        loadBaseGenerators(new HashSet<>(generatorConfig.getGeneratorBaseByID(arenaID)),arenaID);
        loadCenterGenerators(
                new HashSet<>(generatorConfig.getGeneratorCenterByID(arenaID)),
                arenaID,
                emeraldPhase,
                diamondPhase
        );

    }

    public void startAll(BedWars bw){

        for(Generator generator : generators){
            generator.start(bw);
        }

    }

    public void add(Generator generator){
        generators.add(generator);
    }

    private void loadBaseGenerators(Set<String> set,String arenaID){
        for(String s : set){

            Type type = generatorConfig.getBaseType(arenaID,Integer.parseInt(s));

            Material material = generatorConfig.getMaterial(type);
            int time = generatorConfig.getBaseTime(type);
            int amount = generatorConfig.getAmount(type);
            int max = generatorConfig.getMax(type);
            Location location = generatorConfig.getBaseLocation(arenaID,Integer.parseInt(s));

            add(new Generator(Integer.parseInt(s),GENERATOR_KEY,location,
                    material,time,amount,max));

        }
    }

    private void loadCenterGenerators(Set<String> set,String arenaID,
                                      EmeraldPhases emeraldPhase,
                                      DiamondPhases diamondPhase){

        for(String s : set){

            Type type = generatorConfig.getCenterType(arenaID,Integer.parseInt(s));
            Material material = generatorConfig.getMaterial(type);
            int amount = generatorConfig.getAmount(type);
            int max = generatorConfig.getMax(type);
            Location location = generatorConfig.getCenterLocation(arenaID,Integer.parseInt(s));


            World world = location.getWorld();
            TextDisplay hologram = createHologram(world,s,arenaID);
            if(type == Type.EMERALD){

                int time = generatorConfig.getEmeraldTime(type,emeraldPhase);

                add(new EmeraldGenerator(
                        Integer.parseInt(s),
                        GENERATOR_KEY,
                        location,
                        material,
                        time,
                        amount,
                        max,
                        hologram,
                        getTextHologram(Type.EMERALD,diamondPhase,emeraldPhase)
                ));
            }

            if(type == Type.DIAMOND){

                int time = generatorConfig.getDiamondTime(type,diamondPhase);

                add(new DiamondGenerator(
                        Integer.parseInt(s),
                        GENERATOR_KEY,
                        location,
                        material,
                        time,
                        amount,
                        max,
                        hologram,
                        getTextHologram(Type.DIAMOND,diamondPhase,emeraldPhase)
                ));

            }


        }
    }

    private TextDisplay createHologram(World world,String id,String arenaID){
        Location location = generatorConfig.getHologramLocation(id, arenaID);
        location.getChunk().load();

        return (TextDisplay) world.spawnEntity(
                location,
                org.bukkit.entity.EntityType.TEXT_DISPLAY
        );
    }

    private String getTextHologram(Type type,DiamondPhases diamondPhase,EmeraldPhases emeraldPhase){

        String hologram = generatorConfig.getTextHologram(type);

        String phase = switch (type) {
            case DIAMOND -> String.valueOf(diamondPhase);
            case EMERALD -> String.valueOf(emeraldPhase);
            default -> "";
        };

        return hologram.replace("%phase%", phase);
    }


}

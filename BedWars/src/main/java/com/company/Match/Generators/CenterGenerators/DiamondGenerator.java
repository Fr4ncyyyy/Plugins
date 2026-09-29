package com.company.Match.Generators.CenterGenerators;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.TextDisplay;

public class DiamondGenerator extends CenterGenerator {

    public DiamondGenerator(int id,
                            NamespacedKey GENERATOR_KEY,
                            Location location,
                            Material material,
                            int time,
                            int amount,
                            int max,
                            TextDisplay textDisplay,
                            String hologramFormat) {
        super(id, GENERATOR_KEY, location, material, time, amount, max,textDisplay,hologramFormat);

        name = "DIAMOND";
    }

}

package com.company.Match.Generators.CenterGenerators;
import com.company.BedWars;
import com.company.Match.Generators.Generator;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Display;
import org.bukkit.entity.TextDisplay;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class CenterGenerator extends Generator {

    protected String name;

    protected TextDisplay textDisplay;
    protected int countdown;
    private final BukkitRunnable countdownRunnable;
    private final String hologramFormat;

    public CenterGenerator(int id,
                           NamespacedKey GENERATOR_KEY,
                           Location location,
                           Material material,
                           int time,
                           int amount,
                           int max,
                           TextDisplay textDisplay,
                           String hologramFormat) {
        super(id, GENERATOR_KEY, location, material, time, amount, max);
        this.textDisplay = textDisplay;
        this.countdown = time;
        this.hologramFormat = hologramFormat;

        countdownRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                if(countdown > 0){
                    countdown--;
                }else {
                    countdown = time;
                }
                updateHologram(hologramFormat);
            }
        };

    }

    @Override
    public void start(BedWars bw){
        super.start(bw);

        countdownRunnable.runTaskTimer(
                bw,
                20L,
                20L
        );
    }

    public void setTextDisplay(String text){
        textDisplay.setText(text);
        textDisplay.setBillboard(Display.Billboard.CENTER);
        textDisplay.setBackgroundColor(Color.fromARGB(0, 0, 0, 0));
    }

    private void updateHologram(String string){
        setTextDisplay(string.replace("%time%",String.valueOf(countdown))
                .replace("%name%",name));
    }


}

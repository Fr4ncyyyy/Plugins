package com.company.Match.Team;

import com.company.Color;
import com.company.Config.BedConfig;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class Bed {

    public static BedConfig bedConfig = new BedConfig();

    private final Location location;
    private final Color color;
    private final ItemStack bed;
    private boolean destroyed;

    public Bed(String arenaID, Color color, ItemStack bed) {
        this.color = color;
        this.bed = bed;
        location = bedConfig.getBedLocation(arenaID, color);
        destroyed = false;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public Location getLocation() {
        return location;
    }
    public boolean isBedBlock(Location location) {

        Block configuredBlock = this.location.getBlock();

        if (!(configuredBlock.getBlockData() instanceof org.bukkit.block.data.type.Bed bedData)) {
            return configuredBlock.getLocation().equals(location);
        }

        Block otherPart;

        if (bedData.getPart() == org.bukkit.block.data.type.Bed.Part.HEAD) {
            otherPart = configuredBlock.getRelative(bedData.getFacing().getOppositeFace());
        } else {
            otherPart = configuredBlock.getRelative(bedData.getFacing());
        }

        return configuredBlock.getX() == location.getBlockX()
                && configuredBlock.getY() == location.getBlockY()
                && configuredBlock.getZ() == location.getBlockZ()

                || otherPart.getX() == location.getBlockX()
                && otherPart.getY() == location.getBlockY()
                && otherPart.getZ() == location.getBlockZ();
    }

}

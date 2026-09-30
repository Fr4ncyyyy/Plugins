package com.company.GUI;

import java.util.HashMap;
import java.util.UUID;

public class GUIManager {

    private final HashMap<UUID,GUI> openedGUIs;

    public GUIManager(){
        openedGUIs = new HashMap<>();
    }
    public void put(UUID uuid,GUI gui){
        openedGUIs.put(uuid,gui);
    }
    public void remove(UUID uuid){
        openedGUIs.remove(uuid);
    }
    public GUI getGUI(UUID uuid){
        return openedGUIs.get(uuid);
    }

}

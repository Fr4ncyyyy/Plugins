package com.company.GUI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {

    private final GUIManager guiManager;
    public GUIListener(GUIManager guiManager){
        this.guiManager = guiManager;
    }

    @EventHandler
    public void onClickGUI(InventoryClickEvent event) {

        Inventory inventory = event.getInventory();
        Entity entity = event.getWhoClicked();

        if(!(entity instanceof Player))return;
        GUI gui = guiManager.getGUI(entity.getUniqueId());
        if(gui == null)return;

        if(inventory.equals(gui.getInventory())){

            if(event.getRawSlot() >= inventory.getSize()){
                event.setCancelled(true);
                return;
            }

            event.setCancelled(true);
            GUI.StatusGUI status = gui.getStatusGUI();
            gui.handleClick((Player)entity,event.getRawSlot());

            if(status == GUI.StatusGUI.INVENTORY){

                ItemStack item = event.getCurrentItem();

                if(item != null){

                    if(entity.hasPermission("inventorybackupper.interact")){
                        ((Player) entity).getInventory().addItem(item.clone());
                    }

                }
            }
        }

    }

    @EventHandler
    public void onCloseGUI(InventoryCloseEvent event){

        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }

        GUI gui = guiManager.getGUI(player.getUniqueId());

        if (gui == null) {
            return;
        }

        if (!event.getInventory().equals(gui.getInventory())) {
            return;
        }

        guiManager.remove(event.getPlayer().getUniqueId());

    }

}

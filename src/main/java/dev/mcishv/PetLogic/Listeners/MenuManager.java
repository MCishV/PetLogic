package dev.mcishv.PetLogic.Listeners;

import dev.mcishv.PetLogic.PetLogic;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MenuManager implements Listener {

    private final PetLogic plugin;

    public MenuManager(PetLogic plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.isShiftClick()) return;
        if (event.getView().getTitle().equalsIgnoreCase("Улучшение питомцев")) {
            event.setCancelled(true);
            if (event.isLeftClick()) {
                if (!(event.getWhoClicked() instanceof Player)) return;
                Player player = (Player) event.getWhoClicked();
                //        if (event.getClickedInventory() == plugin.inventoryManager.get(player)) {
                ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem != null) {
                    if (clickedItem.getType() == Material.PLAYER_HEAD || clickedItem.getType() == Material.BARRIER) {
                        int size = plugin.petCommand.size;
                        int i = event.getSlot() + 1;
                        if (clickedItem.getType() == Material.BARRIER) {
                            plugin.petCommand.deleteAllPets(player);
                        } else if (i == size || i == size - 1 || i == size - 2) {
                            plugin.petCreator.CreateArmorStand(player, new String[]{"", (i - 3) + ""});
                        } else {
                            plugin.petCreator.CreateArmorStand(player, new String[]{"", (i) + ""});
                        }
                        player.closeInventory();
                    }
                }
            }
        }
    }
}

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

    private final String prefix = "§bPetLogic §7>§f ";

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
                            if(player.hasPermission(plugin.petCreator.get_obj_permission(String.valueOf(i - 3)))) {
                                if(plugin.equipped.get(player) != null) {
                                    if (!plugin.equipped.get(player).contains(String.valueOf(i - 3))) {
                                        plugin.petCreator.CreateArmorStand(player, new String[]{"", String.valueOf(i - 3)});
                                    } else {
                                        plugin.petDelete.deleteArmorStand(player, String.valueOf(i - 3));
                                    }
                                } else {
                                    plugin.petCreator.CreateArmorStand(player, new String[]{"", String.valueOf(i - 3)});
                                }
                            } else {
                                player.sendMessage(prefix + "У вас нет такого питомца. Открывайте кейсы чтобы заполучить его!");
                            }
                        } else {
                            if(player.hasPermission(plugin.petCreator.get_obj_permission(String.valueOf(i)))) {
                                if(plugin.equipped.get(player) != null) {
                                    if (!plugin.equipped.get(player).contains(String.valueOf(i))) {
                                        plugin.petCreator.CreateArmorStand(player, new String[]{"", String.valueOf(i)});
                                    } else {
                                        plugin.petDelete.deleteArmorStand(player, String.valueOf(i));
                                    }
                                } else {
                                    plugin.petCreator.CreateArmorStand(player, new String[]{"", String.valueOf(i)});
                                }
                            } else {
                                player.sendMessage(prefix + "У вас нет такого питомца. Открывайте кейсы чтобы заполучить его!");
                            }
                        }
                        player.closeInventory();
                    }
                }
            }
        }
    }
}

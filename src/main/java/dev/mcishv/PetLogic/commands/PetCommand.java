package dev.mcishv.PetLogic.commands;

import dev.mcishv.PetLogic.PetLogic;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

@Getter
public class PetCommand implements CommandExecutor {

    private final String prefix = "§bPetLogic §7>§f ";

    private final PetLogic plugin;

    public PetCommand(PetLogic plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("petlogic")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(prefix + "Только игроки могут использовать эту команду.");
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("petlogic.use")) {
                player.sendMessage(prefix + "§cУ вас нет прав на выполнения данной команды.");
                return true;
            }
            if (args.length <= 1) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("gui")) {
                        openPetMenu(player);
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("enable")) {
                        player.sendMessage("§e/pet enable <номер_текстуры> §7> Создать голову питомца. \n" + prefix + "§e/pet disable §7> Удалить голову питомца.");
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("disable")) {
                        if (plugin.playerCountAS.get(player) == null) {
                            player.sendMessage(prefix + "Питомцы не найдены");
                            return true;
                        } else if (plugin.playerCountAS.get(player) == 0) {
                            player.sendMessage(prefix + "Питомцы не найдены");
                            return true;
                        }
                        removeEntityAs(player);
                        plugin.playerCountAS.remove(player);
                        player.sendMessage(prefix + "Питомцы удалены");

                    }
                } else {
                    player.sendMessage(prefix + "§e/pet enable <номер_текстуры> §7> Создать голову питомца. \n" + prefix + "§e/pet disable §7> Удалить голову питомца.");
                }
                return true;
            } else if (args.length == 2) {
                try {
                    int value = Integer.parseInt(args[1]);
                    if (value < 1 || value > 5) {
                        player.sendMessage(prefix + "Можно выбрать текстуру только от 1 до 5");
                        return true;
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(prefix + "§e/pet enable <номер_текстуры> §7> Создать голову питомца. \n" + prefix + "§e/pet disable §7> Удалить голову питомца.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("enable")) {
                    plugin.petCreator.CreateArmorStand(player, args);
                    return true;
                }
            } else {
                player.sendMessage(prefix + "§e/pet enable <номер_текстуры> §7> Создать голову питомца. \n§e/pet disable §7> Удалить голову питомца.");
                return false;
            }
        }
        return false;
    }

    private void removePlayerFull(Player player) {
        plugin.playerCountAS.remove(player);
        removeEntityAs(player);
        clearLists(player);
    }

    private void removeEntityAs(Player player) {
        if (plugin.playerArmorStands.get(player) != null && !plugin.playerArmorStands.get(player).isDead())
            plugin.playerArmorStands.get(player).remove();
        if (plugin.playerArmorStands1.get(player) != null && !plugin.playerArmorStands1.get(player).isDead())
            plugin.playerArmorStands1.get(player).remove();
        if (plugin.playerArmorStands2.get(player) != null && !plugin.playerArmorStands2.get(player).isDead())
            plugin.playerArmorStands2.get(player).remove();
        if (plugin.playerArmorStands3.get(player) != null && !plugin.playerArmorStands3.get(player).isDead())
            plugin.playerArmorStands3.get(player).remove();
    }

    private void clearLists(Player player) {
        plugin.playerArmorStands.remove(player);
        plugin.playerArmorStands1.remove(player);
        plugin.playerArmorStands2.remove(player);
        plugin.playerArmorStands3.remove(player);
    }

    public void deleteAllPets(Player player) {
        removeEntityAs(player);
        plugin.playerCountAS.remove(player);
        player.sendMessage(prefix + "Питомцы удалены");
    }

    private int num;
    public int size = 18;
    private ItemStack petItem0 = new ItemStack(Material.PLAYER_HEAD);

    public void openPetMenu(Player player) {
        Inventory petMenu = Bukkit.createInventory(null, size, "Улучшение питомцев");
        
        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta barrierMeta = barrier.getItemMeta();
        barrierMeta.setDisplayName("§r§fУдалить всех питомцев");
        barrier.setItemMeta(barrierMeta);
        
        petMenu.setItem(size - 5, barrier);
        petMenu.setItem(size - 6, plugin.petCreator.get_obj_head("1"));
        petMenu.setItem(size - 4, plugin.petCreator.get_obj_head("2"));
        petMenu.setItem(size - 3, plugin.petCreator.get_obj_head("3"));
        petMenu.setItem(size - 2, plugin.petCreator.get_obj_head("4"));
        petMenu.setItem(size - 1, plugin.petCreator.get_obj_head("5"));
        
        plugin.inventoryManager.put(player, petMenu);
        player.openInventory(petMenu);
    }
}

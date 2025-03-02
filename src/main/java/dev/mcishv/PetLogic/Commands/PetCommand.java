package dev.mcishv.PetLogic.Commands;

import dev.mcishv.PetLogic.PetLogic;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

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
        if(plugin.playerArmorStands.get(player) != null && !plugin.playerArmorStands.get(player).isDead()) plugin.playerArmorStands.get(player).remove();
        if(plugin.playerArmorStands1.get(player) != null && !plugin.playerArmorStands1.get(player).isDead()) plugin.playerArmorStands1.get(player).remove();
        if(plugin.playerArmorStands2.get(player) != null && !plugin.playerArmorStands2.get(player).isDead()) plugin.playerArmorStands2.get(player).remove();
        if(plugin.playerArmorStands3.get(player) != null && !plugin.playerArmorStands3.get(player).isDead()) plugin.playerArmorStands3.get(player).remove();
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
    public int size = 36;
    private ItemStack petItem0 = new ItemStack(Material.PLAYER_HEAD);

    public void openPetMenu(Player player) {
        Inventory petMenu = Bukkit.createInventory(null, size, "Улучшение питомцев");
        for (int i = 0; i < size; i++) {
            num = i;
            if (i == size-5) {
                ItemStack barrier = new ItemStack(Material.BARRIER);
                ItemMeta barrierMeta = barrier.getItemMeta();
                barrierMeta.setDisplayName("§r§fУдалить всех питомцев");
                barrierMeta.setLore(Arrays.asList("", "§r§7Удалит сущности всех заспавненных питомцев", "§r§7Вы сможете вызвать их повторно в этом меню"));
                barrier.setItemMeta(barrierMeta);
                petMenu.setItem(i, barrier);
                continue;
            } else if (i == size-6 || i == size-4) {
                continue;
            } else if (i == size-3 || i == size-2 || i == size-1) {
                num = i - 3;
            }
            ItemStack petItem0 = plugin.petCreator.get_obj_head(String.valueOf(num + 1));

            petMenu.setItem(i, petItem0);
        }
        player.openInventory(petMenu);
    }
}

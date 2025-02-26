package dev.mcishv.PetLogic.commands;

import dev.mcishv.PetLogic.PetLogic;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import java.lang.reflect.Field;
import java.util.UUID;

@Getter
public class PetCommand implements CommandExecutor {

    private final PetLogic plugin;

    public PetCommand(PetLogic plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("petlogic")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Только игроки могут использовать эту команду.");
                return true;
            }

            Player player = (Player) sender;

            if (!player.hasPermission("petlogic.use")) {
                player.sendMessage("§cУ вас нет прав на выполнения данной команды.");
                return true;
            }

            if (args.length == 0) {
                player.sendMessage("§e/pet enable §7> Создать или удалить голову питомца.");
                return true;
            } else if (args.length == 1) {
                ArmorStand armorStand = plugin.playerArmorStands.get(player);
                if (args[0].equalsIgnoreCase("enable")) {
                    if (armorStand == null) {
                        createArmorStandForPlayer(player);
                        player.sendMessage("Питомец создан.");
                    } else {
                        player.sendMessage("Такой питомец уже создан.");
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("disable")) {
                    if (armorStand != null) {
                        removeArmorStandForPlayer(player, armorStand);
                        player.sendMessage("Питомец удален");
                    } else {
                        player.sendMessage("Питомец не заспавнен.");
                    }
                    return true;
                }
            } else {
                player.sendMessage("Используйте команду '/pet enable' для создания питомца.\nИспользуйте команду '/pet disable' для удаления питомца.");
                return false;
            }
        }
        return false;
    }


    private void createArmorStandForPlayer(Player player) {
        Location playerLocation = player.getLocation();
        ArmorStand armorStand = player.getWorld().spawn(playerLocation, ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setInvisible(true);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName("Test Fox");
        armorStand.setCanPickupItems(false);
        armorStand.setCollidable(false);
        armorStand.setInvulnerable(true);
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjRhMDM0NzQzNjQzNGViMTNkNTM3YjllYjZiNDViNmVmNGM1YTc4Zjg2ZTkxODYzZWY2MWQyYjhhNTNiODIifX19"));

        Field profileField;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        skull.setItemMeta(meta);

        armorStand.setHelmet(skull);
        plugin.playerArmorStands.put(player, armorStand);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || armorStand.isDead()) {
                    this.cancel();
                    return;
                }

                Location targetLocation = player.getLocation();
                armorStand.teleport(targetLocation);
            }
        }.runTaskTimer(plugin, 0L, 3L);
    }

    private void removeArmorStandForPlayer(Player player, ArmorStand armorStand) {
        if (armorStand != null && !armorStand.isDead()) {
            armorStand.remove();
        }
        plugin.playerArmorStands.remove(player);
    }
}

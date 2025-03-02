package dev.mcishv.PetLogic.Utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.mcishv.PetLogic.PetLogic;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.*;

public class PetCreator {

    private final String prefix = "§bPetLogic §7>§f ";

    private final PetLogic plugin;

    public PetCreator(PetLogic plugin) {
        this.plugin = plugin;
    }

    public void CreateArmorStand(Player player, String[] args) {
        Location playerLocation = player.getLocation();
        if (player.hasPermission("petlogic.have")) {
            if (plugin.playerCountAS.get(player) == null || plugin.playerCountAS.get(player) == 0 || plugin.equipped.get(player).get(0).equals("1000000")) {
                ArmorStand armorStand = player.getWorld().spawn(playerLocation, ArmorStand.class);
                createArmorStandForPlayer(player, args, armorStand, plugin.playerArmorStands, plugin.playerCountAS, 180);
                List<String> list = new ArrayList<>();
                list.add(0, args[1]);
                plugin.equipped.put(player, list);
                player.sendMessage(prefix + "Питомец создан.");
            } else if (plugin.playerCountAS.get(player) == 1 || plugin.equipped.get(player).get(1).equals("1000000")) {
                ArmorStand armorStand = player.getWorld().spawn(playerLocation, ArmorStand.class);
                createArmorStandForPlayer(player, args, armorStand, plugin.playerArmorStands1, plugin.playerCountAS, 0);
                player.sendMessage(prefix + "Питомец создан.");
                List<String> ints = plugin.equipped.get(player);
                ints.add(1, args[1]);
                plugin.equipped.put(player, ints);
            } else if (plugin.playerCountAS.get(player) == 2 || plugin.equipped.get(player).get(2).equals("1000000")) {
                if (player.hasPermission("petlogic.donate")) {
                    ArmorStand armorStand = player.getWorld().spawn(playerLocation, ArmorStand.class);
                    createArmorStandForPlayer(player, args, armorStand, plugin.playerArmorStands2, plugin.playerCountAS, 240);
                    List<String> ints = plugin.equipped.get(player);
                    ints.add(2, args[1]);
                    plugin.equipped.put(player, ints);
                    player.sendMessage(prefix + "Питомец создан.");
                } else {
                    player.sendMessage(prefix + "Вы достигли максимума по количеству питомцев. Вы можете расширить лимит до 4 купив привелегию.");
                }

            } else if (plugin.playerCountAS.get(player) == 3 || plugin.equipped.get(player).get(3).equals("1000000")) {
                if (player.hasPermission("petlogic.donate")) {
                    ArmorStand armorStand = player.getWorld().spawn(playerLocation, ArmorStand.class);
                    createArmorStandForPlayer(player, args, armorStand, plugin.playerArmorStands3, plugin.playerCountAS, 300);
                    List<String> ints = plugin.equipped.get(player);
                    ints.add(3, args[1]);
                    plugin.equipped.put(player, ints);
                    player.sendMessage(prefix + "Питомец создан.");
                } else {
                    player.sendMessage(prefix + "Вы достигли максимума по количеству питомцев. Вы можете расширить лимит до 4 купив привелегию.");
                }
            } else {
                player.sendMessage(prefix + "Вы достигли максимума по количеству питомцев.");
            }
        }
    }
    private void createArmorStandForPlayer(Player player, String[] args, ArmorStand armorStand, Map<Player, ArmorStand> playerArmorStandMap, Map<Player, Integer> playerCountAS, Integer addAngle) {
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setInvisible(true);
        armorStand.setCustomNameVisible(true);
        armorStand.setCanPickupItems(false);
        armorStand.setCollidable(false);
        armorStand.setInvulnerable(true);

        armorStand.setCustomName(get_obj_name(args[1]));
        armorStand.setHelmet(get_obj_head(args[1]));
        int how_many = 0;

        if (playerCountAS.get(player) != null) {
            how_many = playerCountAS.get(player);
        }
        playerCountAS.remove(player);
        playerCountAS.put(player, how_many+1);
        playerArmorStandMap.put(player, armorStand);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || armorStand.isDead()) {
                    armorStand.remove();
                    //removePlayerFull(player);
                    this.cancel();
                    return;
                }
                Location playerLocation = player.getLocation();
                double angle = Math.toRadians(playerLocation.getYaw() + addAngle);
                double xOffset = Math.cos(angle);
                double zOffset = Math.sin(angle);
                Location armorStandLocation = playerLocation.clone().add(xOffset, 0, zOffset);
                armorStand.teleport(armorStandLocation);
            }
        }.runTaskTimer(plugin, 0L, 3L);
    }

    private String[] getTexture(String texture) {
        String customName = "Error";
        String get_head = "1";
        String permission = "petlogic.have";

        switch (texture) {
            case "1":
                customName = "Лиса";
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjRhMDM0NzQzNjQzNGViMTNkNTM3YjllYjZiNDViNmVmNGM1YTc4Zjg2ZTkxODYzZWY2MWQyYjhhNTNiODIifX19";
                permission = "petlogic.1";
                break;
            case "2":
                customName = "Курица";
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWJlODEyOGFiMzZjNWQ2YzQ1NjNhOGI0YTRiNTE2NGMxNTVmMmYyNjAyNzUzN2IzZGMyM2YyMmEzNjk1NDQ5In19fQ==";
                permission = "petlogic.2";
                break;
            case "3":
                customName = "Енот";
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjYzYzhlMWNmM2U4OTA1MDczZGNiZTE4YTQ4ZThkN2I5NWUxYWU1ZmM1OGQ3OTM2ZWQ2N2Y3MjhiYjVhMzFjIn19fQ==";
                permission = "petlogic.3";
                break;
            case "4":
                customName = "Мышь";
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmRjMWE2ODc0ZTNmZjJiOTExZWRhZTllNTNlMjE4ZmU4ZDE1ZjY5MTY4ZWE2YWY5Yjk1ODVlMmNjZjIyOGY3In19fQ==";
                permission = "petlogic.4";
                break;
            case "5":
                customName = "Сова";
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzNiZGU0MzExMWY2OWE3ZmRhNmVjNmZhZjIyNjNjODI3OTYxZjM5MGQ3YzYxNjNlZDEyMzEwMzVkMWIwYjkifX19";
                permission = "petlogic.5";
                break;
            case "6":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWE5MTJlYjA2NGExZDg4MTcxMzI3YmNhNThlY2RkNTQ3ZWZiYzE4Mzk4Y2UxNWMyMDlhZTE5NTVhYjVlN2ZkOSJ9fX0=";
                customName = "Собака";
                permission = "petlogic.6";
                break;
            case "7":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWQ2YzZlZGE5NDJmN2Y1ZjcxYzMxNjFjNzMwNmY0YWVkMzA3ZDgyODk1ZjlkMmIwN2FiNDUyNTcxOGVkYzUifX19";
                customName = "Корова";
                permission = "petlogic.7";
                break;
            case "8":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2ExMjE4ODI1ODYwMWJjYjdmNzZlM2UyNDg5NTU1YTI2YzBkNzZlNmVmZWMyZmQ5NjZjYTM3MmI2ZGRlMDAifX19";
                customName = "Кошка";
                permission = "petlogic.8";
                break;
            case "9":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjMxZjljY2M2YjNlMzJlY2YxM2I4YTExYWMyOWNkMzNkMThjOTVmYzczZGI4YTY2YzVkNjU3Y2NiOGJlNzAifX19    ";
                customName = "Овца";
                permission = "petlogic.9";
                break;
            case "10":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDhjZGQ0ZjI4NTYzMmMyNWQ3NjJlY2UyNWY0MTkzYjk2NmMyNjQxYjE1ZDliZGJjMGExMTMwMjNkZTc2YWIifX19";
                customName = "Панда";
                permission = "petlogic.10";
                break;
            case "11":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTlhYzE2ZjI5NmI0NjFkMDVlYTA3ODVkNDc3MDMzZTUyNzM1OGI0ZjMwYzI2NmFhMDJmMDIwMTU3ZmZjYTczNiJ9fX0=";
                customName = "Пчела";
                permission = "petlogic.11";
                break;
            case "12":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjFlYmM3YWFkNWE2NTZkNTg0MmQ0ODExNjdiNWI3Yjk4ZWFmOWQ5MjRjMmRiYjkzNDhhMzEyMDMzMzAyNjMifX19";
                customName = "Лягушка";
                permission = "petlogic.12";
                break;
            case "13":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWMzNzgzMmM0MTM1MjU1NTU1M2Q4NmFhYWUxMzQxOTMzMWUzYWRlOTk3YmNlMGI4MTEzN2Q1MjA0ZTRjZGU3ZSJ9fX0=";
                customName = "Солнце";
                permission = "petlogic.13";
                break;
            case "14":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGZiYTVkYmVlMmE3MzJkMDUzNjE4YTM5OThmOTg2YzhmNTZkYjQwZmUzMTRhMGNlMDI4NTAxZjVlNTRiNzNkMyJ9fX0=";
                customName = "Луна";
                permission = "petlogic.14";
                break;
            case "15":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19";
                customName = "Земля";
                permission = "petlogic.15";
                break;
            case "16":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19";
                customName = "Земля";
                permission = "petlogic.16";
                break;
            case "17":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19";
                customName = "Земля";
                permission = "petlogic.17";
                break;
            case "18":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19";
                customName = "Земля";
                permission = "petlogic.18";
                break;
            case "19":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19";
                customName = "Земля";
                permission = "petlogic.19";
                break;
            case "20":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19";
                customName = "Земля";
                permission = "petlogic.20";
                break;
            case "21":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19";
                customName = "Земля";
                permission = "petlogic.21";
                break;
            case "22":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19";
                customName = "Земля";
                permission = "petlogic.22";
                break;
            case "23":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19";
                customName = "Земля";
                permission = "petlogic.23";
                break;
            case "24":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19";
                customName = "Земля";
                permission = "petlogic.24";
                break;
            case "25":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19";
                customName = "Земля";
                permission = "petlogic.25";
                break;
            case "26":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19";
                customName = "Земля";
                permission = "petlogic.26";
                break;
            case "27":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19";
                customName = "Земля";
                permission = "petlogic.27";
                break;
            case "28":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19";
                customName = "Земля";
                permission = "petlogic.28";
                break;
            case "29":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19";
                customName = "Земля";
                permission = "petlogic.29";
                break;
            case "30":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19";
                customName = "Земля";
                permission = "petlogic.30";
                break;
            case "31":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19";
                customName = "Земля";
                permission = "petlogic.31";
                break;
            case "32":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19";
                customName = "Земля";
                permission = "petlogic.32";
                break;
            case "33":
                get_head = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZTFkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19";
                customName = "Земля";
                permission = "petlogic.33";
                break;
        }
        return new String[]{"§r§f" + customName, get_head, permission};
    }

    public ItemStack get_obj_head(String id) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        String[] gt = getTexture(id);
        String get_head = gt[1];
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", get_head));

        Field profileField;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}
        meta.setDisplayName(gt[0]);
        meta.setLore(get_lore());
        skull.setItemMeta(meta);
        return skull;
    }
    
    public String get_obj_name(String id) {
        return getTexture(id)[0];
    }

    public String get_obj_permission(String id) {
        return getTexture(id)[2];
    }

    private List<String> get_lore() {
        String first = "§r§fРедкость ★★★⯪☆"; // + rare;
        String second = "§r§fБонус 1.02х"; // + bonus;
        String third = "§r§fУлучшить - 100$"; // + upgradecost;
        return Arrays.asList(first, second, third);
    }
}

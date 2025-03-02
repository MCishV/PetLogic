package dev.mcishv.PetLogic.Utils;

import dev.mcishv.PetLogic.PetLogic;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.Map;

public class PetDelete {
    private final String prefix = "";

    private final PetLogic plugin;

    public PetDelete(PetLogic plugin) {
        this.plugin = plugin;
    }
    public void deleteArmorStand(Player player, String id) {
        Map<Player, ArmorStand> targetMap = getMapById(id, player);
        if (targetMap == null) return;
        if (!targetMap.containsKey(player)) {
            return;
        }
        if (targetMap.get(player) != null) {
            targetMap.get(player).remove();
        }
        targetMap.remove(player);
        plugin.equipped.get(player).remove(id);
    }
    private Map<Player, ArmorStand> getMapById(String id, Player player) {
        int index = plugin.equipped.get(player).indexOf(id);
        return switch(index) {
            case 1 -> plugin.playerArmorStands;
            case 2 -> plugin.playerArmorStands1;
            case 3 -> plugin.playerArmorStands2;
            case 4 -> plugin.playerArmorStands3;
            default -> null;
        };
    }
}

package dev.mcishv.PetLogic.Utils;

import dev.mcishv.PetLogic.PetLogic;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.List;

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
            if (plugin.playerCountAS.get(player) != null) {
                plugin.playerCountAS.compute(player, (k, count) -> count - 1);
            }
        }
        if (plugin.playerCountAS.get(player) == 0) {
            plugin.equipped.remove(player);
        } else {
            int ind = plugin.equipped.get(player).indexOf(id);
            List<String> l = plugin.equipped.get(player);
            l.add(ind, "1000000");
            plugin.equipped.put(player, l);
        }
        targetMap.remove(player);
    }
    private Map<Player, ArmorStand> getMapById(String id, Player player) {
        int index = plugin.equipped.get(player).indexOf(id) + 1;
        return switch(index) {
            case 1 -> plugin.playerArmorStands;
            case 2 -> plugin.playerArmorStands1;
            case 3 -> plugin.playerArmorStands2;
            case 4 -> plugin.playerArmorStands3;
            default -> null;
        };
    }
}

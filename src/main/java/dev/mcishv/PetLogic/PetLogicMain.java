package dev.mcishv.PetLogic;
 
import dev.mcishv.PetLogic.commands.enablepet1;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class PetLogicMain extends JavaPlugin {

    @Getter
    private static PetLogicMain instance;

    private final Map<Player, ArmorStand> playerArmorStands = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        logPluginInfo();
        registerCommands();
    }

    @Override
    public void onDisable() {
        playerArmorStands.values().forEach(armorStand -> {
            if (armorStand != null && !armorStand.isDead()) {
                armorStand.remove();
            }
        });
        playerArmorStands.clear();
        Bukkit.getLogger().info("PetLogic отключен.");
    }

    private void logPluginInfo() {
        Bukkit.getLogger().info("\n=-=-=-=-=-=-=-=-=-=-=-=\n" +
                "       PetLogic\n" +
                "Version: " + getDescription().getVersion() + "\n" +
                "Author: " + String.join(", ", getDescription().getAuthors()) + "\n" +
                "Server Version: " + Bukkit.getServer().getVersion() + "\n" +
                "=-=-=-=-=-=-=-=-=-=-=-=\n" +
                "PetLogic включен " + getDescription().getVersion());
    }

    private void registerCommands() {
        getCommand("enablepet1").setExecutor(new enablepet1());
    }
}

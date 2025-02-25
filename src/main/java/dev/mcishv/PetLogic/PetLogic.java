package dev.mcishv.PetLogic;

import dev.mcishv.PetLogic.commands.PetCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PetLogic extends JavaPlugin {

    private static PetLogic instance;
    private Logger logger;

    public final Map<Player, ArmorStand> playerArmorStands = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();

        logger.info("\n=-=-=-=-=-=-=-=-=-=-=-=\n" +
                "       PetLogic\n" +
                "Version: " + getDescription().getVersion() + "\n" +
                "Author: " + String.join(", ", getDescription().getAuthors()) + "\n" +
                "Server Version: " + Bukkit.getServer().getVersion() + "\n" +
                "=-=-=-=-=-=-=-=-=-=-=-=\n" +
                "PetLogic включен " + getDescription().getVersion());

        getCommand("petlogic").setExecutor(new PetCommand(this));
    }

    @Override
    public void onDisable() {
        for (ArmorStand armorStand : playerArmorStands.values()) {
            if (armorStand != null && !armorStand.isDead()) {
                armorStand.remove();
            }
        }
        playerArmorStands.clear();

        logger = null;
    }
}
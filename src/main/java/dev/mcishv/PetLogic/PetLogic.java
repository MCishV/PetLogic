package dev.mcishv.PetLogic;

import dev.mcishv.PetLogic.commands.PetCommand;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PetLogic extends JavaPlugin {
    @Getter
    private static PetLogic instance;
    @Getter
    private static Logger jlogger;

    public final Map<Player, Boolean> playerArmorBool = new HashMap<>();
    public final Map<Player, ArmorStand> playerArmorStands = new HashMap<>();
    public final Map<Player, Boolean> playerArmorBool1 = new HashMap<>();
    public final Map<Player, ArmorStand> playerArmorStands1 = new HashMap<>();
    public final Map<Player, Boolean> playerArmorBool2 = new HashMap<>();
    public final Map<Player, ArmorStand> playerArmorStands2 = new HashMap<>();
    public final Map<Player, Boolean> playerArmorBool3 = new HashMap<>();
    public final Map<Player, ArmorStand> playerArmorStands3 = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        jlogger = super.getLogger();

        jlogger.info("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n" +
                "                PetLogic\n" +
                "Version: " + getDescription().getVersion() + "\n" +
                "Author: " + String.join(", ", getDescription().getAuthors()) + "\n" +
                "             Server Version\n" + Bukkit.getServer().getVersion() + "\n" +
                "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n" +
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

        jlogger = null;
    }
}
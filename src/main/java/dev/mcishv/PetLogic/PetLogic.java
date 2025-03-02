package dev.mcishv.PetLogic;

import dev.mcishv.PetLogic.Commands.PetCommand;
import dev.mcishv.PetLogic.Managers.EconomyManager;
import dev.mcishv.PetLogic.Listeners.MenuManager;
import dev.mcishv.PetLogic.Utils.PetCreator;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class PetLogic extends JavaPlugin {
    public PetCreator petCreator;
    public PetCommand petCommand;
    private static Economy econ = null;
    public EconomyManager economyManager;
    public MenuManager menuManager;
    private FileConfiguration languageConfig;
    @Getter
    private static PetLogic instance;
    @Getter
    public static Logger jlogger;

    public final Map<Player, Integer> playerCountAS = new HashMap<>();
    public final Map<Player, ArmorStand> playerArmorStands = new HashMap<>();
    public final Map<Player, ArmorStand> playerArmorStands1 = new HashMap<>();
    public final Map<Player, ArmorStand> playerArmorStands2 = new HashMap<>();
    public final Map<Player, ArmorStand> playerArmorStands3 = new HashMap<>();
    public final Map<Player, List<String>> equipped = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        jlogger = super.getLogger();
        if (!setupEconomy()) {
            getLogger().severe("Vault не найден! Плагин будет отключен.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        jlogger.info("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        jlogger.info("                PetLogic");
        jlogger.info("Version: " + getDescription().getVersion());
        jlogger.info("Author: " + String.join(", ", getDescription().getAuthors()));
        jlogger.info("Server Version: " + Bukkit.getServer().getVersion());
        jlogger.info("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        jlogger.info("§APetLogic " + getDescription().getVersion() + " enabled");

        economyManager = new EconomyManager(econ);
        petCreator = new PetCreator(this);
        petCommand = new PetCommand(this);
        getCommand("petlogic").setExecutor(new PetCommand(this));
        Bukkit.getServer().getPluginManager().registerEvents(new MenuManager(this), this);
    }

    @Override
    public void onDisable() {
        for (ArmorStand armorStand : playerArmorStands.values()) {
            if (armorStand != null && !armorStand.isDead()) {
                armorStand.remove();
            }
        }
        playerArmorStands.clear();
        for (ArmorStand armorStand : playerArmorStands1.values()) {
            if (armorStand != null && !armorStand.isDead()) {
                armorStand.remove();
            }
        }
        playerArmorStands1.clear();
        for (ArmorStand armorStand : playerArmorStands2.values()) {
            if (armorStand != null && !armorStand.isDead()) {
                armorStand.remove();
            }
        }
        playerArmorStands2.clear();
        for (ArmorStand armorStand : playerArmorStands3.values()) {
            if (armorStand != null && !armorStand.isDead()) {
                armorStand.remove();
            }
        }
        playerArmorStands3.clear();

        jlogger = null;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

}
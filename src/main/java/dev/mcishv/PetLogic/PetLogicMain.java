package dev.mcishv.PetLogic;
import dev.mcishv.PetLogic.commands.enablepet1;
import lombok.Getter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.bukkit.Bukkit;

public class PetLogicMain extends JavaPlugin {
    @Getter
    private static PetLogicMain instance;
    @Getter
    private static Logger jlogger;

    public Map<Player, ArmorStand> playerArmorStands = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        jlogger = super.getLogger();
        jlogger.info("=-=-=-=-=-=-=-=-=-=-=-=");
        jlogger.info("       PetLogic");
        jlogger.info("Version: " + getDescription().getVersion());
        jlogger.info("Author: " + getDescription().getAuthors());
        jlogger.info("     Server Version");
        jlogger.info(Bukkit.getServer().getVersion());
        jlogger.info("=-=-=-=-=-=-=-=-=-=-=-=-");
        jlogger.info("Successfully enabled PetLogic " + getDescription().getVersion());
        this.getCommand("enablepet1").setExecutor(new enablepet1());
    }
    @Override
    public void onDisable() {
    }
}
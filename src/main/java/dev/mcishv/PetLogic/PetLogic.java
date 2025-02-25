package dev.mcishv.PetLogic;

import dev.mcishv.PetLogic.commands.EnablePet1;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PetLogicMain extends JavaPlugin {

    private static PetLogicMain instance;
    private Logger logger;

    public final Map<Player, ArmorStand> playerArmorStands = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();

        logger.info("Загружаем плагин PetLogic");

        getCommand("EnablePet1").setExecutor(new EnablePet1(this));

        logger.info("Плагин PetLogic загружен.");
    }

    @Override
    public void onDisable() {
        for (ArmorStand armorStand : playerArmorStands.values()) {
            if (armorStand != null && !armorStand.isDead()) {
                armorStand.remove();
            }
        }
        playerArmorStands.clear();

        logger.info("Плагин PetLogic выключен.");
    }
}
package dev.mcishv.PetLogic.Managers;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

public class EconomyManager {

    private final Economy econ;

    public EconomyManager(Economy econ) {
        this.econ = econ;
    }

    private final String prefix = "§bPetLogic §7>§f ";

    public void deposit(Player player, double amount) {
        econ.depositPlayer(player, amount);
        player.sendMessage(prefix + "На счёт начислено "+ amount + " монет.");
    }

    public void withdraw(Player player, double amount) {
        econ.withdrawPlayer(player, amount);
        player.sendMessage(prefix + "Со счёта списано "+ amount + " монет.");
    }
}

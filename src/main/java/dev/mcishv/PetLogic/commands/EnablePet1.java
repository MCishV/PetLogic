package dev.mcishv.PetLogic.commands;

import dev.mcishv.PetLogic.PetLogicMain;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class EnablePet1 implements CommandExecutor {

    private final PetLogicMain plugin;

    public EnablePet1(PetLogicMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("enablepet1")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                ArmorStand armorStand = plugin.playerArmorStands.get(player);
                if (armorStand == null) {
                    createArmorStandForPlayer(player);
                    player.sendMessage("Голова создана");
                } else {
                    removeArmorStandForPlayer(player, armorStand);
                    player.sendMessage("Голова удалена");
                }
                return true;
            } else {
                sender.sendMessage("Эту команду можно использовать только игрокам.");
                return true;
            }
        }
        return false;
    }

    private void createArmorStandForPlayer(Player player) {
        Location playerLocation = player.getLocation();
        ArmorStand armorStand = player.getWorld().spawn(playerLocation, ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setInvisible(true);
        armorStand.setCustomNameVisible(true);
        armorStand.setCustomName("Test Pet");
        armorStand.setCanPickupItems(false);
        armorStand.setCollidable(false);
        armorStand.setInvulnerable(true);
        armorStand.setHelmet(new org.bukkit.inventory.ItemStack(Material.PLAYER_HEAD, 1));

        plugin.playerArmorStands.put(player, armorStand);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || armorStand.isDead()) {
                    this.cancel();
                    return;
                }

                Location targetLocation = player.getLocation();
                armorStand.teleport(targetLocation);
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    private void removeArmorStandForPlayer(Player player, ArmorStand armorStand) {
        if (armorStand != null && !armorStand.isDead()) {
            armorStand.remove();
        }
        plugin.playerArmorStands.remove(player);
    }
}

package dev.mcishv.PetLogic.commands;

import dev.mcishv.PetLogic.PetLogicMain;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class EnablePet1 implements CommandExecutor {

    @Getter
    private static PetLogicMain instance;
    private BukkitRunnable task;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("EnablePet1")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                ArmorStand armorStand = PetLogicMain.getInstance().playerArmorStands.get(player);
                if (armorStand == null) {
                    Location playerLocation = player.getLocation();
                    ArmorStand armorStand1 = player.getWorld().spawn(playerLocation, ArmorStand.class);
                    armorStand1.setVisible(false);
                    armorStand1.setGravity(false);
                    armorStand1.setInvisible(true);
                    armorStand1.setCustomNameVisible(true);
                    armorStand1.setCustomName("Test Pet");
                    armorStand1.setCanPickupItems(false);
                    armorStand1.setCollidable(false);
                    armorStand1.setInvulnerable(true);
                    armorStand1.setHelmet(new org.bukkit.inventory.ItemStack(Material.PLAYER_HEAD, 1, (short) 3));
                    PetLogicMain.getInstance().playerArmorStands.put(player, armorStand1);
                    task = new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (!player.isOnline() || armorStand1.isDead()) {
                                this.cancel();
                                return;
                            }

                            Location targetLocation = player.getLocation();
                            Location standLocation = armorStand1.getLocation();

                            // Перемещаем армор стенд к игроку
                            standLocation.setX(targetLocation.getX());
                            standLocation.setY(targetLocation.getY());
                            standLocation.setZ(targetLocation.getZ());
                            standLocation.setYaw(targetLocation.getYaw());
                            standLocation.setPitch(targetLocation.getPitch());
                            armorStand1.teleport(standLocation);
                        }
                    };
                    task.runTaskTimer(PetLogicMain.getInstance(), 0L, 1L);
                    player.sendMessage("Голова создана");
                    return true;
                } else {
                    task.cancel();
                    ArmorStand armorStandDelete = PetLogicMain.getInstance().playerArmorStands.get(player);
                    armorStandDelete.remove();
                    PetLogicMain.getInstance().playerArmorStands.remove(player);
                    player.sendMessage("Голова удалена");
                    return true;
                }
            } else {
                sender.sendMessage("Эту команду невозможно запустить с консоли/ркона.");
                return true;
            }
        }
        return true;
    }
}

package dev.mcishv.PetLogic.commands;

import dev.mcishv.PetLogic.PetLogicMain;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.bukkit.inventory.ItemStack;

public class enablepet1 implements CommandExecutor {

    private BukkitRunnable petTask;
    private static final ItemStack PET_HEAD = new ItemStack(Material.PLAYER_HEAD, 1);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Эту команду невозможно запустить с консоли/ркона.");
            return true;
        }

        Player player = (Player) sender;
        ArmorStand pet = PetLogicMain.getInstance().playerArmorStands.get(player);

        if (pet == null) {
            handlePetCreation(player);
        } else {
            handlePetRemoval(player);
        }

        return true;
    }

    private void handlePetCreation(Player player) {
        ArmorStand pet = createPetEntity(player);
        startPetMovementTask(player, pet);
        PetLogicMain.getInstance().playerArmorStands.put(player, pet);
        player.sendMessage("Голова создана");
    }

    private ArmorStand createPetEntity(Player player) {
        Location spawnLocation = player.getLocation();
        ArmorStand pet = player.getWorld().spawn(spawnLocation, ArmorStand.class);

        pet.setVisible(false);
        pet.setGravity(false);
        pet.setInvisible(true);
        pet.setCustomNameVisible(true);
        pet.setCustomName("Test Pet");
        pet.setCanPickupItems(false);
        pet.setCollidable(false);
        pet.setInvulnerable(true);
        pet.getEquipment().setHelmet(PET_HEAD);

        return pet;
    }

    private void startPetMovementTask(Player player, ArmorStand pet) {
        petTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || pet.isDead()) {
                    this.cancel();
                    return;
                }

                updatePetPosition(player, pet);
            }
        };
        petTask.runTaskTimer(PetLogicMain.getInstance(), 0L, 1L);
    }

    private void updatePetPosition(Player player, ArmorStand pet) {
        Location targetLocation = player.getLocation();
        Location petLocation = pet.getLocation();

        petLocation.setX(targetLocation.getX());
        petLocation.setY(targetLocation.getY());
        petLocation.setZ(targetLocation.getZ());
        pet.setRotation(targetLocation.getYaw(), targetLocation.getPitch());
        pet.teleport(petLocation);
    }

    private void handlePetRemoval(Player player) {
        petTask.cancel();
        ArmorStand pet = PetLogicMain.getInstance().playerArmorStands.remove(player);
        if (pet != null && !pet.isDead()) {
            pet.remove();
        }
        player.sendMessage("Голова удалена");
    }
}

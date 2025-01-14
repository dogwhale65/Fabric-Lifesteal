package dogwhale65.lifesteal.death;

import dogwhale65.lifesteal.command.ModCommandFunctions;
import dogwhale65.lifesteal.item.ModItems;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.BannedPlayerEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;

import java.util.Date;

public class DeathEventHandler {

    // Register events
    public static void register() {
        // Register the death event
        ServerLivingEntityEvents.AFTER_DEATH.register((livingEntity, damageSource) -> {

            if (livingEntity instanceof ServerPlayerEntity player) {
                handlePlayerDeath(player);

                if (player.getAttacker() instanceof ServerPlayerEntity attacker) {
                    if (attacker.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getValue() >= 40){
                        ItemStack item = new ItemStack(ModItems.HEART, 1);
                        attacker.giveItemStack(item);
                    } else {
                        attacker.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(attacker.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue() + 2);
                    }
                }
            }
        });

        // Register the respawn event
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> handleHealthAfterRespawn(oldPlayer.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue(), newPlayer));
    }

    // Handle death logic (banning if health is at 1 heart)
    private static void handlePlayerDeath(ServerPlayerEntity player) {
        var attribute = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (attribute == null) return;

        double currentMaxHealth = attribute.getBaseValue();

        if (currentMaxHealth <= 2.0) banPlayer(player);


        /*if (currentMaxHealth <= 2.0) {
            if (ModCommandFunctions.banType) {
                banPlayer(player);
            } else {
                // Transition to spectator mode and stop further death handling
                player.changeGameMode(GameMode.SPECTATOR);

                // Reset health and max health to safe values
                attribute.setBaseValue(2.0);
                player.setHealth(2.0F);

            }
        }*/
    }


    // Handle health modification after respawn (reduce health if necessary)
    private static void handleHealthAfterRespawn(Double beforeHealth, ServerPlayerEntity player) {
        double maxHealth = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getValue();
        if (maxHealth >= 2.0) {
            player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(beforeHealth - 2.0);
            double currentHealth = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue();
            player.setHealth((float) currentHealth);

        }
    }

    // Ban the player if they die with 1 heart
    private static void banPlayer(ServerPlayerEntity player) {
        MinecraftServer server = player.getServer();

        if (server != null) {

            BannedPlayerEntry banEntry = new BannedPlayerEntry(
                    player.getGameProfile(),
                    new Date(),
                    "Server",
                    null,
                    "§cYou have run out of hearts!"
            );

            server.getPlayerManager().getUserBanList().add(banEntry);

            player.networkHandler.disconnect(Text.literal("§cYou have run out of hearts!"));

            player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(6.0);
        }
    }
}

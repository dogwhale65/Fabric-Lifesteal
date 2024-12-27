package dogwhale65.lifesteal.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dogwhale65.lifesteal.screen.ReviveScreenHandler;
import dogwhale65.lifesteal.item.ModItems;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ModCommandFunctions {

    public static boolean banType = true;


    public static int withdraw(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        if (source.getPlayer() == null){
            return 0;
        }

        ServerPlayerEntity player = source.getPlayer();

        int amount = context.getArgument("amount", Integer.class);
        double heartValue = amount * 2;
        double playerMaxHealth = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getValue();

        if (amount < 20) {
            if (heartValue < playerMaxHealth) {

                player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(playerMaxHealth - heartValue);
                player.setHealth(player.getHealth());

                /*EntityAttributesS2CPacket packet = new EntityAttributesS2CPacket(player.getId(), attributes);

                player.networkHandler.sendPacket(packet);*/

                ItemStack item = new ItemStack(ModItems.HEART, amount);
                player.giveItemStack(item);
                source.sendFeedback(() -> Text.literal("§8[§a✓§8] You have withdrawn " + amount + " hearts!"), false);

                return 1;


            } else {
                source.sendFeedback(() -> Text.literal("§8[§4✕§8] Not enough hearts to withdraw."), false);
                return 0;
            }
        } else {
            source.sendFeedback(() -> Text.literal("§cYou do not have enough hearts to withdraw " + amount + "§c."), false);
            return 0;
        }
    }

    public static int revive(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayer();
        if (source.getPlayer() == null){
            return 0;
        }
        assert player != null;
        ReviveScreenHandler.ReviveScreen(player);




        return 1;
    }

    public static int spectatorDeathType(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        if (banType == true){
            banType = false;
            context.getSource().sendMessage(Text.literal("Deathtype set to spectator!"));
        } else {
            context.getSource().sendMessage(Text.literal("Deathtype is already set to spectator!"));
        }

        return 1;
    }
    public static int banDeathType(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if (banType == false){
            banType = true;
            context.getSource().sendMessage(Text.literal("Deathtype set to ban!"));
        } else {
            context.getSource().sendMessage(Text.literal("Deathtype is already set to ban!"));
        }


        return 1;
    }




}

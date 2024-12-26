package dogwhale65.lifesteal.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HeartItem extends Item {
    public HeartItem(Settings settings) {
        super(settings);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        // Add custom lore here
        tooltip.add(Text.literal("Right-Click to equip a heart!").formatted(Formatting.DARK_GRAY));
    }
    /*@Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();

        if (player != null && !context.getWorld().isClient) {
            // Increase the player's max health by 1 heart (2.0F)
            double playerHealth = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getValue();
            if (playerHealth < 40) {
                player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(playerHealth + 2);
                player.sendMessage(Text.literal("§8[§a✓§8] You have gained 1 heart!"));

            } else {
                player.sendMessage(Text.literal("§8[§4✕§8] You have reached the maximum amount of hearts!"));
                return ActionResult.FAIL;
            }


        }

        return ActionResult.SUCCESS; // Indicate that the action was successful
    }*/

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) { // Ensure this only happens on the server side
            // Increase the player's max health by 1 heart (2.0F)
            double playerHealth = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getValue();
            if (playerHealth < 40) {
                player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(playerHealth + 2);
                player.sendMessage(Text.literal("§8[§a✓§8] You have gained 1 heart!"));
                player.getStackInHand(hand).decrement(1);
            } else {
                player.sendMessage(Text.literal("§8[§4✕§8] You have reached the maximum amount of hearts!"));
            }
        }
        return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
    }
}

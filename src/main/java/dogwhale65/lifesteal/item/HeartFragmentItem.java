package dogwhale65.lifesteal.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HeartFragmentItem extends Item {
    public HeartFragmentItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        // Add custom lore here
        tooltip.add(Text.literal("Use 4 of these to craft a heart!").formatted(Formatting.DARK_GRAY));
    }
}

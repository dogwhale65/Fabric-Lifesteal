package dogwhale65.lifesteal.datagen;

import dogwhale65.lifesteal.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LIFE_BEACON, 1)
                .pattern("NHN")
                .pattern("HRH")
                .pattern("NHN")
                .input('N', Items.NETHERITE_BLOCK)
                .input('R', Items.RECOVERY_COMPASS)
                .input('H', ModItems.HEART)
                .criterion(hasItem(Items.RECOVERY_COMPASS), conditionsFromItem(Items.RECOVERY_COMPASS))
                .criterion(hasItem(ModItems.HEART), conditionsFromItem(ModItems.HEART))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.LIFE_BEACON)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.HEART, 1)
                .pattern("NHN")
                .pattern("HEH")
                .pattern("NHN")
                .input('E', Items.ENCHANTED_GOLDEN_APPLE)
                .input('N', Items.NETHER_STAR)
                .input('H', ModItems.HEART_FRAG)
                .criterion(hasItem(Items.ENCHANTED_GOLDEN_APPLE), conditionsFromItem(Items.ENCHANTED_GOLDEN_APPLE))
                .criterion(hasItem(ModItems.HEART_FRAG), conditionsFromItem(ModItems.HEART_FRAG))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.HEART)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.HEART_FRAG, 1)
                .pattern("DDD")
                .pattern("DTD")
                .pattern("DDD")
                .input('D', Items.DIAMOND_BLOCK)
                .input('T', Items.TOTEM_OF_UNDYING)
                .criterion(hasItem(Items.TOTEM_OF_UNDYING), conditionsFromItem(Items.TOTEM_OF_UNDYING))
                .criterion(hasItem(Items.DIAMOND_BLOCK), conditionsFromItem(Items.DIAMOND_BLOCK))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.HEART_FRAG)));
    }
}

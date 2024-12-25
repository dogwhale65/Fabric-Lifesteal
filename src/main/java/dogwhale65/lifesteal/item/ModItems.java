package dogwhale65.lifesteal.item;

import dogwhale65.lifesteal.LifestealFabric;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item HEART = registerItem("heart_item", new HeartItem(new Item.Settings()));
    public static final Item HEART_FRAG = registerItem("heart_fragment", new Item(new Item.Settings()));
    public static final Item LIFE_BEACON = registerItem("life_beacon", new LifeBeaconItem(new Item.Settings()));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(LifestealFabric.MOD_ID, name), item);
    }
    public static void registerModItems() {
        LifestealFabric.LOGGER.info("Registering Mod Items for " + LifestealFabric.MOD_ID);



        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(HEART);
            entries.add(HEART_FRAG);
            entries.add(LIFE_BEACON);
        });

    }



}

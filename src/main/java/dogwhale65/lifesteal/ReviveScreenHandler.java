package dogwhale65.lifesteal;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.screen.slot.SlotActionType;

import net.minecraft.server.BannedPlayerList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.Collection;


public class ReviveScreenHandler {

    public static void ReviveScreen(PlayerEntity player) {
        int rows = 6;
        int slots = rows * 9;
        SimpleInventory inventory = new SimpleInventory(slots);

        MinecraftServer server = player.getServer();
        if (server != null) {

            BannedPlayerList bannedPlayerList = server.getPlayerManager().getUserBanList();
            String[] bannedNamesArray = bannedPlayerList.getNames();

            Collection<String> bannedNames = Arrays.asList(bannedNamesArray);

            int index = 0;
            for (String playerName : bannedNames) {
                if (index >= slots) break;

                if (playerName == null) continue;

                ItemStack playerHead = new ItemStack(Items.PLAYER_HEAD);
                NbtCompound nbt = playerHead.getOrCreateNbt();
                nbt.putString("SkullOwner", playerName);

                playerHead.setCustomName(Text.literal(playerName));

                inventory.setStack(index++, playerHead);
            }
        }

        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                (syncId, inv, p) -> new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X6, syncId, inv, inventory, rows) {
                    @Override
                    public boolean canUse(PlayerEntity player) {
                        return true;
                    }

                    @Override
                    public ItemStack quickMove(PlayerEntity player, int index) {
                        return ItemStack.EMPTY;
                    }

                    @Override
                    public void onSlotClick(int slotId, int button, SlotActionType actionType, PlayerEntity playerEntity) {
                        if (actionType != SlotActionType.PICKUP) {
                            return;
                        }


                        ItemStack clickedItemStack = this.slots.get(slotId).getStack();

                        Text displayName = clickedItemStack.getName();


                        MinecraftServer server = player.getServer();
                        server.getUserCache().findByName(displayName.getString()).ifPresent(profile-> server.getPlayerManager().getUserBanList().remove(profile));

                        if (clickedItemStack.getItem() == Items.PLAYER_HEAD) {
                            if (playerEntity instanceof ServerPlayerEntity serverPlayer) {
                                serverPlayer.closeHandledScreen();
                                player.sendMessage(Text.literal("§aSuccessfully revived " + displayName.getString() + "§a!"), false);
                            }
                        }


                    }
                },
                Text.literal("Banned Players"))
        );
    }
}

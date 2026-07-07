package de.fnafhc.fnaf_collectibles.utils.packets;

import de.fnafhc.fnaf_collectibles.Fnaf_collectibles;
import de.fnafhc.fnaf_collectibles.init.ItemInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DepositCardPacket {
    private final String message;

    public DepositCardPacket(String message) {
        this.message = message;
    }

    public DepositCardPacket(FriendlyByteBuf buf) {
        this.message = buf.readUtf(32767);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(message);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if(stack.getItem() == ItemInit.INDEX.get()) {
                CompoundTag tag = stack.getTag().getCompound("cards");
                if(tag.contains(message)) {
                    Item item = Fnaf_collectibles.getItemFromRegistryName(message);
                    ItemStack stack1 = new ItemStack(item);

                    player.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                        ItemStack remaining = ItemHandlerHelper.insertItemStacked(handler, stack1, false);

                        if (!remaining.isEmpty()) {
                            player.drop(remaining, false);
                        }
                    });
                    int count = tag.getInt(message);
                    if(count > 1) {
                        count = count - 1;
                        tag.putInt(message, count);
                        stack.getTag().put("cards", tag);
                    }else {
                        tag.remove(message);
                        stack.getTag().put("cards", tag);
                    }
                }else {
                    System.out.println(player.getName() + " tries to cheat cards!");
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}

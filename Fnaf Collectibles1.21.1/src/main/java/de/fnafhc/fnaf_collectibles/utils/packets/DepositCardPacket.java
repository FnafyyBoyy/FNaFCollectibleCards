package de.fnafhc.fnaf_collectibles.utils.packets;

import de.fnafhc.fnaf_collectibles.Fnaf_Collectibles;
import de.fnafhc.fnaf_collectibles.init.ItemInit;
import de.fnafhc.fnaf_collectibles.utils.IndexData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashMap;
import java.util.Map;

public record DepositCardPacket(String message) implements CustomPacketPayload {

    public static final Type<DepositCardPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Fnaf_Collectibles.MODID, "deposit_card"));

    public static final StreamCodec<FriendlyByteBuf, DepositCardPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, DepositCardPacket::message,
            DepositCardPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleServer(DepositCardPacket payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

            if (stack.is(ItemInit.INDEX.get())) {
                IndexData indexData = stack.getOrDefault(ItemInit.INDEX_DATA_COMPONENT.get(), new IndexData(new HashMap<>()));
                Map<String, Integer> map = new HashMap<>(indexData.cards());

                if (map.containsKey(payload.message())) {
                    Item item = Fnaf_Collectibles.getItemFromRegistryName(payload.message());
                    ItemStack stack1 = new ItemStack(item);

                    var handler = player.getCapability(Capabilities.ItemHandler.ENTITY);
                    if (handler != null) {
                        ItemStack remaining = ItemHandlerHelper.insertItemStacked(handler, stack1, false);
                        if (!remaining.isEmpty()) {
                            player.drop(remaining, false);
                        }
                    }

                    int count = map.get(payload.message());
                    if (count > 1) {
                        map.put(payload.message(), count - 1);
                    } else {
                        map.remove(payload.message());
                    }

                    stack.set(ItemInit.INDEX_DATA_COMPONENT.get(), new IndexData(map));
                } else {
                    System.out.println(player.getName() + " tries to cheat cards!");
                }
            }
        });
    }
}
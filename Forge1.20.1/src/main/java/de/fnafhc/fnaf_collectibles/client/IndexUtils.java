package de.fnafhc.fnaf_collectibles.client;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class IndexUtils {
    public static void openScreen(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        CompoundTag cards = tag.contains("cards")
                ? tag.getCompound("cards")
                : new CompoundTag();
        Minecraft.getInstance().setScreen(new IndexScreen(Component.literal("Index"), stack));
    }
}

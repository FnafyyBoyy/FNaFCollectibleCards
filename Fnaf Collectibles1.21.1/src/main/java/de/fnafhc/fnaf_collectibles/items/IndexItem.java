package de.fnafhc.fnaf_collectibles.items;

import de.fnafhc.fnaf_collectibles.Fnaf_Collectibles;
import de.fnafhc.fnaf_collectibles.client.IndexUtils;
import de.fnafhc.fnaf_collectibles.init.ItemInit;
import de.fnafhc.fnaf_collectibles.utils.IndexData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexItem extends Item {
    public IndexItem() {
        super(new Properties().stacksTo(1).fireResistant());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (hand == InteractionHand.OFF_HAND) return super.use(level, player, hand);
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            if (!level.isClientSide) {
                Inventory inv = player.getInventory();

                List<Item> commonItem = Arrays.asList(Fnaf_Collectibles.common);
                List<Item> uncommonItem = Arrays.asList(Fnaf_Collectibles.uncommon);
                List<Item> rareItem = Arrays.asList(Fnaf_Collectibles.rare);
                List<Item> epicItem = Arrays.asList(Fnaf_Collectibles.epic);
                List<Item> legendaryItem = Arrays.asList(Fnaf_Collectibles.legendary);

                IndexData existingData = stack.getOrDefault(ItemInit.INDEX_DATA_COMPONENT.get(), new IndexData(new HashMap<>()));
                Map<String, Integer> map = new HashMap<>(existingData.cards());

                for (int i = 0; i < inv.getContainerSize(); i++) {
                    ItemStack iStack = inv.getItem(i);
                    String regName = Fnaf_Collectibles.getItemRegistryName(iStack.getItem());

                    if (commonItem.contains(iStack.getItem())
                            || uncommonItem.contains(iStack.getItem())
                            || rareItem.contains(iStack.getItem())
                            || epicItem.contains(iStack.getItem())
                            || legendaryItem.contains(iStack.getItem())) {

                        map.put(regName, map.getOrDefault(regName, 0) + iStack.getCount());
                        inv.setItem(i, ItemStack.EMPTY);
                    }
                }

                stack.set(ItemInit.INDEX_DATA_COMPONENT.get(), new IndexData(map));
            }
        } else {
            if (level.isClientSide) {
                IndexUtils.openScreen(stack);
            }
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Component c1 = Component.literal("Right-click: ").withStyle(ChatFormatting.WHITE)
                .append(Component.literal("Open").withStyle(ChatFormatting.GRAY));
        Component c2 = Component.literal("Shift Right-click: ").withStyle(ChatFormatting.WHITE)
                .append(Component.literal("Index all cards in inventory").withStyle(ChatFormatting.GRAY));
        Component c3 = Component.literal("DO NOT LOSE THIS ITEM IF THE CARDS ARE IMPORTANT TO YOU").withStyle(ChatFormatting.RED);

        tooltipComponents.add(c1);
        tooltipComponents.add(c2);
        tooltipComponents.add(c3);
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
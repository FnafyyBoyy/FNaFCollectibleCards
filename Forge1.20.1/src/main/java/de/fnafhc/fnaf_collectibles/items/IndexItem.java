package de.fnafhc.fnaf_collectibles.items;

import de.fnafhc.fnaf_collectibles.Fnaf_collectibles;
import de.fnafhc.fnaf_collectibles.client.IndexUtils;
import de.fnafhc.fnaf_collectibles.init.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexItem extends Item {
    public IndexItem() {
        super(new Item.Properties()
                .stacksTo(1)
                .fireResistant());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(hand == InteractionHand.OFF_HAND) return super.use(level, player, hand);
        ItemStack stack = player.getItemInHand(hand);
        if(!level.isClientSide) {
            CompoundTag tag = stack.getOrCreateTag();
            if(!tag.contains("cards")) {
                tag.put("cards", new CompoundTag());
            }
        }
        if(player.isShiftKeyDown()) {
            if(!level.isClientSide) {
                Inventory inv = player.getInventory();

                List<Item> commonItem = Arrays.asList(Fnaf_collectibles.common);
                List<Item> uncommonItem = Arrays.asList(Fnaf_collectibles.uncommon);
                List<Item> rareItem = Arrays.asList(Fnaf_collectibles.rare);
                List<Item> epicItem = Arrays.asList(Fnaf_collectibles.epic);
                List<Item> legendaryItem = Arrays.asList(Fnaf_collectibles.legendary);

                CompoundTag tag = stack.getTag().getCompound("cards");

                Map<String, Integer> map = new HashMap<>();

                for (String key : tag.getAllKeys()) {
                    map.put(key, tag.getInt(key));
                }

                for (int i = 0; i < inv.getContainerSize(); i++) {
                    ItemStack iStack = inv.getItem(i);
                    String regName = Fnaf_collectibles.getItemRegistryName(iStack.getItem());
                    if(commonItem.contains(iStack.getItem())
                    || uncommonItem.contains(iStack.getItem())
                    || rareItem.contains(iStack.getItem())
                    || epicItem.contains(iStack.getItem())
                    || legendaryItem.contains(iStack.getItem())) {
                        if(map.containsKey(regName)) {
                            int count = map.get(regName);
                            count = count + 1;
                            map.put(regName, count);
                        }else {
                            map.put(regName, 1);
                        }
                        inv.setItem(i, ItemStack.EMPTY);
                    }
                }

                CompoundTag mapTag = new CompoundTag();

                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    mapTag.putInt(entry.getKey(), entry.getValue());
                }

                stack.getTag().put("cards", mapTag);
            }
        }else {
            if(level.isClientSide) {
                IndexUtils.openScreen(stack);
            }
        }
        return super.use(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        Component c1 = Component.literal("Right-click: ").withStyle(ChatFormatting.WHITE)
                .append(Component.literal("Open").withStyle(ChatFormatting.GRAY));

        Component c2 = Component.literal("Shift Right-click: ").withStyle(ChatFormatting.WHITE)
                .append(Component.literal("Index all cards in inventory").withStyle(ChatFormatting.GRAY));

        Component c3 = Component.literal("DO NOT LOSE THIS ITEM IF THE CARDS ARE IMPORTANT TO YOU").withStyle(ChatFormatting.RED);

        list.add(c1);
        list.add(c2);
        list.add(c3);
        super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
    }
}

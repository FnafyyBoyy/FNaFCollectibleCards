package de.fnafhc.fnaf_collectibles.items;

import de.fnafhc.fnaf_collectibles.Fnaf_Collectibles;
import de.fnafhc.fnaf_collectibles.init.ItemInit;
import de.fnafhc.fnaf_collectibles.utils.CardData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Random;

public class CollectiblePackItem extends Item {
    private final String lvl;

    public CollectiblePackItem(String lvl, Properties properties) {
        super(properties);
        this.lvl = lvl;
    }

    public String getLvl() {
        return this.lvl;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand pHand) {
        if (pHand == InteractionHand.MAIN_HAND) {
            ItemStack item = player.getItemInHand(InteractionHand.MAIN_HAND);

            addTags(item);

            CardData data = item.get(ItemInit.CARD_DATA_COMPONENT.get());
            if (data != null) {
                int chance = data.chance();
                int i = data.count();

                player.setItemInHand(pHand, new ItemStack(Items.AIR));

                ItemStack item1 = new ItemStack(getRandomCard(chance));
                ItemStack item2 = new ItemStack(getRandomCard(chance));
                ItemStack item3 = new ItemStack(getRandomCard(chance));

                if (!player.addItem(item1)) {
                    player.drop(item1, false);
                }

                if (i >= 2) {
                    if (!player.addItem(item2)) {
                        player.drop(item2, false);
                    }
                }
                if (i == 3) {
                    if (!player.addItem(item3)) {
                        player.drop(item3, false);
                    }
                }
                return InteractionResultHolder.consume(item);
            }
        }

        return super.use(level, player, pHand);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (!world.isClientSide) {
            addTags(stack);
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private void addTags(ItemStack stack) {
        if (!stack.has(ItemInit.CARD_DATA_COMPONENT.get())) {
            Random random = new Random();
            int cc = 60;
            int rc = 30;
            int roll = random.nextInt(100);

            int count;
            if (roll < cc) {
                count = 1;
            } else if (roll < cc + rc) {
                count = 2;
            } else {
                count = 3;
            }

            int chance = getChance();

            stack.set(ItemInit.CARD_DATA_COMPONENT.get(), new CardData(count, chance));
        }
    }

    public static int getChance() {
        Random random = new Random();
        double r = random.nextDouble();
        double exponent = 4;
        int value = (int) (Math.pow(r, exponent) * 100) + 1;
        return Math.min(100, Math.max(1, value));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        CardData data = stack.get(ItemInit.CARD_DATA_COMPONENT.get());
        if (data != null) {
            tooltipComponents.add(Component.literal("Cards in this Pack: §b" + data.count()));
            tooltipComponents.add(Component.literal("Legendary Chance: §b" + data.chance() + "%"));
            tooltipComponents.add(Component.literal("§7More Packs will come in the Future"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public static Item getRandomCard(int legendaryChance) {
        Random random = new Random();
        int commonChance = 65;
        int uncommonChance = 15;
        int rareChance = 10;
        int epicChance = 7;

        int subtractFromCommon = Math.min(legendaryChance, commonChance);
        commonChance -= subtractFromCommon;
        int remaining = legendaryChance - subtractFromCommon;
        if (remaining > 0) {
            int subtractFromUncommon = Math.min(remaining, uncommonChance);
            uncommonChance -= subtractFromUncommon;
            remaining -= subtractFromUncommon;

            if (remaining > 0) {
                int subtractFromRare = Math.min(remaining, rareChance);
                rareChance -= subtractFromRare;
                remaining -= subtractFromRare;

                if (remaining > 0) {
                    int subtractFromEpic = Math.min(remaining, epicChance);
                    epicChance -= subtractFromEpic;
                }
            }
        }
        int roll = random.nextInt(100);
        if (roll < commonChance) {
            return getRandomFromArray(Fnaf_Collectibles.common);
        } else if (roll < commonChance + uncommonChance) {
            return getRandomFromArray(Fnaf_Collectibles.uncommon);
        } else if (roll < commonChance + uncommonChance + rareChance) {
            return getRandomFromArray(Fnaf_Collectibles.rare);
        } else if (roll < commonChance + uncommonChance + rareChance + epicChance) {
            return getRandomFromArray(Fnaf_Collectibles.epic);
        } else {
            return getRandomFromArray(Fnaf_Collectibles.legendary);
        }
    }

    private static Item getRandomFromArray(Item[] array) {
        Random random = new Random();
        return array[random.nextInt(array.length)];
    }
}
package de.fnafhc.fnaf_collectibles.items;

import de.fnafhc.fnaf_collectibles.Fnaf_collectibles;
import net.minecraft.nbt.CompoundTag;
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
import org.jetbrains.annotations.Nullable;

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
        if(pHand.equals(InteractionHand.MAIN_HAND)) {
            ItemStack item = player.getItemInHand(InteractionHand.MAIN_HAND);
            if(item.getTag() == null) {
                addTags(item);
            }
            int chance = item.getTag().getInt("chance");
            if(item.getTag() == null || !item.getTag().contains("count")){
                ItemStack item1 = new ItemStack(getRandomCard(chance));
                item1.setCount(1);
                player.addItem(item1);
            }else {
                int i = item.getTag().getInt("count");
                player.setItemInHand(pHand, new ItemStack(Items.AIR));
                ItemStack item1 = new ItemStack(getRandomCard(chance));
                item1.setCount(1);
                ItemStack item2 = new ItemStack(getRandomCard(chance));
                item1.setCount(1);
                ItemStack item3 = new ItemStack(getRandomCard(chance));
                item1.setCount(1);
                if(!player.addItem(item1)){
                    player.drop(item1, false);
                }
                player.addItem(item1);
                if(i == 2) {
                    if(!player.addItem(item2)){
                        player.drop(item2, false);
                    }
                }else if(i == 3){
                    if(!player.addItem(item2)){
                        player.drop(item2, false);
                    }
                    if(!player.addItem(item3)){
                        player.drop(item3, false);
                    }
                }
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
        CompoundTag tag = stack.getOrCreateTag();
        if (tag == null || !tag.contains("count")) {
            Random random = new Random();
            int cc = 60;
            int rc = 30;
            int ec = 10;
            int roll = random.nextInt(100);
            if(roll < cc){
                tag.putInt("count", 1);
            }else if(roll < cc+rc){
                tag.putInt("count", 2);
            }else {
                tag.putInt("count", 3);
            }
        }
        if(!tag.contains("chance")) {
            int chance = getChance();
            tag.putInt("chance", chance);
        }
    }

    public static int getChance() {
        Random random = new Random();
        double r = random.nextDouble();

        double exponent = 4;

        double multiplier = 1.0;

        int value = (int) (Math.pow(r, exponent) * 100) + 1;

        return Math.min(100, Math.max(1, value));
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        if(p_41421_.getTag() != null) {
            p_41423_.add(Component.literal("Cards in this Pack: §b" + p_41421_.getTag().getInt("count")));
            p_41423_.add(Component.literal("Legendary Chance: §b" + p_41421_.getTag().getInt("chance") + "%"));
            p_41423_.add(Component.literal("§7More Packs will come in the Future"));
        }
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }

    public static Item getRandomCard(int legendaryChance) {
        Random random = new Random();
        int commonChance = 65;
        int uncommonChance = 15;
        int rareChance = 10;
        int epicChance = 7;
        int legendaryChanceBase = 3 + legendaryChance;

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
            return getRandomFromArray(Fnaf_collectibles.common);
        } else if (roll < commonChance + uncommonChance) {
            return getRandomFromArray(Fnaf_collectibles.uncommon);
        } else if (roll < commonChance + uncommonChance + rareChance) {
            return getRandomFromArray(Fnaf_collectibles.rare);
        } else if (roll < commonChance + uncommonChance + rareChance + epicChance) {
            return getRandomFromArray(Fnaf_collectibles.epic);
        } else {
            return getRandomFromArray(Fnaf_collectibles.legendary);
        }
    }

    private static Item getRandomFromArray(Item[] array) {
        Random random = new Random();
        return array[random.nextInt(array.length)];
    }
}

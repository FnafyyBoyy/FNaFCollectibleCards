package de.fnafhc.fnaf_collectibles.items;

import de.fnafhc.fnaf_collectibles.init.ItemInit;
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
            if(item.getTag() == null || !item.getTag().contains("count")){
                ItemStack item1 = new ItemStack(getRandomCard());
                item1.setCount(1);
                player.addItem(item1);
            }else {
                int i = item.getTag().getInt("count");
                player.setItemInHand(pHand, new ItemStack(Items.AIR));
                ItemStack item1 = new ItemStack(getRandomCard());
                item1.setCount(1);
                ItemStack item2 = new ItemStack(getRandomCard());
                item1.setCount(1);
                ItemStack item3 = new ItemStack(getRandomCard());
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
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        if(p_41421_.getTag() != null) {
            p_41423_.add(Component.literal("Cards in this Pack: §b" + p_41421_.getTag().getInt("count")));
            p_41423_.add(Component.literal("§7More Packs will come in the Future"));
        }
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }

    public static Item getRandomCard() {
        Random random = new Random();
        Item[] common = {
                ItemInit.FREDDYCARD.get(), ItemInit.BONNIECARD.get(), ItemInit.CHICACARD.get(), ItemInit.FOXYCARD.get(), ItemInit.GOLDENFREDDYCARD.get(), ItemInit.ENDO1CARD.get(),
                ItemInit.TOYFREDDYCARD.get(), ItemInit.TOYBONNIECARD.get(), ItemInit.TOYCHICACARD.get(), ItemInit.MANGLECARD.get(), ItemInit.BALLOONBOYCARD.get(), ItemInit.JJCARD.get(),
                ItemInit.WITHEREDFREDDY.get(), ItemInit.WITHEREDBONNIE.get(), ItemInit.WITHEREDCHICA.get(), ItemInit.WITHEREDFOXY.get(), ItemInit.WITHEREDGOLDENFREDDY.get(),
                ItemInit.PUPPETCARD.get(), ItemInit.SHADOWFREDDYCARD.get(), ItemInit.SHADOWBONNIECARD.get(), ItemInit.ENDO2CARD.get(),
                ItemInit.SPRINGTRAPCARD.get(), ItemInit.PHANTOMFREDDYCARD.get(), ItemInit.PHANTOMCHICACARD.get(), ItemInit.PHANTOMFOXYCARD.get(), ItemInit.PHANTOMMANGLECARD.get(),
                ItemInit.PHANTOMPUPPETCARD.get(),
                ItemInit.NIGHTMAREFREDDYCARD.get(), ItemInit.NIGHTMAREBONNIECARD.get(), ItemInit.NIGHTMARECHICACARD.get(), ItemInit.NIGHTMAREFOXYCARD.get(), ItemInit.NIGHTMAREFREDBEARCARD.get(),
                ItemInit.NIGHTMARECARD.get(), ItemInit.PLUSHTRAPCARD.get(), ItemInit.NIGHTMAREPUPPETCARD.get(), ItemInit.NIGHTMAREBALLOONBOYCARD.get(), ItemInit.JACKOBONNIECARD.get(),
                ItemInit.JACKOCHICACARD.get(),
                ItemInit.CIRCUSBABYCARD.get(), ItemInit.FUNTIMEFREDDYCARD.get(), ItemInit.FUNTIMEFOXYCARD.get(), ItemInit.BALLORACARD.get(), ItemInit.ENNARDCARD.get(), ItemInit.BONBONCARD.get(),
                ItemInit.BIDYBABCARD.get(), ItemInit.MINIREENACARD.get(), ItemInit.LOLBITCARD.get(), ItemInit.YENNDOCARD.get()
        };
        Item[] rare = {};
        Item[] epic = {};

        // Wahrscheinlichkeiten in Prozent
        //int commonChance = 60;
        //int rareChance = 30;
        //int epicChance = 10;
        int commonChance = 101;
        int rareChance = 0;
        int epicChance = 0;

        int roll = random.nextInt(100);

        if (roll < commonChance) {
            return getRandomFromArray(common);
        } else if (roll < commonChance + rareChance) {
            return getRandomFromArray(rare);
        } else {
            return getRandomFromArray(epic);
        }
    }

    private static Item getRandomFromArray(Item[] array) {
        Random random = new Random();
        return array[random.nextInt(array.length)];
    }
}

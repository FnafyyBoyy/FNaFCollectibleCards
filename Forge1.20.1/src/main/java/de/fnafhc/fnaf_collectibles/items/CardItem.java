package de.fnafhc.fnaf_collectibles.items;

import de.fnafhc.fnaf_collectibles.Fnaf_collectibles;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CardItem extends Item {
    private final String lvl;

    public CardItem(String lvl, Properties properties) {
        super(properties);
        this.lvl = lvl;
    }

    public String getLvl() {
        return lvl;
    }

    public static String getItemRegistryName(Item item) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
        return id != null ? id.toString() : "unknown";
    }

    public Component getName() {
        return Fnaf_collectibles.convertToTranslate(this);
    }

    @Override
    public Component getName(ItemStack stack) {
        return Fnaf_collectibles.convertToTranslate(this);
    }

    public String getRarity() {
        if(this.lvl == "common") {
            return "§7Common";
        }
        else if(this.lvl == "uncommon") {
            return "§2Uncommon";
        }
        else if(this.lvl == "rare") {
            return "§9Rare";
        }
        else if(this.lvl == "epic") {
            return "§5Epic";
        }
        else if(this.lvl == "legendary") {
            return "§6Legendary";
        }
        else if(this.lvl == "event") {
            return "§3Event";
        }
        return "§7Common";
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        p_41423_.add(Component.literal("§fRarity: " + getRarity()));
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }
}

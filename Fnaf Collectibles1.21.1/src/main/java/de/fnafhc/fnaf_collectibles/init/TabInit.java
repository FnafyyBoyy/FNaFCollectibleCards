package de.fnafhc.fnaf_collectibles.init;

import de.fnafhc.fnaf_collectibles.Fnaf_Collectibles;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TabInit {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Fnaf_Collectibles.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> cards = TABS.register("fnaf_cards_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemInit.INDEX.get()))
            .title(Component.translatable("tab.fnaf_cards_tab"))
            .withSearchBar()
            .displayItems((itemDisplayParameters, output) -> {
                System.out.println("TEST LOLOL");
                output.accept(ItemInit.INDEX.get());
                output.accept(ItemInit.FNAFCOLLECTIBLEPACK.get());

                for(Item item : Fnaf_Collectibles.common) {
                    output.accept(item);
                }
                for(Item item : Fnaf_Collectibles.uncommon) {
                    output.accept(item);
                }
                for(Item item : Fnaf_Collectibles.rare) {
                    output.accept(item);
                }
                for(Item item : Fnaf_Collectibles.epic) {
                    output.accept(item);
                }
                for(Item item : Fnaf_Collectibles.legendary) {
                    output.accept(item);
                }
            })
            .build());

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }
}

package de.fnafhc.fnaf_collectibles.init;

import de.fnafhc.fnaf_collectibles.Fnaf_collectibles;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TabInit {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Fnaf_collectibles.MODID);

    public static final RegistryObject<CreativeModeTab> cards = TABS.register("fnaf_cards_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemInit.INDEX.get()))
            .title(Component.translatable("tab.fnaf_cards_tab"))
            .withSearchBar()
            .displayItems((itemDisplayParameters, output) -> {
                System.out.println("TEST LOLOL");
                output.accept(ItemInit.INDEX.get());
                output.accept(ItemInit.FNAFCOLLECTIBLEPACK.get());

                for(Item item : Fnaf_collectibles.common) {
                    output.accept(item);
                }
                for(Item item : Fnaf_collectibles.uncommon) {
                    output.accept(item);
                }
                for(Item item : Fnaf_collectibles.rare) {
                    output.accept(item);
                }
                for(Item item : Fnaf_collectibles.epic) {
                    output.accept(item);
                }
                for(Item item : Fnaf_collectibles.legendary) {
                    output.accept(item);
                }
            })
            .build());

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }
}

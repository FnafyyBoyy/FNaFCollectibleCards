package de.fnafhc.fnaf_collectibles.init;

import de.fnafhc.fnaf_collectibles.Fnaf_collectibles;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TabInit {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Fnaf_collectibles.MODID);

    public static final RegistryObject<CreativeModeTab> cards = TABS.register("fnaf_cards_tab", () -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemInit.FREDDYCARD.get()))
            .title(Component.translatable("tab.fnaf_cards_tab"))
            .withSearchBar()
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(ItemInit.FNAFCOLLECTIBLEPACK.get());
                //Fnaf 1 Common
                output.accept(ItemInit.FREDDYCARD.get());
                output.accept(ItemInit.BONNIECARD.get());
                output.accept(ItemInit.FOXYCARD.get());
                output.accept(ItemInit.CHICACARD.get());
                output.accept(ItemInit.GOLDENFREDDYCARD.get());
                output.accept(ItemInit.ENDO1CARD.get());

                //Fnaf 1 Uncommon

                //Fnaf 1 Rare

                //Fnaf 1 Epic

                //Fnaf 1 Legendary

                //Fnaf 2 Common
                output.accept(ItemInit.TOYFREDDYCARD.get());
                output.accept(ItemInit.TOYBONNIECARD.get());
                output.accept(ItemInit.TOYCHICACARD.get());
                output.accept(ItemInit.MANGLECARD.get());
                output.accept(ItemInit.BALLOONBOYCARD.get());
                output.accept(ItemInit.JJCARD.get());
                output.accept(ItemInit.WITHEREDFREDDY.get());
                output.accept(ItemInit.WITHEREDBONNIE.get());
                output.accept(ItemInit.WITHEREDCHICA.get());
                output.accept(ItemInit.WITHEREDFOXY.get());
                output.accept(ItemInit.WITHEREDGOLDENFREDDY.get());
                output.accept(ItemInit.PUPPETCARD.get());
                output.accept(ItemInit.SHADOWFREDDYCARD.get());
                output.accept(ItemInit.SHADOWBONNIECARD.get());
                output.accept(ItemInit.ENDO2CARD.get());

                //Fnaf 2 Uncommon

                //Fnaf 2 Rare

                //Fnaf 2 Epic

                //Fnaf 2 Legendary

                //Fnaf 3 Common
                output.accept(ItemInit.SPRINGTRAPCARD.get());
                output.accept(ItemInit.PHANTOMFREDDYCARD.get());
                output.accept(ItemInit.PHANTOMCHICACARD.get());
                output.accept(ItemInit.PHANTOMFOXYCARD.get());
                output.accept(ItemInit.PHANTOMMANGLECARD.get());
                output.accept(ItemInit.PHANTOMPUPPETCARD.get());

                //Fnaf 3 Uncommon

                //Fnaf 3 Rare

                //Fnaf 3 Epic

                //Fnaf 3 Legendary

                //Fnaf 4 Common
                output.accept(ItemInit.NIGHTMAREFREDDYCARD.get());
                output.accept(ItemInit.NIGHTMAREBONNIECARD.get());
                output.accept(ItemInit.NIGHTMARECHICACARD.get());
                output.accept(ItemInit.NIGHTMAREFOXYCARD.get());
                output.accept(ItemInit.NIGHTMAREFREDBEARCARD.get());
                output.accept(ItemInit.NIGHTMARECARD.get());
                output.accept(ItemInit.PLUSHTRAPCARD.get());
                output.accept(ItemInit.NIGHTMAREPUPPETCARD.get());
                output.accept(ItemInit.NIGHTMAREBALLOONBOYCARD.get());
                output.accept(ItemInit.JACKOBONNIECARD.get());
                output.accept(ItemInit.JACKOCHICACARD.get());

                //Fnaf 4 Uncommon

                //Fnaf 4 Rare

                //Fnaf 4 Epic

                //Fnaf 4 Legendary

                //Fnaf 5 Common
                output.accept(ItemInit.CIRCUSBABYCARD.get());
                output.accept(ItemInit.FUNTIMEFREDDYCARD.get());
                output.accept(ItemInit.FUNTIMEFOXYCARD.get());
                output.accept(ItemInit.BALLORACARD.get());
                output.accept(ItemInit.ENNARDCARD.get());
                output.accept(ItemInit.BONBONCARD.get());
                output.accept(ItemInit.BIDYBABCARD.get());
                output.accept(ItemInit.MINIREENACARD.get());
                output.accept(ItemInit.LOLBITCARD.get());
                output.accept(ItemInit.YENNDOCARD.get());

                //Fnaf 5 Uncommon

                //Fnaf 5 Rare

                //Fnaf 5 Epic

                //Fnaf 5 Legendary

                //Fnaf 6 Common

                //Fnaf 6 Uncommon

                //Fnaf 6 Rare

                //Fnaf 6 Epic

                //Fnaf 6 Legendary
            })
            .build());

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }
}

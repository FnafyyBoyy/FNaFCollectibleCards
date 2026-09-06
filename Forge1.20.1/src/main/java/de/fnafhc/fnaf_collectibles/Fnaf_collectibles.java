package de.fnafhc.fnaf_collectibles;

import de.fnafhc.fnaf_collectibles.init.ItemInit;
import de.fnafhc.fnaf_collectibles.init.TabInit;
import de.fnafhc.fnaf_collectibles.utils.PacketManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Fnaf_collectibles.MODID)
public class Fnaf_collectibles {
    public static final String MODID = "fnaf_collectibles";

    public static Item[] common = {};
    public static Item[] uncommon = {};
    public static Item[] rare = {};
    public static Item[] epic = {};
    public static Item[] legendary = {};
    public static Item[] event = {};
    public static ItemInit.CardSet[] ALL_CARD_SETS = {};

    public Fnaf_collectibles() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemInit.register(modEventBus);
        TabInit.register(modEventBus);
        PacketManager.register();

        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        Fnaf_collectibles.ALL_CARD_SETS = new ItemInit.CardSet[]{
                ItemInit.FREDDYCARD,
                ItemInit.BONNIECARD,
                ItemInit.CHICACARD,
                ItemInit.FOXYCARD,
                ItemInit.GOLDENFREDDYCARD,
                ItemInit.ENDO1CARD,
                ItemInit.TOYFREDDYCARD,
                ItemInit.TOYBONNIECARD,
                ItemInit.TOYCHICACARD,
                ItemInit.MANGLECARD,
                ItemInit.BALLOONBOYCARD,
                ItemInit.JJCARD,
                ItemInit.WITHEREDFREDDY,
                ItemInit.WITHEREDBONNIE,
                ItemInit.WITHEREDCHICA,
                ItemInit.WITHEREDFOXY,
                ItemInit.WITHEREDGOLDENFREDDY,
                ItemInit.PUPPETCARD,
                ItemInit.SHADOWFREDDYCARD,
                ItemInit.SHADOWBONNIECARD,
                ItemInit.ENDO2CARD,
                ItemInit.SPRINGTRAPCARD,
                ItemInit.PHANTOMFREDDYCARD,
                ItemInit.PHANTOMCHICACARD,
                ItemInit.PHANTOMFOXYCARD,
                ItemInit.PHANTOMMANGLECARD,
                ItemInit.PHANTOMPUPPETCARD,
                ItemInit.NIGHTMAREFREDDYCARD,
                ItemInit.NIGHTMAREBONNIECARD,
                ItemInit.NIGHTMARECHICACARD,
                ItemInit.NIGHTMAREFOXYCARD,
                ItemInit.NIGHTMAREFREDBEARCARD,
                ItemInit.NIGHTMARECARD,
                ItemInit.PLUSHTRAPCARD,
                ItemInit.NIGHTMAREPUPPETCARD,
                ItemInit.NIGHTMAREBALLOONBOYCARD,
                ItemInit.JACKOBONNIECARD,
                ItemInit.JACKOCHICACARD,
                ItemInit.CIRCUSBABYCARD,
                ItemInit.FUNTIMEFREDDYCARD,
                ItemInit.FUNTIMEFOXYCARD,
                ItemInit.BALLORACARD,
                ItemInit.ENNARDCARD,
                ItemInit.BONBONCARD,
                ItemInit.HANDUNITCARD,
                ItemInit.BIDYBABCARD,
                ItemInit.MINIREENACARD,
                ItemInit.LOLBITCARD,
                ItemInit.YENNDOCARD,
                ItemInit.ROCKSTARFREDDYCARD,
                ItemInit.ROCKSTARBONNIECARD,
                ItemInit.ROCKSTARCHICACARD,
                ItemInit.ROCKSTARFOXYCARD,
                ItemInit.SCRAPBABYCARD,
                ItemInit.SCRAPTRAP,
                ItemInit.MOLTENFREDDY,
                ItemInit.LEFTYCARD,
                ItemInit.HELPYCARD,
                ItemInit.MUSICMANCARD,
                ItemInit.ELCHIPCARD,
                ItemInit.FUNTIMECHICACARD,
                ItemInit.HAPPYFROGCARD,
                ItemInit.MRHIPPOCARD,
                ItemInit.PIGPATCHCARD,
                ItemInit.NEDDBEARCARD,
                ItemInit.ORVILLECARD,
                ItemInit.TRASHGANGCARD,
                ItemInit.PICKLESCARD,
                ItemInit.SECURITYPUPPETCARD,
                ItemInit.CANDYCADETCARD,
                ItemInit.FRUITPUNCHCLOWNCARD,
                ItemInit.LEMONADECLOWNCARD,
                ItemInit.PRIZEKINGCARD,
                ItemInit.GUMBALLSWIVELHANDSCARD
        };

        Fnaf_collectibles.common    = ItemInit.cardsOf(set -> set.common);
        Fnaf_collectibles.uncommon  = ItemInit.cardsOf(set -> set.uncommon);
        Fnaf_collectibles.rare      = ItemInit.cardsOf(set -> set.rare);
        Fnaf_collectibles.epic      = ItemInit.cardsOf(set -> set.epic);
        Fnaf_collectibles.legendary = ItemInit.cardsOf(set -> set.legendary);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    public static String getItemRegistryName(Item item) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
        return id != null ? id.toString() : "unknown";
    }

    public static Component convertToTranslate(Item item) {
        String str = "item." + getItemRegistryName(item)
                .replace("uncommon_", "")
                .replace("common_", "")
                .replace("rare_", "")
                .replace("epic_", "")
                .replace("legendary_", "")
                .replace(":", ".");

        return Component.translatable(str);
    }

    public static Item getItemFromRegistryName(String registryName) {
        ResourceLocation id = ResourceLocation.parse(registryName);
        return BuiltInRegistries.ITEM.get(id);
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}

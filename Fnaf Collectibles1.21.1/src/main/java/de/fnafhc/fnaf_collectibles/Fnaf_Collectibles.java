package de.fnafhc.fnaf_collectibles;

import de.fnafhc.fnaf_collectibles.init.ItemInit;
import de.fnafhc.fnaf_collectibles.init.TabInit;
import de.fnafhc.fnaf_collectibles.utils.PacketManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(Fnaf_Collectibles.MODID)
public class Fnaf_Collectibles {
    public static final String MODID = "fnaf_collectibles";

    public static Item[] common = {};
    public static Item[] uncommon = {};
    public static Item[] rare = {};
    public static Item[] epic = {};
    public static Item[] legendary = {};
    public static Item[] event = {};
    public static ItemInit.CardSet[] ALL_CARD_SETS = {};

    public Fnaf_Collectibles(IEventBus modEventBus, ModContainer modContainer) {
        ItemInit.register(modEventBus);
        TabInit.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.register(PacketManager.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        Fnaf_Collectibles.ALL_CARD_SETS = new ItemInit.CardSet[]{
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

        Fnaf_Collectibles.common    = ItemInit.cardsOf(set -> set.common);
        Fnaf_Collectibles.uncommon  = ItemInit.cardsOf(set -> set.uncommon);
        Fnaf_Collectibles.rare      = ItemInit.cardsOf(set -> set.rare);
        Fnaf_Collectibles.epic      = ItemInit.cardsOf(set -> set.epic);
        Fnaf_Collectibles.legendary = ItemInit.cardsOf(set -> set.legendary);
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

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}

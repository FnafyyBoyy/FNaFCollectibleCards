package de.fnafhc.fnaf_collectibles.init;

import de.fnafhc.fnaf_collectibles.Fnaf_collectibles;
import de.fnafhc.fnaf_collectibles.items.CardItem;
import de.fnafhc.fnaf_collectibles.items.CollectiblePackItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Fnaf_collectibles.MODID);

    public static final RegistryObject<Item> FNAFCOLLECTIBLEPACK = ITEMS.register("fnafcommonpack", () -> new CollectiblePackItem("common", new Item.Properties().rarity(Rarity.COMMON).stacksTo(1)));

    public static final RegistryObject<Item> FREDDYCARD = card("freddycard", "common");
    public static final RegistryObject<Item> BONNIECARD = card("bonniecard", "common");
    public static final RegistryObject<Item> FOXYCARD = card("foxycard", "common");
    public static final RegistryObject<Item> CHICACARD = card("chicacard", "common");
    public static final RegistryObject<Item> GOLDENFREDDYCARD = card("goldenfreddycard", "common");
    public static final RegistryObject<Item> ENDO1CARD = card("endo1card", "common");

    public static final RegistryObject<Item> TOYFREDDYCARD = card("toyfreddycard", "common");
    public static final RegistryObject<Item> TOYBONNIECARD = card("toybonniecard", "common");
    public static final RegistryObject<Item> TOYCHICACARD = card("toychicacard", "common");
    public static final RegistryObject<Item> MANGLECARD = card("manglecard", "common");
    public static final RegistryObject<Item> BALLOONBOYCARD = card("balloonboycard", "common");
    public static final RegistryObject<Item> JJCARD = card("jjcard", "common");
    public static final RegistryObject<Item> WITHEREDFREDDY = card("witheredfreddycard", "common");
    public static final RegistryObject<Item> WITHEREDBONNIE = card("witheredbonniecard", "common");
    public static final RegistryObject<Item> WITHEREDCHICA = card("witheredchicacard", "common");
    public static final RegistryObject<Item> WITHEREDFOXY = card("witheredfoxycard", "common");
    public static final RegistryObject<Item> WITHEREDGOLDENFREDDY = card("witheredgoldenfreddycard", "common");
    public static final RegistryObject<Item> PUPPETCARD = card("puppetcard", "common");
    public static final RegistryObject<Item> SHADOWFREDDYCARD = card("shadowfreddycard", "common");
    public static final RegistryObject<Item> SHADOWBONNIECARD = card("shadowbonniecard", "common");
    public static final RegistryObject<Item> ENDO2CARD = card("endo2card", "common");

    public static final RegistryObject<Item> SPRINGTRAPCARD = card("springtrapcard", "common");
    public static final RegistryObject<Item> PHANTOMFREDDYCARD = card("phantomfreddycard", "common");
    public static final RegistryObject<Item> PHANTOMCHICACARD = card("phantomchicacard", "common");
    public static final RegistryObject<Item> PHANTOMFOXYCARD = card("phantomfoxycard", "common");
    public static final RegistryObject<Item> PHANTOMMANGLECARD = card("phantommanglecard", "common");
    public static final RegistryObject<Item> PHANTOMPUPPETCARD = card("phantompuppetcard", "common");

    public static final RegistryObject<Item> NIGHTMAREFREDDYCARD = card("nightmarefreddycard", "common");
    public static final RegistryObject<Item> NIGHTMAREBONNIECARD = card("nightmarebonniecard", "common");
    public static final RegistryObject<Item> NIGHTMARECHICACARD = card("nightmarechicacard", "common");
    public static final RegistryObject<Item> NIGHTMAREFOXYCARD = card("nightmarefoxycard", "common");
    public static final RegistryObject<Item> NIGHTMAREFREDBEARCARD = card("nightmarefredbearcard", "common");
    public static final RegistryObject<Item> NIGHTMARECARD = card("nightmarecard", "common");
    public static final RegistryObject<Item> PLUSHTRAPCARD = card("plushtrapcard", "common");
    public static final RegistryObject<Item> NIGHTMAREPUPPETCARD = card("nightmarepuppetcard", "common");
    public static final RegistryObject<Item> NIGHTMAREBALLOONBOYCARD = card("nightmareballoonboycard", "common");
    public static final RegistryObject<Item> JACKOBONNIECARD = card("jackobonniecard", "common");
    public static final RegistryObject<Item> JACKOCHICACARD = card("jackochicacard", "common");

    public static final RegistryObject<Item> CIRCUSBABYCARD = card("circusbabycard", "common");
    public static final RegistryObject<Item> FUNTIMEFREDDYCARD = card("funtimefreddycard", "common");
    public static final RegistryObject<Item> FUNTIMEFOXYCARD = card("funtimefoxycard", "common");
    public static final RegistryObject<Item> BALLORACARD = card("balloracard", "common");
    public static final RegistryObject<Item> ENNARDCARD = card("ennardcard", "common");
    public static final RegistryObject<Item> BONBONCARD = card("bonboncard", "common");
    public static final RegistryObject<Item> BIDYBABCARD = card("bidybabcard", "common");
    public static final RegistryObject<Item> MINIREENACARD = card("minireenacard", "common");
    public static final RegistryObject<Item> LOLBITCARD = card("lolbitcard", "common");
    public static final RegistryObject<Item> YENNDOCARD = card("yenndocard", "common");

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    public static RegistryObject<Item> card(String name, String lvl){
        if(lvl == "common") {
            return ITEMS.register(name, () -> new CardItem(lvl, new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)));
        }else if(lvl == "uncommon") {
            return ITEMS.register(name, () -> new CardItem(lvl, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
        }else if(lvl == "rare") {
            return ITEMS.register(name, () -> new CardItem(lvl, new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
        }else if(lvl == "epic") {
            return ITEMS.register(name, () -> new CardItem(lvl, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
        }else if(lvl == "legendary") {
            return ITEMS.register(name, () -> new CardItem(lvl, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
        }
        return ITEMS.register(name, () -> new CardItem(lvl, new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)));
    }
}

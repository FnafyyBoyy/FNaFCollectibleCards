package de.fnafhc.fnaf_collectibles.init;

import de.fnafhc.fnaf_collectibles.Fnaf_collectibles;
import de.fnafhc.fnaf_collectibles.items.CardItem;
import de.fnafhc.fnaf_collectibles.items.CardPull;
import de.fnafhc.fnaf_collectibles.items.CollectiblePackItem;
import de.fnafhc.fnaf_collectibles.items.IndexItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Fnaf_collectibles.MODID);

    public static final RegistryObject<Item> INDEX = ITEMS.register("index", IndexItem::new);

    public static final RegistryObject<Item> FNAFCOLLECTIBLEPACK = ITEMS.register("fnafcommonpack", () -> new CollectiblePackItem("common", new Item.Properties().rarity(Rarity.COMMON).stacksTo(1)));

    public static final RegistryObject<Item> CARDPULL = ITEMS.register("cardpull", () -> new CardPull(new Item.Properties()));

    public static class CardSet {
        public final RegistryObject<Item> common, uncommon, rare, epic, legendary;

        public CardSet(String baseName) {
            common = _card("common_" + baseName, "common");
            uncommon = _card("uncommon_" + baseName, "uncommon");
            rare = _card("rare_" + baseName, "rare");
            epic = _card("epic_" + baseName, "epic");
            legendary = _card("legendary_" + baseName, "legendary");
        }
    }

    public static final CardSet FREDDYCARD = new CardSet("freddy_card");
    public static final CardSet BONNIECARD = new CardSet("bonnie_card");
    public static final CardSet FOXYCARD = new CardSet("foxy_card");
    public static final CardSet CHICACARD = new CardSet("chica_card");
    public static final CardSet GOLDENFREDDYCARD = new CardSet("goldenfreddy_card");
    public static final CardSet ENDO1CARD = new CardSet("endo1_card");

    public static final CardSet TOYFREDDYCARD = new CardSet("toyfreddy_card");
    public static final CardSet TOYBONNIECARD = new CardSet("toybonnie_card");
    public static final CardSet TOYCHICACARD = new CardSet("toychica_card");
    public static final CardSet MANGLECARD = new CardSet("mangle_card");
    public static final CardSet BALLOONBOYCARD = new CardSet("balloonboy_card");
    public static final CardSet JJCARD = new CardSet("jj_card");
    public static final CardSet WITHEREDFREDDY = new CardSet("witheredfreddy_card");
    public static final CardSet WITHEREDBONNIE = new CardSet("witheredbonnie_card");
    public static final CardSet WITHEREDCHICA = new CardSet("witheredchica_card");
    public static final CardSet WITHEREDFOXY = new CardSet("witheredfoxy_card");
    public static final CardSet WITHEREDGOLDENFREDDY = new CardSet("witheredgoldenfreddy_card");
    public static final CardSet PUPPETCARD = new CardSet("puppet_card");
    public static final CardSet SHADOWFREDDYCARD = new CardSet("shadowfreddy_card");
    public static final CardSet SHADOWBONNIECARD = new CardSet("shadowbonnie_card");
    public static final CardSet ENDO2CARD = new CardSet("endo2_card");

    public static final CardSet SPRINGTRAPCARD = new CardSet("springtrap_card");
    public static final CardSet PHANTOMFREDDYCARD = new CardSet("phantomfreddy_card");
    public static final CardSet PHANTOMCHICACARD = new CardSet("phantomchica_card");
    public static final CardSet PHANTOMFOXYCARD = new CardSet("phantomfoxy_card");
    public static final CardSet PHANTOMMANGLECARD = new CardSet("phantommangle_card");
    public static final CardSet PHANTOMPUPPETCARD = new CardSet("phantompuppet_card");

    public static final CardSet NIGHTMAREFREDDYCARD = new CardSet("nightmarefreddy_card");
    public static final CardSet NIGHTMAREBONNIECARD = new CardSet("nightmarebonnie_card");
    public static final CardSet NIGHTMARECHICACARD = new CardSet("nightmarechica_card");
    public static final CardSet NIGHTMAREFOXYCARD = new CardSet("nightmarefoxy_card");
    public static final CardSet NIGHTMAREFREDBEARCARD = new CardSet("nightmarefredbear_card");
    public static final CardSet NIGHTMARECARD = new CardSet("nightmare_card");
    public static final CardSet PLUSHTRAPCARD = new CardSet("plushtrap_card");
    public static final CardSet NIGHTMAREPUPPETCARD = new CardSet("nightmarepuppet_card");
    public static final CardSet NIGHTMAREBALLOONBOYCARD = new CardSet("nightmareballoonboy_card");
    public static final CardSet JACKOBONNIECARD = new CardSet("jackobonnie_card");
    public static final CardSet JACKOCHICACARD = new CardSet("jackochica_card");

    public static final CardSet CIRCUSBABYCARD = new CardSet("circusbaby_card");
    public static final CardSet FUNTIMEFREDDYCARD = new CardSet("funtimefreddy_card");
    public static final CardSet FUNTIMEFOXYCARD = new CardSet("funtimefoxy_card");
    public static final CardSet BALLORACARD = new CardSet("ballora_card");
    public static final CardSet ENNARDCARD = new CardSet("ennard_card");
    public static final CardSet BONBONCARD = new CardSet("bonbon_card");
    public static final CardSet BIDYBABCARD = new CardSet("bidybab_card");
    public static final CardSet MINIREENACARD = new CardSet("minireena_card");
    public static final CardSet LOLBITCARD = new CardSet("lolbit_card");
    public static final CardSet YENNDOCARD = new CardSet("yenndo_card");

    public static RegistryObject<Item> _card(String name, String lvl){
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

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    public static Item[] cardsOf(Function<CardSet, RegistryObject<Item>> getter) {
        Item[] result = new Item[Fnaf_collectibles.ALL_CARD_SETS.length];
        for (int i = 0; i < Fnaf_collectibles.ALL_CARD_SETS.length; i++) {
            result[i] = getter.apply(Fnaf_collectibles.ALL_CARD_SETS[i]).get();
        }
        return result;
    }
}

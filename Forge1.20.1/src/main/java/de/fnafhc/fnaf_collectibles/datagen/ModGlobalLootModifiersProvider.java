package de.fnafhc.fnaf_collectibles.datagen;

import de.fnafhc.fnaf_collectibles.Fnaf_collectibles;
import de.fnafhc.fnaf_collectibles.init.ItemInit;
import de.fnafhc.fnaf_collectibles.loot.AddItemModifer;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, Fnaf_collectibles.MODID);
    }

    @Override
    protected void start() {
        LootItemCondition[] conditions = {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/simple_dungeon")).build(),
                new LootTableIdCondition.Builder(new ResourceLocation("chests/jungle_temple")).build(),
                new LootTableIdCondition.Builder(new ResourceLocation("chests/stronghold_library")).build(),
                new LootTableIdCondition.Builder(new ResourceLocation("chests/stronghold_crossing")).build(),
                new LootTableIdCondition.Builder(new ResourceLocation("chests/stronghold_corridor")).build(),
                new LootTableIdCondition.Builder(new ResourceLocation("chests/ancient_city")).build(),
                new LootTableIdCondition.Builder(new ResourceLocation("chests/end_city_treasure")).build(),
                new LootTableIdCondition.Builder(new ResourceLocation("chests/ruined_portal")).build(),
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/village_weaponsmith")).build(),
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/village_toolsmith")).build(),
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/village_armorer")).build(),
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/village_savanna_house")).build(),
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/village_plains_house")).build(),
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/village_snowy_house")).build(),
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/village_taiga_house")).build(),
                new LootTableIdCondition.Builder(new ResourceLocation("chests/village/village_desert_house")).build(),
                new LootTableIdCondition.Builder(new ResourceLocation("chests/shipwreck_map")).build(),
                new LootTableIdCondition.Builder(new ResourceLocation("chests/shipwreck_supply")).build(),
                new LootTableIdCondition.Builder(new ResourceLocation("chests/shipwreck_treasure")).build(),
                new LootTableIdCondition.Builder(new ResourceLocation("chests/buried_treasure")).build()
        };

        add("commonpack", new AddItemModifer(conditions, ItemInit.FNAFCOLLECTIBLEPACK.get()));
    }
}

package de.fnafhc.fnaf_collectibles.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record CardData(int count, int chance) {
    public static final Codec<CardData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("count").forGetter(CardData::count),
            Codec.INT.fieldOf("chance").forGetter(CardData::chance)
    ).apply(instance, CardData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, CardData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, CardData::count,
            ByteBufCodecs.INT, CardData::chance,
            CardData::new
    );
}

package de.fnafhc.fnaf_collectibles.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.HashMap;
import java.util.Map;

public record IndexData(Map<String, Integer> cards) {
    public static final Codec<IndexData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, Codec.INT).fieldOf("cards").forGetter(IndexData::cards)
    ).apply(instance, IndexData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, IndexData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.INT), IndexData::cards,
            IndexData::new
    );
}

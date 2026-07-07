package de.fnafhc.fnaf_collectibles.utils;

import de.fnafhc.fnaf_collectibles.Fnaf_collectibles;
import de.fnafhc.fnaf_collectibles.utils.packets.DepositCardPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketManager {
    private static final String PROTOCOL_VERSION = "1";
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Fnaf_collectibles.MODID, "main"))
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(DepositCardPacket.class, id())
                .encoder(DepositCardPacket::toBytes)
                .decoder(DepositCardPacket::new)
                .consumerMainThread(DepositCardPacket::handle)
                .add();
    }

    public static void sendToServer(Object message) {
        INSTANCE.sendToServer(message);
    }
}

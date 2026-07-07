package de.fnafhc.fnaf_collectibles.utils;

import de.fnafhc.fnaf_collectibles.Fnaf_Collectibles;
import de.fnafhc.fnaf_collectibles.utils.packets.DepositCardPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Fnaf_Collectibles.MODID, bus = EventBusSubscriber.Bus.MOD)
public class PacketManager {

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(
                DepositCardPacket.TYPE,
                DepositCardPacket.STREAM_CODEC,
                DepositCardPacket::handleServer
        );
    }

    public static void sendToServer(CustomPacketPayload message) {
        PacketDistributor.sendToServer(message);
    }
}
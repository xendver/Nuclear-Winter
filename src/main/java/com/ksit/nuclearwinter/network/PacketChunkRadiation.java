package com.ksit.nuclearwinter.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketChunkRadiation {

    // Уровень радиации чанка игрока (передаётся с сервера)
    private final float radiationLevel;

    // Конструктор для отправки (на сервере)
    public PacketChunkRadiation(float radiationLevel) {
        this.radiationLevel = radiationLevel;
    }

    // Конструктор десериализации (на клиенте при получении)
    public PacketChunkRadiation(FriendlyByteBuf buf) {
        this.radiationLevel = buf.readFloat();
    }

    // Сериализация: запись в буфер (на сервере перед отправкой)
    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(radiationLevel);
    }

    // Обработка на клиенте
    public void handle(Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        ctx.enqueueWork(() ->
                ClientRadiationData.radiationLevel = radiationLevel
        );

        ctx.setPacketHandled(true);
    }

    // Клиентское хранилище уровня радиации
    public static class ClientRadiationData {
        public static volatile float radiationLevel = 0f;
    }
}
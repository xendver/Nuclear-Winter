package com.ksit.nuclearwinter.network;

import com.ksit.nuclearwinter.NuclearWinter;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {

    // PROTOCOL_VERSION = "1", если клиент и сервер не совпадают, то соединение отклоняется
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(NuclearWinter.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            // Совпадение версий = клиент принимает соединение
            PROTOCOL_VERSION::equals,
            // Совпадение версий = сервер принимает соединение
            PROTOCOL_VERSION::equals
    );

    public static int id = 0;

    public static void register() {
        // Пакет:
        // id          - уникальный номер пакета
        // класс       - тип пакета
        // encoder     - как записать в буфер
        // decoder     - как прочитать из буфера
        // consumer    - обработчик на получателе
        INSTANCE.registerMessage(
                id++,
                PacketChunkRadiation.class,
                PacketChunkRadiation::encode,
                PacketChunkRadiation::new,
                PacketChunkRadiation::handle
        );
    }
}
// src/main/java/com/ytxmmie/ytmodthird/network/UploadImagePayload.java
package com.ytxmmie.ytmodthird.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record UploadImagePayload(String base64) implements CustomPayload {
    public static final CustomPayload.Id<UploadImagePayload> ID = new CustomPayload.Id<>(Identifier.of("ytmodthird:upload_image"));
    public static final PacketCodec<RegistryByteBuf, UploadImagePayload> CODEC =
            CustomPayload.codecOf(UploadImagePayload::write, UploadImagePayload::read);

    public static void register() {
        PayloadTypeRegistry.playS2C().register(ID, CODEC);
    }

    public static UploadImagePayload read(RegistryByteBuf buf) {
        return new UploadImagePayload(buf.readString());
    }

    public void write(RegistryByteBuf buf) {
        buf.writeString(base64);
    }

    @Override
    public CustomPayload.Id<UploadImagePayload> getId() {
        return ID;
    }
}
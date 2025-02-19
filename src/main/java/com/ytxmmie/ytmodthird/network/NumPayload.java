package com.ytxmmie.ytmodthird.network;

import com.ytxmmie.ytmodthird.YtModThird;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record NumPayload(String num) implements CustomPayload {
    public static final Id<NumPayload> NUM_PACKET_ID = new CustomPayload.Id<>(Identifier.of(YtModThird.Mod_ID, "num_payload"));
    public static final PacketCodec<RegistryByteBuf, NumPayload> CODEC = CustomPayload.codecOf(NumPayload::write, NumPayload::fromBuf);

    private static NumPayload fromBuf(RegistryByteBuf buf) {
        return new NumPayload(buf.readString());
    }

    private void write(RegistryByteBuf buf) {
        buf.writeString(this.num);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return NumPayload.NUM_PACKET_ID;
    }

}
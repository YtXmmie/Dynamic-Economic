package com.ytxmmie.ytmodthird.network;

import com.ytxmmie.ytmodthird.YtModThird;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record EngravingPayload(NbtCompound nbt) implements CustomPayload {
    public static final Id<EngravingPayload> CODE_PACKET_ID = new CustomPayload.Id<>(Identifier.of(YtModThird.Mod_ID, "engraving_payload"));
    public static final PacketCodec<RegistryByteBuf, EngravingPayload> CODEC = CustomPayload.codecOf(EngravingPayload::write, EngravingPayload::fromBuf);

    private static EngravingPayload fromBuf(RegistryByteBuf buf) {
        return new EngravingPayload(buf.readNbt());
    }

    private void write(RegistryByteBuf buf) {
        buf.writeNbt(this.nbt);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return EngravingPayload.CODE_PACKET_ID;
    }

}

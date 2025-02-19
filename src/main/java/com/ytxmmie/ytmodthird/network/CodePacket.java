package com.ytxmmie.ytmodthird.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;
import net.minecraft.network.packet.PlayPackets;
import net.minecraft.network.packet.c2s.play.RenameItemC2SPacket;
import net.minecraft.screen.AnvilScreenHandler;

public class CodePacket extends RenameItemC2SPacket implements Packet<ServerPlayPacketListener> {

    public static final PacketCodec<PacketByteBuf, CodePacket> CODEC = Packet.createCodec(CodePacket::write, CodePacket::new);
    private final String name;

    public CodePacket(String name) {
        super(name);
        this.name = name;
    }

    private CodePacket(PacketByteBuf buf) {
        super(String.valueOf(buf));
        this.name = buf.readString();
    }

    private void write(PacketByteBuf buf) {
        buf.writeString(this.name);
    }

    @Override
    public PacketType<RenameItemC2SPacket> getPacketId() {
        return PlayPackets.RENAME_ITEM;
    }

    public void apply(ServerPlayPacketListener serverPlayPacketListener) {
        serverPlayPacketListener.onRenameItem(this);
    }

    public String getName() {
        return this.name;
    }
}
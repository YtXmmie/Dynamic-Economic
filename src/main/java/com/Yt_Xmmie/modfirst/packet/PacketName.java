package com.Yt_Xmmie.modfirst.packet;

import com.Yt_Xmmie.modfirst.block.tileentity.ContainerPrintingMachiane;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
public class PacketName implements IMessage {

    public PacketName(){}

    private String toSend;

    public PacketName(String name) {
        this.toSend = name;
    }

    @Override public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, toSend);
    }

    @Override public void fromBytes(ByteBuf buf) {
        toSend = ByteBufUtils.readUTF8String(buf);
    }

    public static class PacketNameHandler implements IMessageHandler<PacketName, IMessage> {
        // Do note that the default constructor is required, but implicitly defined in this case

        @Override public IMessage onMessage(PacketName message, MessageContext ctx) {
            // This is the player the packet was sent to the server from
            EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
            // The value that was sent
            String s = message.toSend;
            // Execute the action on the main server thread by adding it as a scheduled task
            serverPlayer.getServerWorld().addScheduledTask(() -> {
                if (serverPlayer.openContainer instanceof ContainerPrintingMachiane) {
                	ContainerPrintingMachiane printingMachine = (ContainerPrintingMachiane) serverPlayer.openContainer;
                	printingMachine.updateMoneyValue(s);
                }
            });
            // No response packet
            return null;
        }
    }
}
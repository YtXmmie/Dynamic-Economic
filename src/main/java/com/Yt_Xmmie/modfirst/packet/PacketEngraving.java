package com.Yt_Xmmie.modfirst.packet;


import com.Yt_Xmmie.modfirst.Main;
import com.Yt_Xmmie.modfirst.init.ModBlocks;
import com.Yt_Xmmie.modfirst.util.NetUtils;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketEngraving implements IMessage, IMessageHandler<PacketEngraving, IMessage>
{
	
	String moneykey;
	public PacketEngraving() {
		
	}
	public PacketEngraving(String key) {
		this.moneykey = key;
	}

	@Override
	public IMessage onMessage(PacketEngraving message, MessageContext ctx)
	{
		EntityPlayer player = NetUtils.getPlayerFromContext(ctx);
		player.getHeldItemMainhand().shrink(1);
		if(player.getHeldItemMainhand().isEmpty()) {
			player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
		}
		ItemStack newEngraving = Main.formingEngraving.get(player);
		newEngraving.getTagCompound().setString("moneykey", message.moneykey);
		EntityItem item = new EntityItem(player.world, player.posX, player.posY, player.posZ, newEngraving);
		player.world.spawnEntity(item);
		return null;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		byte[] data = new byte[buf.readableBytes()];
		buf.readBytes(data);
		moneykey = new String(data);
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
			
		buf.writeBytes(moneykey.getBytes());
		
	}

}
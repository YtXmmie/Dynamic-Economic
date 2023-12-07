package com.Yt_Xmmie.modfirst.network;

import com.Yt_Xmmie.modfirst.block.tileentity.ContainerPrintingMachiane;
import com.Yt_Xmmie.modfirst.block.tileentity.TileEntityPrintingMachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;


public class EngravingGuiHandler implements IGuiHandler{

	public static final int WRITE_ENGRAVING=1;
	public static final int PRINTING_MONEY=2;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == WRITE_ENGRAVING) 
		{
	//		return new GuiScreenEngraving(player);
		}
		if(ID == PRINTING_MONEY) {

            BlockPos pos = new BlockPos(x, y, z);
            TileEntity te = world.getTileEntity(pos);
            return new ContainerPrintingMachiane(player.inventory, world, pos, (TileEntityPrintingMachine) te);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == WRITE_ENGRAVING)
		{
			return new GuiScreenEngraving(player);
		}
		if(ID == PRINTING_MONEY) {

            BlockPos pos = new BlockPos(x, y, z);
            TileEntity te = world.getTileEntity(pos);
            return new GuiContainerrPrintingMachiane(new ContainerPrintingMachiane(player.inventory, world, pos, (TileEntityPrintingMachine) te));
		}
		return null;
	}

}
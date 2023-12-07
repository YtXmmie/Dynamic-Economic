package com.Yt_Xmmie.modfirst.util;

import com.Yt_Xmmie.modfirst.Main;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EngravingUtils {

	public static void bindName(ItemStack stack, World world, EntityPlayer player)
	{
		if(!stack.hasTagCompound())
		{
			stack.setTagCompound(new NBTTagCompound());
		}
		player.openGui(Main.instance, 1, world, (int)player.posX, (int)player.posY , (int)player.posZ);
		Main.formingEngraving.put(player, stack);
	}
	
}

package com.Yt_Xmmie.modfirst;

import com.Yt_Xmmie.modfirst.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class EconomicalTab extends CreativeTabs{

	public EconomicalTab( ) {
		super("economical_tab" );
	}

	@Override
	public ItemStack getTabIconItem() {
		//这里的参数是你的某一个物品，到时候物品栏会显示该物品的贴图
		return new ItemStack(ModItems.MONEY);
	}
	
}

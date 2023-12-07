package com.Yt_Xmmie.modfirst.items;
import com.Yt_Xmmie.modfirst.Main;
import com.Yt_Xmmie.modfirst.init.ModItems;
import com.Yt_Xmmie.modfirst.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
public class ItemBase extends Item implements IHasModel{
	public ItemBase(String name,CreativeTabs tab) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);
		//setMaxStackSize(10);
		
		ModItems.ITEMS.add(this);
	}

	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}

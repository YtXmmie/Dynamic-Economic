package com.Yt_Xmmie.modfirst.items;

import java.util.List;

import com.Yt_Xmmie.modfirst.Main;
import com.Yt_Xmmie.modfirst.init.ModItems;
import com.Yt_Xmmie.modfirst.util.IHasModel;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWrittenEngraving extends Item implements IHasModel{

	public String ParValue;
	ItemWrittenEngraving () {this.ParValue="item.written_engraving.sub";}
	public ItemWrittenEngraving(String name,CreativeTabs tab) {
		this();
		setUnlocalizedName("ytmodfirst."+name);
		setRegistryName(name);
		setCreativeTab(tab);
		setMaxStackSize(64);
		ModItems.ITEMS.add(this);
	}
	public static ItemStack setupNewEngraving()
    {
        ItemStack itemstack = new ItemStack(ModItems.WRITTEN_ENGRAVING, 1);
        return itemstack;
    }

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
		
	}
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, world, tooltip, flagIn);
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("moneykey")) {
			//tooltip.add(stack.getTagCompound().getString("moneykey"));
			tooltip.add(I18n.format("Engraving.moneykey", stack.getTagCompound().getString("moneykey")));			
		}
	}
}
